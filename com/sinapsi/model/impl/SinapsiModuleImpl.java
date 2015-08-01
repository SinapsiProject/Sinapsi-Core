package com.sinapsi.model.impl;

import com.sinapsi.model.module.SinapsiModule;
import com.sinapsi.model.module.SinapsiModuleMember;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Implementation of SinapsiModule
 */
public class SinapsiModuleImpl extends SinapsiModuleDescriptorImpl implements SinapsiModule{


    private final Class<? extends SinapsiModuleMember>[] members;

    @SafeVarargs
    SinapsiModuleImpl(int minVersion, int defVersion, String name, String devId, String platform, Class<? extends SinapsiModuleMember>... members) {
        super(minVersion, defVersion, name, devId, platform);
        this.members = members;
    }

    @Override
    public List<Class<? extends SinapsiModuleMember>> getMembers() {
        return new ArrayList<>(Arrays.asList(members));
    }
}
