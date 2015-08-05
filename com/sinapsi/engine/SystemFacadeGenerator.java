package com.sinapsi.engine;

import com.sinapsi.engine.system.ComponentSystemAdapter;
import com.sinapsi.engine.system.SystemFacade;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * TODO: doku
 */
public class SystemFacadeGenerator {

    private RequirementResolver resolver;
    private PlatformDependantObjectProvider provider;
    private List<Class<? extends ComponentSystemAdapter>> adapterClasses = new ArrayList<>();

    public SystemFacadeGenerator(Class<? extends ComponentSystemAdapter>[] adapterClasses,
                                 RequirementResolver requirementResolver,
                                 PlatformDependantObjectProvider objectProvider){
        resolver = requirementResolver;
        provider = objectProvider;
        Collections.addAll(this.adapterClasses, adapterClasses);
    }

    public SystemFacade generateSystemFacade(){
        SystemFacade sf = new SystemFacade();
        resolver.resolveRequirements(sf);

        //TODO: instantiate and initialize adapters

        return null;
    }

    private Object[] getAdapterInitializationObjects(PlatformDependantObjectProvider.ObjectKey[] keys){
        Object[] result = new Object[keys.length];
        for(int i = 0; i < keys.length; ++i){
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
