package com.sinapsi.model;

import com.sinapsi.model.impl.CommunicationInfo;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Abstract data type representing a collection of devices with related online/offline status.
 */
public class DevicesStatus extends CommunicationInfo{

    private Map<Integer, DeviceInterface> devices = new HashMap<>();
    private Map<Integer, Boolean> statuses = new HashMap<>();

    public void putDeviceStatus(DeviceInterface device, Boolean status){
        devices.put(device.getId(), device);
        statuses.put(device.getId(), status);
    }

    public DeviceInterface getDevice(int id){
        return devices.get(id);
    }

    public Collection<DeviceInterface> getDevices(){
        return devices.values();
    }

    public boolean getStatus(int id){
        return statuses.get(id);
    }

    public Set<Integer> getIDSet(){
        return devices.keySet();
    }

    public void clear(){
        statuses.clear();
        devices.clear();
    }

    public void remove(int id){
        statuses.remove(id);
        devices.remove(id);
    }
}
