package com.sinapsi.client.websocket;

import com.google.gson.Gson;
import com.sinapsi.client.websocket.WSClient;
import com.sinapsi.webshared.wsproto.WebSocketEventHandler;
import com.sinapsi.webshared.wsproto.WebSocketMessage;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;

/**
 * Created by Giuseppe on 11/06/15.
 */
public class ClientAdapter <T1, T2> implements WebSocketEventHandler {

    public static final String REFLECTION_METHOD_CALL = "REFLECTION_METHOD_CALL";


    private InvocationHandler ih;

    WebSocketEventHandler handlerCallback;
    private Class<T1> txInterface;
    private T2 rxImplementation;
    private Gson gson;


    public ClientAdapter(final WSClient wsClient, WebSocketEventHandler handlerCallback, Class<T1> txInterFace, T2 rxInterfaceImplementation, final Gson gson){
        this.handlerCallback = handlerCallback;
        this.txInterface = txInterFace;
        this.rxImplementation = rxInterfaceImplementation;
        this.gson = gson;
        validateServiceClass(txInterFace);
        this.ih = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                if(method.getGenericReturnType() != Void.TYPE) throw new RuntimeException("The method "+method.getName()+" must return void.") ;


                String[] parJSONs = new String[args.length];
                for(int i = 0; i < args.length; ++i){
                    parJSONs[i] = gson.toJson(args[i]);
                }

                ReflectiveMethodCall rmc = new ReflectiveMethodCall(method.toGenericString(), parJSONs);

                String data = gson.toJson(rmc);
                WebSocketMessage wsm = new WebSocketMessage(REFLECTION_METHOD_CALL, data);
                wsClient.send(gson.toJson(wsm));

                return null;
            }
        };


    }

    @SuppressWarnings("unchecked")
    public <T> T build(){
        return (T) Proxy.newProxyInstance(
                txInterface.getClassLoader(),
                new Class[]{txInterface},
                ih);
    }


    static void validateServiceClass(Class<?> interFace) {
        if (!interFace.isInterface()) {
            throw new IllegalArgumentException("Only interface endpoint definitions are supported.");
        }
        // Prevent API interfaces from extending other interfaces. This not only avoids a bug in
        // Android (http://b.android.com/58753) but it forces composition of API declarations which is
        // the recommended pattern.
        if (interFace.getInterfaces().length > 0) {
            throw new IllegalArgumentException("Interface definitions must not extend other interfaces.");
        }
    }

    @Override
    public void onWebSocketOpen() {
        handlerCallback.onWebSocketOpen();
    }

    @Override
    public void onWebSocketMessage(String message) {
        WebSocketMessage wsMsg = gson.fromJson(message, WebSocketMessage.class);
        if(wsMsg.getMsgType().equals(REFLECTION_METHOD_CALL)){
            ReflectiveMethodCall rmc = gson.fromJson(wsMsg.getData(), ReflectiveMethodCall.class);
            Method[] methods = rxImplementation.getClass().getMethods();

            for(Method m: methods){
                if(rmc.getMethodName().equals(m.toGenericString())){
                        Object[] parameters = new Object[rmc.getParameters().length];
                        for(int i = 0; i < rmc.getParameters().length; ++i){
                            String parameter = rmc.getParameters()[i];
                            parameters[i] = gson.fromJson(parameter, m.getParameterTypes()[i]);
                        }
                        try {
                            m.invoke(rxImplementation, parameters);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    break;
                }
            }
        }else{
            handlerCallback.onWebSocketMessage(message);
        }
    }

    @Override
    public void onWebSocketError(Exception ex) {
        handlerCallback.onWebSocketError(ex);
    }

    @Override
    public void onWebSocketClose(int code, String reason, boolean remote) {
        handlerCallback.onWebSocketClose(code, reason, remote);
    }


    public class ReflectiveMethodCall {
        private String methodName;
        private String[] parameters;

        public ReflectiveMethodCall(String methodName, String[] parameters) {
            this.methodName = methodName;
            this.parameters = parameters;
        }

        public String getMethodName() {
            return methodName;
        }

        public String[] getParameters(){
            return parameters;
        }
    }

}
