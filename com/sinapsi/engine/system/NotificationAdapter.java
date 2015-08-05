package com.sinapsi.engine.system;

import com.sinapsi.engine.ComponentSystemAdapter;
import com.sinapsi.engine.system.annotations.AdapterInterface;

/**
 * Interface used to adapt various system-dependent calls
 * to show various types of dialogs
 */
@AdapterInterface(NotificationAdapter.ADAPTER_NOTIFICATION)
public interface NotificationAdapter extends ComponentSystemAdapter {
    public static final String ADAPTER_NOTIFICATION = "ADAPTER_NOTIFICATION";
    public static final String REQUIREMENT_SIMPLE_NOTIFICATIONS = "REQUIREMENT_SIMPLE_NOTIFICATIONS";

    /**
     * shows up a simple notification on the system
     * @param title the title
     * @param message the message
     */
    public void showSimpleNotification(String title, String message);
}
