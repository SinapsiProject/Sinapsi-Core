package com.sinapsi.model.module;

import com.sinapsi.engine.RequirementResolver;

import java.util.List;

public interface SinapsiModule extends SinapsiModuleDescriptor {
    public List<Class<? extends SinapsiModuleMember>> getMembers();
    public RequirementResolver getRequirementResolver();
}
