package com.sinapsi.engine.components.common;

import com.sinapsi.engine.DefaultCoreModules;
import com.sinapsi.engine.execution.ExecutionInterface;
import com.sinapsi.engine.system.DialogAdapter;
import com.sinapsi.engine.Action;
import com.sinapsi.engine.parameters.FormalParamBuilder;
import com.sinapsi.engine.system.annotations.Component;
import com.sinapsi.engine.system.annotations.Requirement;
import com.sinapsi.engine.system.annotations.Requires;
import com.sinapsi.model.module.SinapsiModuleDescriptor;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * ActionContinueConfirmDialog class. This Action will show a
 * dialog to ask the user to confirm the execution of the
 * rest of the macro.
 * This is a good example of how the ExecutionInterface class can
 * be used by a component. When the action is activated, the dialog
 * is showed and the execution of the macro is paused. When the user
 * chooses "Yes" on the dialog, the execution of the macro is continues.
 * If the user chooses "No" then the execution of the macro is cancelled.
 * This action must be parametrized in order to know at execution phase
 * the text message in the dialog.
 * Notice that this action is completely platform-independent:
 * it relies on ExecutionInterface and DialogAdapter.
 */
@Component(ActionContinueConfirmDialog.ACTION_CONTINUE_CONFIRM_DIALOG)
@Requires({
        @Requirement(value = 1, name = DefaultCoreModules.REQUIREMENT_SIMPLE_DIALOGS)
})
public class ActionContinueConfirmDialog extends Action {

    public static final String ACTION_CONTINUE_CONFIRM_DIALOG = "ACTION_CONTINUE_CONFIRM_DIALOG";

    @Override
    public void onActivate(final ExecutionInterface di) throws JSONException{
        DialogAdapter da = (DialogAdapter) di.getSystemFacade().getSystemService(DialogAdapter.ADAPTER_DIALOGS);
        JSONObject pjo = getParsedParams(di.getLocalVars(),di.getGlobalVars());
        String message = pjo.getString("dialog_message");
        String title = pjo.getString("dialog_title");

        da.showSimpleConfirmDialog(message, title, new DialogAdapter.OnDialogChoiceListener() {
            @Override
            public void onDialogChoice() {
                di.unpause();
            }
        }, new DialogAdapter.OnDialogChoiceListener() {
            @Override
            public void onDialogChoice() {
                di.cancel();
            }
        });

        di.pause();
    }

    @Override
    public JSONObject getFormalParametersJSON() throws JSONException {
        return new FormalParamBuilder()
                .put("dialog_message", FormalParamBuilder.Types.STRING, false)
                .put("dialog_title", FormalParamBuilder.Types.STRING, false)
                .create();
    }

    @Override
    public SinapsiModuleDescriptor getBelongingSinapsiModule() {
        return DefaultCoreModules.ANTARES_COMMONS_MODULE;
    }
}
