package com.sinapsi.model.plugin;

/**
 * TODO: doku
 */
public interface SinapsiPluginPackageDescriptor {
    public int getMinimumEngineVersion();
    public int getDefaultEngineVersion();
    public String packageName();
    public String getDeveloperID();
    public String getClientPlatformType();
}
