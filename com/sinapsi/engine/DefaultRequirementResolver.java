package com.sinapsi.engine;

import com.sinapsi.engine.system.SystemFacade;

/**
 * //TODO: doku
 */
public abstract class DefaultRequirementResolver implements RequirementResolver{

    @Override
    public PlatformDependantObjectProvider.ObjectKey[] getPlatformDependantObjectsKeys() {
        return null;
    }

    @Override
    public void setPlatformDependantObjects(Object... objects) {
        //does nothing
    }

    @Override
    public abstract void resolveRequirements(SystemFacade sf);
}
