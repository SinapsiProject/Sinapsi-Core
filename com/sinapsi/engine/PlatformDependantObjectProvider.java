package com.sinapsi.engine;

/**
 * //TODO: doku
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
