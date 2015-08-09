package com.sinapsi.engine;

import com.sinapsi.model.MacroComponent;
import com.sinapsi.model.module.SinapsiModule;
import com.sinapsi.model.module.SinapsiModuleMember;
import com.sinapsi.model.module.SinapsiModuleName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The purpose of this class is to manage all the modules of a specific MacroEngineInstance
 */
public class EngineModuleManager {

    private Map<String, SinapsiModule> modules = new HashMap<>();
    private Set<String> filledRoles = new HashSet<>();
    private MacroEngine engine;
    private List<Class<? extends MacroComponent>> components = new ArrayList<>();
    private List<Class<? extends ComponentSystemAdapter>> adapters = new ArrayList<>();
    private List<RequirementResolver> resolvers = new ArrayList<>();
    private boolean errorStatus;

    public EngineModuleManager(MacroEngine engine,
                               SinapsiModule... modules) {
        errorStatus = false;
        this.engine = engine;
        for (SinapsiModule m : modules) {
            this.modules.put(getModuleCompleteName(m), m);
            if(m.getFilledRoles()!=null)
                Collections.addAll(this.filledRoles, m.getFilledRoles());
        }

        extractClasses();

        check();
    }

    private void check() {
        for (SinapsiModule m : modules.values()) {
            if (!m.getClientPlatformType().equals(SinapsiPlatforms.PLATFORM_ALL) &&
                    !m.getClientPlatformType().equals(engine.getPlatform())) {
                errorStatus = true;
                return;
            }
            if (m.getMinimumEngineVersion() > engine.getCurrentVersion().ordinal()) {
                errorStatus = true;
                return;
            }
            if (m.getModuleDependencies() != null) {
                for (SinapsiModuleName dependency : m.getModuleDependencies()) {
                    if (!modules.containsKey(getModuleCompleteName(dependency))) {
                        errorStatus = true;
                        return;
                    }
                }
            }
            if (m.getNeededRoles() != null) {
                for (String nr : m.getNeededRoles()){
                    if(!filledRoles.contains(nr)){
                        errorStatus = true;
                        return;
                    }
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void extractClasses() {
        for (SinapsiModule m : modules.values()) {
            if (m.getRequirementResolver() != null)
                resolvers.add(m.getRequirementResolver());
            for (Class<? extends SinapsiModuleMember> mm : m.getMembers()) {
                if (doesClassImplementMacroComponent(mm))
                    components.add((Class<? extends MacroComponent>) mm);
                if (doesClassImplementComponentSystemAdapter(mm))
                    adapters.add((Class<? extends ComponentSystemAdapter>) mm);
            }
        }
    }


    public Class<? extends MacroComponent>[] getAllComponentClasses() {
        //noinspection unchecked
        return components.toArray((Class<? extends MacroComponent>[]) new Class<?>[components.size()]);
    }

    public Class<? extends ComponentSystemAdapter>[] getAllComponentSystemAdapterClasses() {
        //noinspection unchecked
        return adapters.toArray((Class<? extends ComponentSystemAdapter>[]) new Class<?>[adapters.size()]);
    }

    public RequirementResolver[] getAllRequirementResolvers() {
        return resolvers.toArray(new RequirementResolver[resolvers.size()]);
    }

    public MacroEngine getEngine() {
        return engine;
    }

    /**
     * Checks if x is a class which implements - or an interface which extends -
     * directly or indirectly MacroComponent
     *
     * @param x the class object
     */
    private boolean doesClassImplementMacroComponent(Class x) {
        return MacroComponent.class.isAssignableFrom(x);
    }

    /**
     * Checks if x is a class which implements - or an interface which extends -
     * directly or indirectly ComponentSystemAdapter
     *
     * @param x the class object
     */
    private boolean doesClassImplementComponentSystemAdapter(Class x) {
        return ComponentSystemAdapter.class.isAssignableFrom(x);
    }

    public boolean isErrorStatus() {
        return errorStatus;
    }

    public static String getModuleCompleteName(SinapsiModuleName smn) {
        return smn.getDeveloperID() + "#" + smn.getModuleName();
    }
}
