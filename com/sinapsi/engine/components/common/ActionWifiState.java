package com.sinapsi.engine.components.common;

import com.sinapsi.engine.DefaultCoreModules;
import com.sinapsi.engine.execution.ExecutionInterface;
import com.sinapsi.engine.system.WifiAdapter;
import com.sinapsi.engine.Action;
import com.sinapsi.engine.parameters.FormalParamBuilder;
import com.sinapsi.engine.system.annotations.Component;
import com.sinapsi.engine.system.annotations.Requirement;
import com.sinapsi.engine.system.annotations.Requires;
import com.sinapsi.model.module.ModuleMember;
import com.sinapsi.model.module.SinapsiModuleDescriptor;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * ActionWifiState class. This Action will turn on or off the
 * system's default wifi adapter. This action must be parametrized
 * to tell at execution phase if the wifi adapter must be turned on
 * or off.
 * Notice that this action is completely platform-independent:
 * it relies on other facades/adapters like SystemFacade and
 * WifiAdapter.
 */
@ModuleMember(DefaultCoreModules.ANTARES_COMMON_COMPONENTS_MODULE_NAME)
@Component(ActionWifiState.ACTION_WIFI_STATE)
@Requires({
        @Requirement(value = 1,name = DefaultCoreModules.REQUIREMENT_WIFI)
})
public class ActionWifiState extends Action{

    public static final String ACTION_WIFI_STATE = "ACTION_WIFI_STATE";

    @Override
    public void onActivate(final ExecutionInterface s) throws JSONException{

        WifiAdapter wa = (WifiAdapter) s.getSystemFacade().getSystemService(WifiAdapter.ADAPTER_WIFI);
        JSONObject pjo = getParsedParams(s.getLocalVars(),s.getGlobalVars());
        boolean activate;

        activate = pjo.getBoolean("wifi_switch");

        if (activate) wa.setStatus(true);
        else wa.setStatus(false);

    }

    @Override
    public JSONObject getFormalParametersJSON() throws JSONException {
        return new FormalParamBuilder()
                .put("wifi_switch", FormalParamBuilder.BoolStyles.ON_OFF,false)
                .create();
    }

}
