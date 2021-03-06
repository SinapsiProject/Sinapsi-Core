package com.sinapsi.engine.modules.common;

import com.sinapsi.engine.modules.DefaultCoreModules;
import com.sinapsi.engine.activation.Event;
import com.sinapsi.engine.execution.ExecutionInterface;
import com.sinapsi.engine.component.Trigger;
import com.sinapsi.engine.parameters.FormalParamBuilder;
import com.sinapsi.engine.annotations.Component;
import com.sinapsi.engine.annotations.Requirement;
import com.sinapsi.engine.annotations.Requires;
import com.sinapsi.model.module.ModuleMember;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * TriggerSMS class. This trigger will activate a macro when
 * a new SMS message arrives on the device. This trigger can
 * be parametrized by the sender or/and by the content of the
 * message.
 */
@ModuleMember(DefaultCoreModules.ANTARES_COMMON_COMPONENTS_MODULE_NAME)
@Component(TriggerSMS.TRIGGER_SMS)
@Requires({
        @Requirement(value = 1, name = DefaultCoreModules.REQUIREMENT_SMS_READ)
})
public class TriggerSMS extends Trigger{

    public static final String TRIGGER_SMS = "TRIGGER_SMS";

    @Override
    public JSONObject getFormalParametersJSON() throws JSONException {
        return new FormalParamBuilder()
                .put("sender_number", FormalParamBuilder.Types.STRING, true)
                .putAdvancedString("message_content", true)
                .create();
    }

    @Override
    protected JSONObject extractParameterValues(Event e, ExecutionInterface di) throws JSONException {
        return e.getJSONObject();
    }

}
