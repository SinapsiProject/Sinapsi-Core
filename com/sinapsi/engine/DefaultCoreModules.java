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

    public static final String ANTARES_CORE_MODULE_NAME = "ANTARES_CORE_MODULE";
    public static final String ANTARES_COMMONS_MODULE_NAME = "ANTARES_COMMONS_MODULE";

    public static final SinapsiModule ANTARES_CORE_MODULE = new FactoryModel().newModule(
            SinapsiVersions.ANTARES.ordinal(),
            SinapsiVersions.ANTARES.ordinal(),
            ANTARES_CORE_MODULE_NAME,
            SINAPSI_TEAM_DEVELOPER_ID,
            SinapsiPlatforms.PLATFORM_ALL,

            TriggerEngineStart.class,

            ActionLog.class,
            ActionSetVariable.class
    );

    public static final SinapsiModule ANTARES_COMMONS_MODULE = new FactoryModel().newModule(
            SinapsiVersions.ANTARES.ordinal(),
            SinapsiVersions.ANTARES.ordinal(),
            ANTARES_COMMONS_MODULE_NAME,
            SINAPSI_TEAM_DEVELOPER_ID,
            SinapsiPlatforms.PLATFORM_UNDEFINED,

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
