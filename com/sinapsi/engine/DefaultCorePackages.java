package com.sinapsi.engine;

import com.sinapsi.model.plugin.SinapsiPluginPackage;
import com.sinapsi.model.plugin.SinapsiPluginPackageMember;

import java.util.List;

/**
 * Class containing the default core package object constants
 */
public class DefaultCorePackages {

    public static final String SINAPSI_TEAM_DEVELOPER_ID = "SINAPSI";

    public static final String ANTARES_CORE_PACKAGE_NAME = "ANTARES_CORE_PACKAGE_NAME";

    public SinapsiPluginPackage ANTARES_CORE_PACKAGE = new SinapsiPluginPackage() {
        @Override
        public List<Class<? extends SinapsiPluginPackageMember>> getMembers() {
            return null; //TODO: insert members (core components)
        }

        @Override
        public int getMinimumEngineVersion() {
            return SinapsiVersions.ANTARES.ordinal();
        }

        @Override
        public int getDefaultEngineVersion() {
            return SinapsiVersions.ANTARES.ordinal();
        }

        @Override
        public String packageName() {
            return ANTARES_CORE_PACKAGE_NAME;
        }

        @Override
        public String getDeveloperID() {
            return SINAPSI_TEAM_DEVELOPER_ID;
        }

        @Override
        public String getClientPlatformType() {
            return SinapsiPlatforms.PLATFORM_CORE;
        }
    };


    private DefaultCorePackages(){} //don't instantiate
}
