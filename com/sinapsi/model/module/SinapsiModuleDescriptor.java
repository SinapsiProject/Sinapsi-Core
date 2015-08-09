package com.sinapsi.model.module;

/**
 * This interface contains methods to get metadata of a module.
 */
public interface SinapsiModuleDescriptor extends SinapsiModuleName{
    public int getMinimumEngineVersion();
    public int getDefaultEngineVersion();

    public String getClientPlatformType();
    public SinapsiModuleName[] getModuleDependencies();
    public String[] getFilledRoles();
    public String[] getNeededRoles();
}
