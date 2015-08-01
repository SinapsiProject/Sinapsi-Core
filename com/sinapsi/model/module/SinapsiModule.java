package com.sinapsi.model.module;

import java.util.List;

public interface SinapsiModule extends SinapsiModuleDescriptor {
    public List<Class<? extends SinapsiModuleMember>> getMembers();
}
