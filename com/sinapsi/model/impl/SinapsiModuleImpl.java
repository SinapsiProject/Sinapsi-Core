package com.sinapsi.model.impl;

import com.sinapsi.engine.RequirementResolver;
import com.sinapsi.model.module.SinapsiModule;
import com.sinapsi.model.module.SinapsiModuleMember;
import com.sinapsi.model.module.SinapsiModuleName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Implementation of SinapsiModule
 */
public class SinapsiModuleImpl extends SinapsiModuleDescriptorImpl implements SinapsiModule{


    private final RequirementResolver resolver;
    private final Class<? extends SinapsiModuleMember>[] members;

    @SafeVarargs
    SinapsiModuleImpl(int minVersion,
                      int defVersion,
                      String name,
                      String devId,
                      String platform,
                      RequirementResolver resolver,
                      String[] neededRoles,
                      String[] filledRoles,
                      SinapsiModuleName[] dependencies,
                      Class<? extends SinapsiModuleMember>... members) {
        super(minVersion, defVersion, name, devId, platform, neededRoles, filledRoles, dependencies);
        this.resolver = resolver;
        this.members = members;
    }

    @Override
    public List<Class<? extends SinapsiModuleMember>> getMembers() {
        return new ArrayList<>(Arrays.asList(members));
    }

    @Override
    public RequirementResolver getRequirementResolver() {
        return resolver;
    }
}
