package com.sinapsi.engine;

import com.sinapsi.engine.system.ComponentSystemAdapter;
import com.sinapsi.model.MacroComponent;
import com.sinapsi.model.module.SinapsiModule;
import com.sinapsi.model.module.SinapsiModuleMember;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The purpose of this class is to manage all the modules of a specific MacroEngineInstance
 */
public class EngineModuleManager {

    private List<SinapsiModule> modules = new ArrayList<>();
    private MacroEngine engine;
    private List<Class<? extends MacroComponent>> components = new ArrayList<>();
    private List<Class<? extends ComponentSystemAdapter>> adapters = new ArrayList<>();

    public EngineModuleManager(MacroEngine engine,
                               SinapsiModule... modules) {
        this.engine = engine;
        Collections.addAll(this.modules, modules);

        extractClasses();
    }

    @SuppressWarnings("unchecked")
    private void extractClasses() {
        for(SinapsiModule m: modules){
            for(Class<? extends SinapsiModuleMember> mm: m.getMembers()){
                if(doesClassImplementMacroComponent(mm))
                    components.add((Class<? extends MacroComponent>) mm);
                if(doesClassImplementComponentSystemAdapter(mm))
                    adapters.add((Class<? extends ComponentSystemAdapter>) mm);
            }
        }
    }


    public Class<? extends MacroComponent>[] getAllComponentClasses() {
        //noinspection unchecked
        return components.toArray((Class<? extends MacroComponent>[])new Class<?>[components.size()]);
    }

    public Class<? extends ComponentSystemAdapter>[] getAllComponentSystemAdapters() {
        //noinspection unchecked
        return adapters.toArray((Class<? extends ComponentSystemAdapter>[])new Class<?>[adapters.size()]);
    }

    public MacroEngine getEngine() {
        return engine;
    }

    /**
     * Checks if x is a class which implements - or an interface which extends -
     * directly or indirectly MacroComponent
     * @param x the class object
     */
    private boolean doesClassImplementMacroComponent(Class x){
        return MacroComponent.class.isAssignableFrom(x);
    }

    /**
     * Checks if x is a class which implements - or an interface which extends -
     * directly or indirectly ComponentSystemAdapter
     * @param x the class object
     */
    private boolean doesClassImplementComponentSystemAdapter(Class x){
        return ComponentSystemAdapter.class.isAssignableFrom(x);
    }
}
