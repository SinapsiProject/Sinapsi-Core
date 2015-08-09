package com.sinapsi.engine.modules.core;

import com.sinapsi.engine.modules.DefaultCoreModules;
import com.sinapsi.engine.activation.Event;
import com.sinapsi.engine.execution.ExecutionInterface;
import com.sinapsi.engine.component.Trigger;
import com.sinapsi.engine.annotations.Component;
import com.sinapsi.engine.annotations.Requirement;
import com.sinapsi.engine.annotations.Requires;
import com.sinapsi.model.module.ModuleMember;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * TriggerEngineStart class. This trigger will activate a macro when
 * the Sinapsi engine starts up. This trigger cannot be parametrized.
 */
@ModuleMember(DefaultCoreModules.ANTARES_CORE_MODULE_NAME)
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

}
