package com.sinapsi.engine.system;

import com.sinapsi.engine.ComponentSystemAdapter;
import com.sinapsi.engine.system.annotations.AdapterInterface;

/**
 * Indipendent system interface that gives a way to return name and model of the device
 */
@AdapterInterface(DeviceInfoAdapter.ADAPTER_DEVICE_INFO)
public interface DeviceInfoAdapter extends ComponentSystemAdapter {

    public static final String ADAPTER_DEVICE_INFO = "ADAPTER_DEVICE_INFO";

    /**
     * Return the name od the device
     * @return
     */
    public String getDeviceName();

    /**
     * Return the model of the device
     * @return
     */
    public String getDeviceModel();

    /**
     * Return the type od the device
     * @return
     */
    public String getDeviceType();

}
