package com.sinapsi.engine;

import com.sinapsi.engine.components.common.ActionContinueConfirmDialog;
import com.sinapsi.engine.components.common.ActionSendSMS;
import com.sinapsi.engine.components.common.ActionSimpleNotification;
import com.sinapsi.engine.components.common.ActionStringInputDialog;
import com.sinapsi.engine.components.common.ActionWifiState;
import com.sinapsi.engine.components.common.TriggerACPower;
import com.sinapsi.engine.components.common.TriggerSMS;
import com.sinapsi.engine.components.common.TriggerWifi;
import com.sinapsi.engine.components.core.ActionLog;
import com.sinapsi.engine.components.core.ActionSetVariable;
import com.sinapsi.engine.components.core.TriggerEngineStart;
import com.sinapsi.model.impl.FactoryModel;
import com.sinapsi.model.module.SinapsiModule;

/**
 * Class containing the default core package object constants
 */
public class DefaultCoreModules {

    public static final String SINAPSI_TEAM_DEVELOPER_ID = "SINAPSI";


    //CORE MODULE - ANTARES VERSION #################################

    public static final String ANTARES_CORE_MODULE_NAME = "ANTARES_CORE_MODULE";

    public static final String ROLE_ANTARES_CORE = "ROLE_ANTARES_CORE";

    public static final String REQUIREMENT_RESTARTABLE_MACRO_ENGINE = "REQUIREMENT_RESTARTABLE_MACRO_ENGINE";

    public static final SinapsiModule ANTARES_CORE_MODULE = new FactoryModel().newModule(
            SinapsiVersions.ANTARES.ordinal(),
            SinapsiVersions.ANTARES.ordinal(),
            ANTARES_CORE_MODULE_NAME,
            SINAPSI_TEAM_DEVELOPER_ID,
            SinapsiPlatforms.PLATFORM_ALL,
            null,
            null,
            new String[]{ROLE_ANTARES_CORE},
            null,

            TriggerEngineStart.class,

            ActionLog.class,
            ActionSetVariable.class
    );


    //COMMONS MODULE - ANTARES VERSION ##############################

    public static final String ANTARES_COMMON_COMPONENTS_MODULE_NAME = "ANTARES_COMMON_COMPONENTS_MODULE";

    public static final String ROLE_ANTARES_COMMON_COMPONENTS = "ROLE_ANTARES_COMMON_COMPONENTS";
    public static final String ROLE_ANTARES_COMMON_ADAPTERS = "ROLE_ANTARES_COMMON_ADAPTERS";

    public static final String REQUIREMENT_SIMPLE_DIALOGS = "REQUIREMENT_SIMPLE_DIALOGS";
    public static final String REQUIREMENT_INPUT_DIALOGS = "REQUIREMENT_INPUT_DIALOGS";
    public static final String REQUIREMENT_SMS_READ = "REQUIREMENT_SMS_READ";
    public static final String REQUIREMENT_SMS_SEND = "REQUIREMENT_SMS_SEND";
    public static final String REQUIREMENT_SIMPLE_NOTIFICATIONS = "REQUIREMENT_SIMPLE_NOTIFICATIONS";
    public static final String REQUIREMENT_WIFI = "REQUIREMENT_WIFI";
    public static final String REQUIREMENT_INTERCEPT_SCREEN_POWER = "REQUIREMENT_INTERCEPT_SCREEN_POWER";
    public static final String REQUIREMENT_AC_CHARGER = "REQUIREMENT_AC_CHARGER";

    public static final SinapsiModule ANTARES_COMMON_COMPONENTS_MODULE = new FactoryModel().newModule(
            SinapsiVersions.ANTARES.ordinal(),
            SinapsiVersions.ANTARES.ordinal(),
            ANTARES_COMMON_COMPONENTS_MODULE_NAME,
            SINAPSI_TEAM_DEVELOPER_ID,
            SinapsiPlatforms.PLATFORM_ALL,
            null,
            new String[]{ROLE_ANTARES_COMMON_ADAPTERS},
            new String[]{ROLE_ANTARES_COMMON_COMPONENTS},
            null,

            ActionContinueConfirmDialog.class,
            ActionSendSMS.class,
            ActionSimpleNotification.class,
            ActionStringInputDialog.class,
            ActionWifiState.class,

            TriggerACPower.class,
            TriggerSMS.class,
            TriggerWifi.class
    );



    private DefaultCoreModules(){} //don't instantiate
}
