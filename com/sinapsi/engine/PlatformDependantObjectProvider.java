package com.sinapsi.engine;

/**
 * Interface to be implemented in order to provide to requirement resolvers and adapters
 * platform dependant objects. Every platform should try to provide the most possible objects
 * which keys are in enum definition @{code ObjectKey}.
 */
public interface PlatformDependantObjectProvider {
    public enum ObjectKey {
        ANDROID_SERVICE_CONTEXT,
        ANDROID_APPLICATION_CONTEXT,
        LINUX_ROOT_PERMISSIONS, //valid also for eventual android modules for rooted devices
    }

    public Object getObject(ObjectKey key) throws ObjectNotAvailableException;

    class ObjectNotAvailableException extends Exception {
        public ObjectNotAvailableException(String detailMessage) {
            super(detailMessage);
        }
    }
}
