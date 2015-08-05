package com.sinapsi.engine;

import com.sinapsi.model.module.SinapsiModuleMember;

/**
 * All adapter should extend this interface.
 */
public interface ComponentSystemAdapter extends SinapsiModuleMember {

    public void init(Object... requiredPlatformDependantObjects);

}
