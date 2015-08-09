package com.sinapsi.engine.modules.common;

import com.sinapsi.engine.modules.DefaultCoreModules;
import com.sinapsi.engine.activation.Event;
import com.sinapsi.engine.execution.ExecutionInterface;
import com.sinapsi.engine.component.Trigger;
import com.sinapsi.engine.parameters.ConnectionStatusChoices;
import com.sinapsi.engine.parameters.FormalParamBuilder;
import com.sinapsi.engine.parameters.SwitchStatusChoices;
import com.sinapsi.engine.annotations.Component;
import com.sinapsi.engine.annotations.Requirement;
import com.sinapsi.engine.annotations.Requires;
import com.sinapsi.model.module.ModuleMember;
import com.sinapsi.utils.JSONUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * TriggerWifi class. This trigger will activate a macro when
 * the wifi state or the wifi connection state changes. This
 * trigger can be parametrized by the status or by the ssid the
 * wifi is connected to.
 * Notice that this trigger is completely platform-independent:
 * it relies on other facades/adapters like SystemFacade and WifiAdapter.
 */
@ModuleMember(DefaultCoreModules.ANTARES_COMMON_COMPONENTS_MODULE_NAME)
@Component(TriggerWifi.TRIGGER_WIFI)
@Requires({
        @Requirement(value = 1, name = DefaultCoreModules.REQUIREMENT_WIFI)
})
public class TriggerWifi extends Trigger {

    public static final String TRIGGER_WIFI = "TRIGGER_WIFI";



    @Override
    protected JSONObject extractParameterValues(Event e, ExecutionInterface di) throws JSONException {
        WifiAdapter wa = (WifiAdapter) di.getSystemFacade().getSystemService(WifiAdapter.ADAPTER_WIFI);
        return new JSONObject() //FIXME: change, check from Event date (intent extras on android)
                .put("wifi_status", wa.getStatus().toString())
                .put("wifi_connection_status", wa.getConnectionStatus().toString())
                .put("wifi_ssid", wa.getSSID());
    }


    @Override
    public JSONObject getFormalParametersJSON() throws JSONException{
        return new FormalParamBuilder()
                .put("wifi_status", JSONUtils.enumValuesToJSONArray(SwitchStatusChoices.class), true)
                .put("wifi_connection_status", JSONUtils.enumValuesToJSONArray(ConnectionStatusChoices.class), true)
                .put("wifi_ssid", FormalParamBuilder.Types.STRING, true)
                .create();

    }


}
