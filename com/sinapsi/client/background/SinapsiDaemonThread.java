package com.sinapsi.client.background;

import com.bgp.codec.DecodingMethod;
import com.bgp.codec.EncodingMethod;
import com.google.gson.Gson;
import com.sinapsi.client.AppConsts;
import com.sinapsi.client.SafeSyncManager;
import com.sinapsi.client.SyncManager;
import com.sinapsi.client.persistence.DiffDBManager;
import com.sinapsi.client.persistence.LocalDBManager;
import com.sinapsi.client.persistence.UserSettingsFacade;
import com.sinapsi.client.persistence.syncmodel.MacroSyncConflict;
import com.sinapsi.client.web.OnlineStatusProvider;
import com.sinapsi.client.web.RetrofitWebServiceFacade;
import com.sinapsi.client.web.SinapsiWebServiceFacade;
import com.sinapsi.client.web.UserLoginStatusListener;
import com.sinapsi.client.websocket.WSClient;
import com.sinapsi.engine.MacroEngine;
import com.sinapsi.engine.activation.ActivationManager;
import com.sinapsi.engine.component.ComponentFactory;
import com.sinapsi.engine.execution.ExecutionInterface;
import com.sinapsi.engine.execution.RemoteExecutionDescriptor;
import com.sinapsi.engine.execution.WebExecutionInterface;
import com.sinapsi.engine.log.SinapsiLog;
import com.sinapsi.engine.requirements.DefaultRequirementResolver;
import com.sinapsi.engine.system.PlatformDependantObjectProvider;
import com.sinapsi.model.DeviceInterface;
import com.sinapsi.model.MacroInterface;
import com.sinapsi.model.UserInterface;
import com.sinapsi.model.impl.CommunicationInfo;
import com.sinapsi.model.impl.FactoryModel;
import com.sinapsi.model.module.SinapsiModule;
import com.sinapsi.webshared.ComponentFactoryProvider;
import com.sinapsi.webshared.wsproto.SinapsiMessageTypes;
import com.sinapsi.webshared.wsproto.WebSocketEventHandler;
import com.sinapsi.webshared.wsproto.WebSocketMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.RestAdapter;

/**
 * This class implements runnable, so it can be executed on a separate thread(or not).
 * This is the platform-independent version of Sinapsi Android Client's SinapsiBackgroundService.
 */
