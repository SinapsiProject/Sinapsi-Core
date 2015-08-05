package com.sinapsi.engine.system;

/**
 * Indipendent system interface that gives a way to return name and model of the device
 */
public interface DeviceInfoAdapter extends ComponentSystemAdapter {

    public static final String SERVICE_DEVICE_INFO = "SERVICE_DEVICE_INFO";

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
