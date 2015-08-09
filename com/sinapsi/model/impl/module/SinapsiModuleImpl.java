package com.sinapsi.model.impl.module;

import com.sinapsi.engine.requirements.RequirementResolver;
import com.sinapsi.model.module.SinapsiModule;
import com.sinapsi.model.module.SinapsiModuleName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Implementation of SinapsiModule
 */
public class SinapsiModuleImpl extends SinapsiModuleDescriptorImpl implements SinapsiModule{


    private final RequirementResolver resolver;
    private final Class<?>[] members;

    public SinapsiModuleImpl(int minVersion,
                      int defVersion,
                      String name,
                      String devId,
                      String platform,
                      RequirementResolver resolver,
                      String[] neededRoles,
                      String[] filledRoles,
                      SinapsiModuleName[] dependencies,
                      Class<?>... members) {
        super(minVersion, defVersion, name, devId, platform, neededRoles, filledRoles, dependencies);
        this.resolver = resolver;
        this.members = members;
    }

    @Override
    public List<Class<?>> getMembers() {
        return new ArrayList<>(Arrays.asList(members));
    }

    @Override
    public RequirementResolver getRequirementResolver() {
        return resolver;
    }
}
