package com.sinapsi.engine.system;

import com.sinapsi.model.module.SinapsiModuleMember;

/**
 * All adapter should extend this interface.
 */
public interface ComponentSystemAdapter extends SinapsiModuleMember {

    public void init(Object... requiredPlatformDependantObjects);

}
