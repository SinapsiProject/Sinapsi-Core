package com.sinapsi.engine.components.common;

import com.sinapsi.engine.DefaultCoreModules;
import com.sinapsi.engine.Event;
import com.sinapsi.engine.SinapsiVersions;
import com.sinapsi.engine.Trigger;
import com.sinapsi.engine.execution.ExecutionInterface;
import com.sinapsi.engine.parameters.FormalParamBuilder;
import com.sinapsi.engine.system.CommonDeviceConsts;
import com.sinapsi.engine.system.annotations.Component;
import com.sinapsi.engine.system.annotations.Requirement;
import com.sinapsi.engine.system.annotations.Requires;
import com.sinapsi.model.module.SinapsiModuleDescriptor;
import com.sinapsi.utils.HashMapBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * TriggerScreenPower class. This trigger will activate a macro when
 * the device's main screen turns on or off. This
 * trigger can be parametrized by the status of the screen power.
 */
@Component(TriggerScreenPower.TRIGGER_SCREEN_POWER)
@Requires({
        @Requirement(value = 1, name = CommonDeviceConsts.REQUIREMENT_INTERCEPT_SCREEN_POWER)
})
public class TriggerScreenPower extends Trigger {

    public static final String TRIGGER_SCREEN_POWER = "TRIGGER_SCREEN_POWER";

    @Override
    public JSONObject getFormalParametersJSON() throws JSONException {
        return new FormalParamBuilder()
                .put("screen_power", FormalParamBuilder.BoolStyles.ON_OFF, true)
                .create();
    }

    @Override
    protected JSONObject extractParameterValues(Event e, ExecutionInterface di) throws JSONException {
        return e.getJSONObject();
    }

    @Override
    public SinapsiModuleDescriptor getBelongingSinapsiModule() {
        return DefaultCoreModules.ANTARES_COMMONS_MODULE;
    }
}
