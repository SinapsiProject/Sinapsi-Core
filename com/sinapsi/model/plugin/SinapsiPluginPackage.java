package com.sinapsi.model.plugin;

import java.util.List;

/**
 * //TODO:doku
 */
public interface SinapsiPluginPackage extends SinapsiPluginPackageDescriptor {
    public List<Class<? extends SinapsiPluginPackageMember>> getMembers();
}
