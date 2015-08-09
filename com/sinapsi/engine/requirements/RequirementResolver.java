package com.sinapsi.engine.requirements;

import com.sinapsi.engine.system.PlatformDependantObjectProvider;
import com.sinapsi.engine.system.SystemFacade;

/**
 * Interface to be implemented each time a new module is defined, in order to specify
 * how requirements are resolved for that specific module.
 */
public interface RequirementResolver{
    public PlatformDependantObjectProvider.ObjectKey[] getPlatformDependantObjectsKeys();
    public void setPlatformDependantObjects(Object... objects);
    public void resolveRequirements(SystemFacade sf);
}
