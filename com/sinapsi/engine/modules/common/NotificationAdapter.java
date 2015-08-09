package com.sinapsi.engine.modules.common;

import com.sinapsi.engine.system.ComponentSystemAdapter;
import com.sinapsi.engine.annotations.AdapterInterface;

/**
 * Interface used to adapt various system-dependent calls
 * to show various types of dialogs
 */
@AdapterInterface(NotificationAdapter.ADAPTER_NOTIFICATION)
public interface NotificationAdapter extends ComponentSystemAdapter {
    public static final String ADAPTER_NOTIFICATION = "ADAPTER_NOTIFICATION";

    /**
     * shows up a simple notification on the system
     * @param title the title
     * @param message the message
     */
    public void showSimpleNotification(String title, String message);
}
