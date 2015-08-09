package com.sinapsi.model.module;

import com.sinapsi.engine.RequirementResolver;

import java.util.List;

public interface SinapsiModule extends SinapsiModuleDescriptor {
    //TODO: find a way to declare requirements between modules (i.e. module A requires module B to work)
    public List<Class<?>> getMembers();
    public RequirementResolver getRequirementResolver();
}
