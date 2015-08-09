package com.sinapsi.model.module;

/**
 * This interface contains methods to get metadata of a module.
 */
public interface SinapsiModuleDescriptor {
    public int getMinimumEngineVersion();
    public int getDefaultEngineVersion();
    public String getModuleName();
    public String getDeveloperID();
    public String getClientPlatformType(); //TODO: check platform at engine initialization
}
