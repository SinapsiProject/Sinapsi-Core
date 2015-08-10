package com.sinapsi.engine.system;

import com.sinapsi.engine.SinapsiPlatforms;
import com.sinapsi.engine.annotations.AdapterImplementation;
import com.sinapsi.engine.annotations.InitializationNeededObjects;
import com.sinapsi.engine.requirements.DefaultRequirementResolver;
import com.sinapsi.engine.requirements.RequirementResolver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Helper class instantiated by MacroEngine to automatically generate the system facade.
 */
public class SystemFacadeGenerator {

    private RequirementResolver defaultResolver;
    private PlatformDependantObjectProvider provider;
    private final String currentPlatform;
    private List<RequirementResolver> modulesResolvers = new ArrayList<>();
    private List<Class<? extends ComponentSystemAdapter>> adapterClasses = new ArrayList<>();

    public SystemFacadeGenerator(Class<? extends ComponentSystemAdapter>[] adapterClasses,
                                 PlatformDependantObjectProvider objectProvider,
                                 String currentPlatform,
                                 DefaultRequirementResolver requirementResolver,
                                 RequirementResolver... modulesResolvers) {
        defaultResolver = requirementResolver;
        provider = objectProvider;
        this.currentPlatform = currentPlatform;

        Collections.addAll(this.modulesResolvers, modulesResolvers);

        Collections.addAll(this.adapterClasses, adapterClasses);
    }

    public SystemFacade generateSystemFacade() {
        SystemFacade sf = new SystemFacade();

        if (defaultResolver != null) defaultResolver.resolveRequirements(sf);

        for (RequirementResolver rr : modulesResolvers) {
            rr.setPlatformDependantObjects(getPlatformDependantObjects(rr.getPlatformDependantObjectsKeys()));
            rr.resolveRequirements(sf);
        }

        for (Class<? extends ComponentSystemAdapter> c : adapterClasses) {
            AdapterImplementation annot = c.getAnnotation(AdapterImplementation.class);
            if (annot != null) {
                InitializationNeededObjects initAnnot = c.getAnnotation(InitializationNeededObjects.class);

                //noinspection TryWithIdenticalCatches
                try {
                    ComponentSystemAdapter csa = c.newInstance();
                    if (initAnnot != null && initAnnot.value()!=null) csa.init(getPlatformDependantObjects(initAnnot.value()));
                    if (annot.platform().equals(currentPlatform) || annot.platform().equals(SinapsiPlatforms.PLATFORM_ALL))
                        sf.addSystemService(annot.value(), csa);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
        }
        return sf;
    }

    private Object[] getPlatformDependantObjects(PlatformDependantObjectProvider.ObjectKey[] keys) {
        if (keys == null) return null;
        Object[] result = new Object[keys.length];
        for (int i = 0; i < keys.length; ++i) {
            PlatformDependantObjectProvider.ObjectKey key = keys[i];
            try {
                result[i] = provider.getObject(key);
            } catch (PlatformDependantObjectProvider.ObjectNotAvailableException e) {
                e.printStackTrace();
                return null;
            }
        }
        return result;
    }
}
