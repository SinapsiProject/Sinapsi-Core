package com.sinapsi.engine;

import com.sinapsi.engine.components.core.TriggerEngineStart;
import com.sinapsi.engine.execution.ExecutionInterface;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Abstract class to allow those triggers that are registered
 * to for system events to be notified for activation.
 * Every platform should extend this class.
 */
public abstract class ActivationManager {

    protected ExecutionInterface executionInterface;

    private List<Trigger> engineStartTriggers = new ArrayList<>();
    private boolean enabled = false;

    /**
     * Registers a trigger for activation.
     * Override this to handle system dependent trigger registration
     * @param t the trigger
     */
    public void addToNotifyList(Trigger t){
        executionInterface.getLog().log("ACTIVMAN", "Registering trigger '"+ t.getName() +"' for event listening.");
        if(t.getName().equals(TriggerEngineStart.TRIGGER_ENGINE_START)) engineStartTriggers.add(t);
    }

    /**
     * Unregisters the specified trigger.
     * Override this to handle system dependent trigger unregistration
     * @param t the trigger
     */
    public void removeFromNotifyList(Trigger t){
        executionInterface.getLog().log("ACTIVMAN", "Unregistering trigger '"+ t.getName() +"' for event listening.");
        if(t.getName().equals(TriggerEngineStart.TRIGGER_ENGINE_START)) engineStartTriggers.remove(t);
    }

    /**
     * Called by the engine to initialize the ActivatorManager
     */
    public void init(ExecutionInterface defaultExecutionInterface){
        this.executionInterface = defaultExecutionInterface;
    }

    /**
     * Internal event, called by MacroEngine class, useful to activate the
     * trigger TriggerEngineStart.
     */
    public void activateForOnEngineStart(){
        if(!enabled) return;
        executionInterface.getLog().log("ACTIVMAN", "OnEngineStart event occurred.");
        Event e = new Event();
        e.put("time_stamp", Calendar.getInstance().getTime());
        for(Trigger t: engineStartTriggers){
            ExecutionInterface ei = executionInterface.cloneInstance();
            t.activate(e, ei);
        }
    }

    /**
     * Enabled state setter.
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Enabled state getter.
     */
    public boolean isEnabled(){
        return enabled;
    }
}
