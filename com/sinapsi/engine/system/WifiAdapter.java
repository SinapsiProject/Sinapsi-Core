package com.sinapsi.engine.system;

import com.sinapsi.engine.ComponentSystemAdapter;
import com.sinapsi.engine.parameters.ConnectionStatusChoices;
import com.sinapsi.engine.parameters.SwitchStatusChoices;
import com.sinapsi.engine.system.annotations.AdapterInterface;

/**
 * Interface used to adapt various system calls
 * to get wifi infos or manage wifi status or connections.
 *
 */
@AdapterInterface(WifiAdapter.ADAPTER_WIFI)
public interface WifiAdapter extends ComponentSystemAdapter {

    public static final String ADAPTER_WIFI = "ADAPTER_WIFI";
    public static final String REQUIREMENT_WIFI = "REQUIREMENT_WIFI";

    /**
     * Getter of the main wifi adapter's status.
     * @return ENABLED, DISABLED, ENABLING or DISABLING
     */
    public SwitchStatusChoices getStatus();

    /**
     * Getter of the main wifi connection's status.
     * @return CONNECTED, DISCONNECTED, DISCONNECTING or CONNECTING
     */
    public ConnectionStatusChoices getConnectionStatus();

    /**
     * Gets the SSID of the connected wifi network
     * @return the SSID
     */
    public String getSSID();

    /**
     * Attempts to connect to the given SSID
     * @param id the SSID
     */
    public void connectToSSID(String id);

    /**
     * Sets the main wifi adapter's status
     * @param status true for enable, false for disable.
     */
    public void setStatus(boolean status);

    /**
     * Sets the main wifi connection's status.
     * @param status true for connect, false for disconnect.
     */
    public void setConnectionStatus(boolean status);
}
