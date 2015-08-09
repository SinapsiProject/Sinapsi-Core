package com.sinapsi.engine.components.core;

import com.sinapsi.engine.DefaultCoreModules;
import com.sinapsi.engine.SinapsiPlatforms;
import com.sinapsi.engine.SinapsiVersions;
import com.sinapsi.engine.execution.ExecutionInterface;
import com.sinapsi.engine.Action;
import com.sinapsi.engine.parameters.FormalParamBuilder;
import com.sinapsi.engine.system.annotations.Component;
import com.sinapsi.engine.system.annotations.Requires;
import com.sinapsi.model.MacroComponent;
import com.sinapsi.model.module.ModuleMember;
import com.sinapsi.model.module.SinapsiModuleDescriptor;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * ActionLog class. This Action will put a message in the Sinapsi
 * log system.
 * This action must be parametrized to know at execution phase
 * the log message.
 * Notice that this action is completely platform-independent:
 * it relies on other facades/adapters in ExecutionInterface.
 */
@ModuleMember(DefaultCoreModules.ANTARES_CORE_MODULE_NAME)
@Component(ActionLog.ACTION_LOG)
@Requires({})
public class ActionLog extends Action {

    public static final String ACTION_LOG = "ACTION_LOG";

    @Override
    public void onActivate(ExecutionInterface di) throws JSONException{
        JSONObject pjo = getParsedParams(di.getLocalVars(),di.getGlobalVars());
        String message = null;

        message = pjo.getString("log_message");

        di.getLog().log("LOG_ACTION", message);
    }

    @Override
    public JSONObject getFormalParametersJSON() throws JSONException {
        return new FormalParamBuilder()
                .put("log_message", FormalParamBuilder.Types.STRING, false)
                .create();
    }

}
