package com.sinapsi.engine.modules.common;

import com.sinapsi.engine.modules.DefaultCoreModules;
import com.sinapsi.engine.execution.ExecutionInterface;
import com.sinapsi.engine.component.Action;
import com.sinapsi.engine.parameters.FormalParamBuilder;
import com.sinapsi.engine.annotations.Component;
import com.sinapsi.engine.annotations.Requirement;
import com.sinapsi.engine.annotations.Requires;
import com.sinapsi.model.module.ModuleMember;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * ActionSimpleNotification class. This Action will show a
 * notification message.
 * This action must be parametrized to know at execution phase
 * the text of th message and the title.
 * Notice that this action is completely platform-independent:
 * it relies on other facades/adapters like SystemFacade and
 * NotificationAdapter.
 */
@ModuleMember(DefaultCoreModules.ANTARES_COMMON_COMPONENTS_MODULE_NAME)
@Component(ActionSimpleNotification.ACTION_SIMPLE_NOTIFICATION)
@Requires({
        @Requirement(value = 1, name = DefaultCoreModules.REQUIREMENT_SIMPLE_NOTIFICATIONS)
})
public class ActionSimpleNotification extends Action {

    public static final String ACTION_SIMPLE_NOTIFICATION = "ACTION_SIMPLE_NOTIFICATION";

    @Override
    public void onActivate(ExecutionInterface di) throws JSONException{
        JSONObject pjo = getParsedParams(di.getLocalVars(),di.getGlobalVars());
        String title = null;
        String message = null;

        title = pjo.getString("notification_title");
        message = pjo.getString("notification_message");

        ((NotificationAdapter) di.getSystemFacade().getSystemService(NotificationAdapter.ADAPTER_NOTIFICATION)).showSimpleNotification(title,message);
    }

    @Override
    public JSONObject getFormalParametersJSON() throws JSONException {
        return new FormalParamBuilder()
                .put("notification_title", FormalParamBuilder.Types.STRING, false)
                .put("notification_message", FormalParamBuilder.Types.STRING, false)
                .create();
    }

}
