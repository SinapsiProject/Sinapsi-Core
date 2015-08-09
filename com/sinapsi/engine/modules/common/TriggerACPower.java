package com.sinapsi.engine.modules.common;

import com.sinapsi.engine.modules.DefaultCoreModules;
import com.sinapsi.engine.activation.Event;
import com.sinapsi.engine.component.Trigger;
import com.sinapsi.engine.execution.ExecutionInterface;
import com.sinapsi.engine.parameters.FormalParamBuilder;
import com.sinapsi.engine.annotations.Component;
import com.sinapsi.engine.annotations.Requirement;
import com.sinapsi.engine.annotations.Requires;
import com.sinapsi.model.module.ModuleMember;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * TriggerACPower class. This trigger will activate a macro when
 * the AC charger is connected or disconnected.
 */
@ModuleMember(DefaultCoreModules.ANTARES_COMMON_COMPONENTS_MODULE_NAME)
@Component(TriggerACPower.TRIGGER_AC_POWER)
@Requires({
        @Requirement(value = 1, name = DefaultCoreModules.REQUIREMENT_AC_CHARGER)
})
public class TriggerACPower extends Trigger {

    public static final String TRIGGER_AC_POWER = "TRIGGER_AC_POWER";

    @Override
    public JSONObject getFormalParametersJSON() throws JSONException {
        return new FormalParamBuilder()
                .put("ac_power", FormalParamBuilder.BoolStyles.ON_OFF, true)
                .create();
    }

    @Override
    protected JSONObject extractParameterValues(Event e, ExecutionInterface di) throws JSONException {
        return e.getJSONObject();
    }
}
