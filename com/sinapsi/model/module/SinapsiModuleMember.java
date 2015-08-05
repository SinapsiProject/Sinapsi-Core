package com.sinapsi.model.module;

/**
 * Interface representing a member (for example, a MacroComponent)
 * of a plugin package.
 */
public interface SinapsiModuleMember { //TODO: this should be an annotation
    public SinapsiModuleDescriptor getBelongingSinapsiModule(); //TODO: this should be only the name of the module
}
