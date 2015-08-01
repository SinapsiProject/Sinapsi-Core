package com.sinapsi.model.impl;

import com.sinapsi.model.module.SinapsiModuleDescriptor;

/**
 * Implementation of SinapsiModuleDescriptor
 */
public class SinapsiModuleDescriptorImpl extends CommunicationInfo implements SinapsiModuleDescriptor {

    private int minVersion;
    private int defVersion;
    private String name;
    private String devId;
    private String platform;

    SinapsiModuleDescriptorImpl(int minVersion, int defVersion, String name, String devId, String platform) {
        this.minVersion = minVersion;
        this.defVersion = defVersion;
        this.name = name;
        this.devId = devId;
        this.platform = platform;
    }

    @Override
    public int getMinimumEngineVersion() {
        return minVersion;
    }

    @Override
    public int getDefaultEngineVersion() {
        return defVersion;
    }

    @Override
    public String getModuleName() {
        return name;
    }

    @Override
    public String getDeveloperID() {
        return devId;
    }

    @Override
    public String getClientPlatformType() {
        return platform;
    }
}
