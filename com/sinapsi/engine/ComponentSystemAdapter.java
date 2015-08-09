package com.sinapsi.engine;


/**
 * All adapter should extend this interface.
 */
public interface ComponentSystemAdapter{

    public void init(Object... requiredPlatformDependantObjects);

}
