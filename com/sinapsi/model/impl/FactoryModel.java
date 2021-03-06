/**
 * 
 */
package com.sinapsi.model.impl;

import com.sinapsi.engine.requirements.RequirementResolver;
import com.sinapsi.model.DeviceInterface;
import com.sinapsi.model.FactoryModelInterface;
import com.sinapsi.model.MacroInterface;
import com.sinapsi.model.UserInterface;
import com.sinapsi.model.impl.module.SinapsiModuleDescriptorImpl;
import com.sinapsi.model.impl.module.SinapsiModuleImpl;
import com.sinapsi.model.module.SinapsiModule;
import com.sinapsi.model.module.SinapsiModuleDescriptor;
import com.sinapsi.model.module.SinapsiModuleName;

/**
 * Implementation of the default factory model interface
 *
 */
public class FactoryModel implements FactoryModelInterface {

	@Override
	public UserInterface newUser(int id, String email, String password, boolean active, String role) {
		return new User(id, email, password, active, role);
	}


	@Override
	public DeviceInterface newDevice(int id, String name, String model, String type, UserInterface user, int clientVersion) {
		return new Device(id, name, model, type, clientVersion, user);
	}


	@Override
	public MacroInterface newMacro(String name, int id) {
		return new Macro(id, name);
	}


	@Override
	public ActionDescriptor newActionDescriptor(int minVersion, String name, String formalParameters) {
		return new ActionDescriptor(minVersion, name, formalParameters);
	}


	@Override
	public TriggerDescriptor newTriggerDescriptor(int minVersion, String name, String formalParameters) {
		return new TriggerDescriptor(minVersion, name, formalParameters);
	}


    @Override
    public SinapsiModuleDescriptor newModuleDescriptor(int minVersion, int defVersion, String name, String devId, String platform, String[] neededRoles, String[] filledRoles, SinapsiModuleName... dependencies) {
        return new SinapsiModuleDescriptorImpl(minVersion, defVersion, name, devId, platform, neededRoles, filledRoles, dependencies);
    }

    @SafeVarargs
    @Override
    public final SinapsiModule newModule(int minVersion, int defVersion, String name, String devId, String platform, RequirementResolver resolver, String[] neededRoles, String[] filledRoles, SinapsiModuleName[] dependencies, Class<?>... members) {
        return new SinapsiModuleImpl(minVersion, defVersion, name, devId, platform, resolver, neededRoles, filledRoles, dependencies, members);
    }


}
