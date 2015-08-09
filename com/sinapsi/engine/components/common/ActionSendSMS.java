package com.sinapsi.engine.components.common;

import com.sinapsi.engine.DefaultCoreModules;
import com.sinapsi.engine.execution.ExecutionInterface;
import com.sinapsi.engine.system.SMSAdapter;
import com.sinapsi.engine.Action;
import com.sinapsi.engine.parameters.FormalParamBuilder;
import com.sinapsi.engine.system.annotations.Component;
import com.sinapsi.engine.system.annotations.Requirement;
import com.sinapsi.engine.system.annotations.Requires;
import com.sinapsi.model.module.ModuleMember;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * ActionSendSMS class. This Action will send a new sms.
 * This action must be parametrized to know at execution phase
 * the text message body and the recipient number
 * Notice that this action is completely platform-independent:
 * it relies on other facades/adapters like SystemFacade and
 * SMSAdapter.
 */
@ModuleMember(DefaultCoreModules.ANTARES_COMMON_COMPONENTS_MODULE_NAME)
@Component(ActionSendSMS.ACTION_SEND_SMS)
@Requires({
        @Requirement(value = 1, name = DefaultCoreModules.REQUIREMENT_SMS_SEND)
})
public class ActionSendSMS extends Action {


    public static final String ACTION_SEND_SMS = "ACTION_SEND_SMS";

    @Override
    public void onActivate(final ExecutionInterface ei) throws JSONException{
        SMSAdapter sa = (SMSAdapter) ei.getSystemFacade().getSystemService(SMSAdapter.ADAPTER_SMS);
        JSONObject pjo = getParsedParams(ei.getLocalVars(), ei.getGlobalVars());
        SMSAdapter.Sms sms = new SMSAdapter.Sms();

        sms.setAddress(pjo.getString("number"));
        sms.setMsg(pjo.getString("msg"));

        sa.sendSMSMessage(sms);//HINT: check returned boolean
    }

    @Override
    public JSONObject getFormalParametersJSON() throws JSONException {
        return new FormalParamBuilder()
                .put("number", FormalParamBuilder.Types.STRING, false)
                .put("msg", FormalParamBuilder.Types.STRING, false)
                .create();
    }

}
