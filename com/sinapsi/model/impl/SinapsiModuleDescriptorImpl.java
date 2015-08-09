package com.sinapsi.model.impl;

import com.sinapsi.model.module.SinapsiModuleDescriptor;
import com.sinapsi.model.module.SinapsiModuleName;

/**
 * Implementation of SinapsiModuleDescriptor
 */
public class SinapsiModuleDescriptorImpl extends CommunicationInfo implements SinapsiModuleDescriptor {

    private int minVersion;
    private int defVersion;
    private String name;
    private String devId;
    private String platform;
    private String[] neededRoles;
    private String[] filledRoles;
    private SinapsiModuleName[] dependencies;

    SinapsiModuleDescriptorImpl(int minVersion, int defVersion, String name, String devId, String platform, String[] neededRoles, String[] filledRoles, SinapsiModuleName... dependencies) {
        this.minVersion = minVersion;
        this.defVersion = defVersion;
        this.name = name;
        this.devId = devId;
        this.platform = platform;
        this.neededRoles = neededRoles;
        this.filledRoles = filledRoles;
        this.dependencies = dependencies;
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

    @Override
    public SinapsiModuleName[] getModuleDependencies() {
        return dependencies;
    }

    @Override
    public String[] getNeededRoles() {
        return neededRoles;
    }


    @Override
    public String[] getFilledRoles() {
        return filledRoles;
    }

}