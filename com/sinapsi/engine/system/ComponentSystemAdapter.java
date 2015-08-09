package com.sinapsi.engine.system;


/**
 * All adapter should extend this interface.
 */
public interface ComponentSystemAdapter{

    public void init(Object... requiredPlatformDependantObjects);

}
