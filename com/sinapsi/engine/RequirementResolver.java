package com.sinapsi.engine;

import com.sinapsi.engine.system.SystemFacade;

/**
 * TODO: doku
 */
public interface RequirementResolver{
    public PlatformDependantObjectProvider.ObjectKey[] getPlatformDependantObjectsKeys();
    public void setPlatformDependantObjects(Object... objects);
    public void resolveRequirements(SystemFacade sf);
}
