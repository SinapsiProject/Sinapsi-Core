package com.sinapsi.engine;

import com.sinapsi.engine.execution.ExecutionInterface;
import com.sinapsi.engine.execution.RemoteExecutionDescriptor;
import com.sinapsi.engine.log.SinapsiLog;
import com.sinapsi.model.DeviceInterface;
import com.sinapsi.model.MacroComponent;
import com.sinapsi.model.MacroInterface;
import com.sinapsi.model.impl.ActionDescriptor;
import com.sinapsi.model.impl.FactoryModel;
import com.sinapsi.model.impl.TriggerDescriptor;
import com.sinapsi.model.module.SinapsiModule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Macro engine class. Used to initialize the whole macro execution system
 * and to keep a list of all defined macros.
 */
public class MacroEngine {

    private FactoryModel fm = new FactoryModel();
    private DeviceInterface device;
    private ActivationManager activator;
    private ComponentFactory factory;
    private SinapsiLog log;

    private HashMap<Integer,MacroInterface> macros = new HashMap<>();

    /**
     * Creates a new MacroEngine instance with a custom component
     * class set.
     * @param currentDevice the devices on which this engine is running
     * @param activationManager the activation manager for trigger activation
     * @param sinapsiLog the sinapsi log
     * @param componentClasses the set of component classes
     */
    @SafeVarargs
    public MacroEngine(DeviceInterface currentDevice,
                       ActivationManager activationManager,
                       SinapsiLog sinapsiLog,
                       Class<? extends MacroComponent>... componentClasses){
        device = currentDevice;
        activator = activationManager;
        log = sinapsiLog;

        factory = new ComponentFactory(device, log, componentClasses);

        sinapsiLog.log("MACROENGINE", "Engine initialized.");
    }

    public MacroEngine(DeviceInterface currentDevice,
                       ActivationManager activationManager,
                       SinapsiLog sinapsiLog,
                       SinapsiModule... modules){
        device = currentDevice;
        activator = activationManager;
        log = sinapsiLog;

        EngineModuleManager moduleManager = new EngineModuleManager(this, modules);
        Class<? extends MacroComponent>[] componentClasses = moduleManager.getAllComponentClasses();

        factory = new ComponentFactory(device, log, componentClasses);

        sinapsiLog.log("MACROENGINE", "Engine initialized.");
    }


    /**
     * Getter of the ComponentFactory instance generated by this
     * MacroEngine
     * @return the component factory
     */
    public ComponentFactory getComponentFactory(){
        return factory;
    }

    /**
     * Adds a new macro to the list of defined macros. When added,
     * the macro's trigger is registered on the ActivationManager
     * and starts listening for system events.
     * @param m the macro
     */
    public void addMacro(MacroInterface m){
        if(m.getTrigger().getExecutionDevice().getId() == device.getId())
            m.getTrigger().register(activator);

        macros.put(m.getId(), m);
        log.log("MACROENGINE", "Added macro " + m.getId() + ":'" + m.getName()+"' to the engine");
    }

    /**
     * Adds all the macros in the collection to the list of defined
     * macros, registering every trigger on the ActivationManager.
     * @param lm the collection of macros
     */
    public void addMacros(Collection<MacroInterface> lm){
        for(MacroInterface m: lm){
            addMacro(m);
        }
        log.log("MACROENGINE", "Added " + lm.size() + " macros to the engine");
    }

    public void startEngine(){
        log.log("MACROENGINE", "Engine started.");
        activator.setEnabled(true);
        activator.activateForOnEngineStart();
    }

    private MacroInterface getMacroById(int idMacro){
        return macros.get(idMacro);
    }

    public void continueMacro(RemoteExecutionDescriptor red) throws MissingMacroException{

        ExecutionInterface ei = activator.executionInterface.cloneInstance();
        MacroInterface m = getMacroById(red.getIdMacro());

        if(m == null) throw new MissingMacroException();

        ei.continueExecutionFromRemote(m, red.getLocalVariables(), red.getStack());
        log.log("MACROENGINE", "Continuing execution of macro with name '" + m.getName() + "'");
        ei.execute();
    }

    public void pauseEngine(){
        activator.setEnabled(false);
    }

    public void resumeEngine(){
        activator.setEnabled(true);
    }

    public boolean isPaused(){
        return activator.isEnabled();
    }

    public HashMap<Integer, MacroInterface> getMacros() {
        return macros;
    }

    public void setMacroEnabled(int id, boolean enabled) throws MissingMacroException {
        MacroInterface m = getMacroById(id);
        if(m == null) throw new MissingMacroException();
        m.setEnabled(enabled);

    }

    public void removeMacro(int id) throws MissingMacroException {
        MacroInterface m = getMacroById(id);
        if(m == null) throw new MissingMacroException();
        if(m.getTrigger().getExecutionDevice().getId() == device.getId()){
            m.getTrigger().unregister(activator);
        }
        macros.remove(id);
    }

    public void clearMacros() {
        for(MacroInterface m: macros.values()){
            if(m.getTrigger().getExecutionDevice().getId() == device.getId()){
                m.getTrigger().unregister(activator);
            }
        }
        macros.clear();
    }

    public MacroInterface newEmptyMacro(int id){
        MacroInterface result = fm.newMacro("", id);
        result.setTrigger(getComponentFactory().newEmptyTrigger(result));
        return result;
    }

    public List<TriggerDescriptor> getAvailableTriggerDescriptors(){
        List<TriggerDescriptor> result = new ArrayList<>();
        result.addAll(getComponentFactory().getAvailableTriggerDescriptors(activator.executionInterface.getSystemFacade()));
        return result;
    }

    public List<ActionDescriptor> getAvailableActionDescriptors(){
        List<ActionDescriptor> result = new ArrayList<>();
        result.addAll(getComponentFactory().getAvailableActionDescriptors(activator.executionInterface.getSystemFacade()));
        return result;
    }
    
    public DeviceInterface getDevice() {
       return this.device;
    }

    public class MissingMacroException extends Exception {
    }
}
