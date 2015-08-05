package com.sinapsi.engine.components.core;

import com.sinapsi.engine.DefaultCoreModules;
import com.sinapsi.engine.Event;
import com.sinapsi.engine.execution.ExecutionInterface;
import com.sinapsi.engine.Trigger;
import com.sinapsi.engine.system.annotations.Component;
import com.sinapsi.engine.system.annotations.Requirement;
import com.sinapsi.engine.system.annotations.Requires;
import com.sinapsi.model.module.SinapsiModuleDescriptor;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * TriggerEngineStart class. This trigger will activate a macro when
 * the Sinapsi engine starts up. This trigger cannot be parametrized.
 */
@Component(TriggerEngineStart.TRIGGER_ENGINE_START)
@Requires({
        @Requirement(value = 1, name = DefaultCoreModules.REQUIREMENT_RESTARTABLE_MACRO_ENGINE)
})
public class TriggerEngineStart extends Trigger{
    public static final String TRIGGER_ENGINE_START = "TRIGGER_ENGINE_START";

    @Override
    public JSONObject getFormalParametersJSON() throws JSONException {
        return null; /*No parameter available for now, however, in the event is stored
                     the timestamp of the Engine startup.*/
    }

    @Override
    protected JSONObject extractParameterValues(Event e, ExecutionInterface di) throws JSONException {
        return null; //This trigger will always activate his macro
    }

    @Override
    public SinapsiModuleDescriptor getBelongingSinapsiModule() {
        return DefaultCoreModules.ANTARES_CORE_MODULE;
    }
}