public class SinapsiDaemonThread implements
        Runnable,
        OnlineStatusProvider,
        WebSocketEventHandler,
        UserLoginStatusListener,
        ComponentFactoryProvider {

    private final static FactoryModel fm = new FactoryModel();

    boolean running = true;
    private RetrofitWebServiceFacade web;
    private SafeSyncManager safeSyncManager;
    private MacroEngine engine;

    private SinapsiLog sinapsiLog;
    private DeviceInterface device;
    private Map<String, WebServiceConnectionListener> connectionListeners = new HashMap<>();

    private boolean started = false;

    private boolean onlineMode = false;
    private static final UserInterface logoutUser = fm.newUser(-1, "Not logged in yet.", "", false, "user");
    private UserInterface loggedUser = logoutUser;

    private UserSettingsFacade userSettings;
    private RestAdapter.Log retrofitLog;
    private final EncodingMethod encodingMethod;
    private final DecodingMethod decodingMethod;
    private ActivationManager activationManager;
    private DefaultRequirementResolver defaultRequirementResolver;
    private PlatformDependantObjectProvider objectProvider;
    private DBManagerProvider dbManagerProvider;
    private OnlineStatusProvider onlineStatusProvider;
    private SinapsiModule[] modules;
    private DaemonCallbacks daemonCallbacks;


    public SinapsiDaemonThread(
            UserSettingsFacade userSettings,
            SinapsiLog sinapsiLog,
            RestAdapter.Log retrofitLog,
            EncodingMethod encodingMethod,
            DecodingMethod decodingMethod,
            ActivationManager activationManager,
            DefaultRequirementResolver defaultRequirementResolver,
            PlatformDependantObjectProvider objectProvider,
            DBManagerProvider dbManagerProvider,
            OnlineStatusProvider onlineStatusProvider,
            DaemonCallbacks callback,
            SinapsiModule... modules
            ){

        this.userSettings = userSettings;
        this.sinapsiLog = sinapsiLog;
        this.retrofitLog = retrofitLog;
        this.encodingMethod = encodingMethod;
        this.decodingMethod = decodingMethod;
        this.activationManager = activationManager;
        this.defaultRequirementResolver = defaultRequirementResolver;
        this.objectProvider = objectProvider;
        this.dbManagerProvider = dbManagerProvider;
        this.onlineStatusProvider = onlineStatusProvider;
        this.daemonCallbacks = callback;
        this.modules = modules;
    }

    @Override
    public void run() {
        initializeDaemon();
        while(running) {
            //TODO: event queue handler?
            if (Thread.interrupted()) {
                return;
            }
        }
    }

    public void initializeDaemon() {
        //TODO: load settings

        //web service initialization ----
        if(encodingMethod == null || decodingMethod == null){
            web = new RetrofitWebServiceFacade(
                    retrofitLog,
                    this,
                    this,
                    this,
                    this
            );
        }else {
            web = new RetrofitWebServiceFacade(
                    retrofitLog,
                    this,
                    this,
                    this,
                    this,
                    encodingMethod,
                    decodingMethod
            );
        }


        if(AppConsts.DEBUG_BYPASS_LOGIN) mockLogin();
    }

    public void initEngine() {
        // ENGINE INITIALIZATION
        WebExecutionInterface defaultWebExecutionInterface = new WebExecutionInterface() {
            @Override
            public void continueExecutionOnDevice(ExecutionInterface ei, DeviceInterface di) {
                web.continueMacroOnDevice(
                        device,
                        di,
                        new RemoteExecutionDescriptor(
                                ei.getMacro().getId(),
                                ei.getLocalVars(),
                                ei.getExecutionStackIndexes()),
                        new SinapsiWebServiceFacade.WebServiceCallback<CommunicationInfo>() {
                            @Override
                            public void success(CommunicationInfo t, Object response) {
                                sinapsiLog.log("EXECUTION_CONTINUE", t.getAdditionalInfo());
                            }

                            @Override
                            public void failure(Throwable error) {
                                sinapsiLog.log("EXECUTION_CONTINUE", "FAIL");
                            }
                        });

            }
        };

        engine = new MacroEngine(
                device,
                activationManager,
                defaultWebExecutionInterface,
                defaultRequirementResolver,
                objectProvider,
                sinapsiLog,
                modules
        );
        // ENGINE INITIALIZATION END

        // SYNC MANAGER INITIALIZATION

        LocalDBManager ldbm_old;
        LocalDBManager ldbm_curr;
        DiffDBManager ddbm;

        if(loggedUser.getId() == logoutUser.getId()){
            ldbm_old = dbManagerProvider.openLocalDBManager("logout_user-lastSync.db", engine.getComponentFactory());
            ldbm_curr = dbManagerProvider.openLocalDBManager("logout_user-current.db", engine.getComponentFactory());
            ddbm = dbManagerProvider.openDiffDBManager("logout_user-diff.db");
        }else{
            ldbm_old = dbManagerProvider.openLocalDBManager(loggedUser.getEmail().replace('@', '_').replace('.', '_') + "-lastSync.db", engine.getComponentFactory());
            ldbm_curr = dbManagerProvider.openLocalDBManager(loggedUser.getEmail().replace('@', '_').replace('.', '_') + "-current.db", engine.getComponentFactory());
            ddbm = dbManagerProvider.openDiffDBManager(loggedUser.getEmail().replace('@', '_').replace('.', '_') + "-diff.db");
        }

        SyncManager syncManager = new SyncManager(
                web,
                ldbm_old,
                ldbm_curr,
                ddbm,
                device
        );

        safeSyncManager = new SafeSyncManager(syncManager, this);

        if(AppConsts.DEBUG_CLEAR_DB_ON_START) syncManager.clearAll();

        // SYNC MANAGER INITIALIZATION END

    }

    public void syncMacrosAndStartEngine(){
        // loads macros from local db/web service -------------------
        syncMacros(new BackgroundSyncCallback() {
            @Override
            public void onBackgroundSyncSuccess(List<MacroInterface> currentMacros) {
                engine.startEngine();
            }

            @Override
            public void onBackgroundSyncFail(Throwable error) {
                //try to start the engine anyway
                engine.startEngine();
            }
        }, false);


        daemonCallbacks.onEngineStarted();
    }


    /**
     * Loads all saved macros from a local db.
     */
    public void syncMacros(final BackgroundSyncCallback callback, final boolean userIntention) {

        safeSyncManager.getMacros(new BackgroundServiceInternalSyncCallback(callback, userIntention));
    }

    public void removeMacro(int id, final BackgroundSyncCallback callback, final boolean userIntention) {
        safeSyncManager.removeMacro(id, new BackgroundServiceInternalSyncCallback(callback, userIntention));
    }

    public void updateMacro(MacroInterface macro, final BackgroundSyncCallback callback, final boolean userIntention) {
        safeSyncManager.updateMacro(macro, new BackgroundServiceInternalSyncCallback(callback, userIntention));
    }

    public void addMacro(MacroInterface macro, final BackgroundSyncCallback callback, final boolean userIntention) {
        safeSyncManager.addMacro(macro, new BackgroundServiceInternalSyncCallback(callback, userIntention));
    }

    public List<MacroInterface> getMacros(){
        return new ArrayList<>(engine.getMacros().values());
    }

    /**
     * Adds a web service connection listener to the notification set.
     * From now on, when the online/offline mode changes, the specified listener
     * is notified.
     *
     * @param wscl the connection listener
     */
    public void addWebServiceConnectionListener(WebServiceConnectionListener wscl) {
        connectionListeners.put(wscl.getClass().getName(), wscl);
    }

    /**
     * Removes a web service connection listener to from the notification set.
     *
     * @param wscl the connection listener
     */
    public void removeWebServiceConnectionListener(WebServiceConnectionListener wscl) {
        connectionListeners.remove(wscl.getClass().getName());
    }

    private void notifyWebServiceConnectionListeners(boolean online) {
        for (WebServiceConnectionListener wscl : connectionListeners.values()) {
            if (online) wscl.onOnlineMode();
            else wscl.onOfflineMode();
        }
    }

    public void handleConflicts(List<MacroSyncConflict> conflicts, SyncManager.ConflictResolutionCallback callback) {
        daemonCallbacks.onSyncConflicts(conflicts, callback);
    }

    public void handleSyncFailure(Throwable e, boolean showError) {
        daemonCallbacks.onSyncFailure(e, showError);
    }

    private void mockLogin() {
        //TODO: impl
    }


    @Override
    public ComponentFactory getComponentFactory() {
        return engine.getComponentFactory();
    }



    @Override
    public boolean isOnline() {
        onlineMode = onlineStatusProvider.isOnline();
        notifyWebServiceConnectionListeners(onlineMode);
        return onlineMode;
    }



    @Override
    public void onUserLogIn(UserInterface user) {
        this.loggedUser = user;
        daemonCallbacks.onUserLogin(user);
    }

    @Override
    public void onUserLogOut() {
        this.loggedUser = logoutUser;
        pauseEngine();
        if(getWSClient().isOpen()) getWSClient().closeConnection();
        daemonCallbacks.onUserLogout();
    }

    public void pauseEngine() {
        engine.pauseEngine();
        daemonCallbacks.onEnginePaused();
    }

    public void resumeEngine() {
        engine.resumeEngine();
        daemonCallbacks.onEngineStarted();
    }

    public MacroInterface newEmptyMacro(){
        int id = safeSyncManager.getMinId() -1;
        return engine.newEmptyMacro(id);
    }

    @Override
    public void onWebSocketOpen() {
        daemonCallbacks.onWebSocketOpen();
    }

    @Override
    public void onWebSocketMessage(String message) {
        daemonCallbacks.onWebSocketMessage();
        Gson gson = new Gson();
        WebSocketMessage wsMsg = gson.fromJson(message, WebSocketMessage.class);
        switch (wsMsg.getMsgType()) {
            case SinapsiMessageTypes.REMOTE_EXECUTION_DESCRIPTOR:{
                final RemoteExecutionDescriptor red = gson.fromJson(wsMsg.getData(), RemoteExecutionDescriptor.class);
                if(!daemonCallbacks.onWebSocketRemoteExecutionDescriptorReceived(red))
                    try {
                        engine.continueMacro(red);
                    } catch (MacroEngine.MissingMacroException e){
                        syncMacros(new BackgroundSyncCallback() {
                            @Override
                            public void onBackgroundSyncSuccess(List<MacroInterface> currentMacros) {
                                try {
                                    engine.continueMacro(red);
                                } catch (MacroEngine.MissingMacroException e1) {
                                    e1.printStackTrace();
                                }
                            }

                            @Override
                            public void onBackgroundSyncFail(Throwable error) {
                                //TODO: a remote execution descriptor arrived,
                                //----:     the macro is not present on this
                                //----:     client, and an attempt to sync failed.
                                //----:     what to do?
                            }
                        }, false);
                    }
            }
            break;
            case SinapsiMessageTypes.MODEL_UPDATED_NOTIFICATION:{
                syncMacros(new BackgroundSyncCallback() {
                    @Override
                    public void onBackgroundSyncSuccess(List<MacroInterface> currentMacros) {
                        //do nothing
                    }

                    @Override
                    public void onBackgroundSyncFail(Throwable error) {
                        //do nothing
                    }
                }, false);
                daemonCallbacks.onWebSocketUpdatedNotificationReceived();
            }
            break;
            case SinapsiMessageTypes.NEW_CONNECTION: {
                daemonCallbacks.onWebSocketNewConnectionNotificationReceived();
            }
            break;
            case SinapsiMessageTypes.CONNECTION_LOST: {
                daemonCallbacks.onWebSocketConnectionLostNotificationReceived();
            }
            break;

        }
    }

    @Override
    public void onWebSocketError(Exception ex) {
        daemonCallbacks.onWebSocketError(ex);
    }

    @Override
    public void onWebSocketClose(int code, String reason, boolean remote) {
        daemonCallbacks.onWebSocketClose(code, reason, remote);
    }

    public boolean isStarted() {
        return started;
    }

    public RetrofitWebServiceFacade getWeb() {
        return web;
    }

    public MacroEngine getEngine() {
        return engine;
    }

    public SinapsiLog getSinapsiLog() {
        return sinapsiLog;
    }

    public DeviceInterface getDevice() {
        return device;
    }

    public UserInterface getLoggedUser() {
        return loggedUser;
    }

    public SinapsiModule[] getModules() {
        return modules;
    }

    public UserSettingsFacade getUserSettings() {
        return userSettings;
    }

    public WSClient getWSClient() {
        return web.getWebSocketClient();
    }

    public void setDevice(DeviceInterface device) {
        this.device = device;
    }

    public interface DBManagerProvider {
        public LocalDBManager openLocalDBManager(String fileName, ComponentFactory componentFactory);
        public DiffDBManager openDiffDBManager(String fileName);
    }

    public static interface BackgroundSyncCallback {
        public void onBackgroundSyncSuccess(List<MacroInterface> currentMacros);

        public void onBackgroundSyncFail(Throwable error);
    }

    private class BackgroundServiceInternalSyncCallback implements SyncManager.MacroSyncCallback {

        private final BackgroundSyncCallback callback;
        private final boolean userIntention;

        public BackgroundServiceInternalSyncCallback(BackgroundSyncCallback callback, boolean userIntention) {
            this.callback = callback;
            this.userIntention = userIntention;
        }

        @Override
        public void onSyncSuccess(List<MacroInterface> currentMacros) {
            engine.clearMacros();
            engine.addMacros(currentMacros);
            callback.onBackgroundSyncSuccess(currentMacros);
        }

        @Override
        public void onSyncConflicts(List<MacroSyncConflict> conflicts, SyncManager.ConflictResolutionCallback conflictCallback) {
            handleConflicts(conflicts, conflictCallback);
        }

        @Override
        public void onSyncFailure(Throwable error) {
            handleSyncFailure(error, userIntention);
            callback.onBackgroundSyncFail(error);
        }
    }

    public interface DaemonCallbacks {
        public void onEngineStarted();
        public void onEnginePaused();

        public void onUserLogin(UserInterface user);
        public void onUserLogout();

        public void onSyncConflicts(List<MacroSyncConflict> conflicts, SyncManager.ConflictResolutionCallback callback);
        public void onSyncFailure(Throwable e, boolean showError);

        public void onWebSocketError(Exception ex);
        public void onWebSocketClose(int code, String reason, boolean remote);
        public void onWebSocketOpen();
        public void onWebSocketMessage();
        public void onWebSocketUpdatedNotificationReceived();
        public void onWebSocketNewConnectionNotificationReceived();
        public void onWebSocketConnectionLostNotificationReceived();
        public boolean onWebSocketRemoteExecutionDescriptorReceived(RemoteExecutionDescriptor red);
    }
}
