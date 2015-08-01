package com.sinapsi.model.impl;

import com.sinapsi.model.MacroComponent;
import com.sinapsi.model.module.SinapsiModuleDescriptor;

import java.util.HashMap;

/**
 * Abstraction of Trigger, this class is not the real representation of Trigger object, but gives support to database manager
 * 
 */
public class TriggerDescriptor extends CommunicationInfo implements MacroComponent {
	private int minVersion;
	private String name;
	private String formalParameters;
	private SinapsiModuleDescriptor module;
	
	/**
	 * Default ctor
	 */
	public TriggerDescriptor(int minVer, String n, String formalParameters, SinapsiModuleDescriptor module) {
		super();
		this.minVersion = minVer;
		this.name = n;
        this.formalParameters = formalParameters;
        this.module = module;
	}

	@Override
	public int getMinVersion() {
		return minVersion;
	}

	@Override
	public String getName() {
		return name;
	}


    @Override
    public ComponentTypes getComponentType() {
        return ComponentTypes.TRIGGER;
    }

    @Override
    public HashMap<String, Integer> getSystemRequirementKeys() {
        return null;
    }

    @Override
    public String getFormalParameters() {
        return formalParameters;
    }

	@Override
	public SinapsiModuleDescriptor getBelongingSinapsiModule() {
		return module;
	}
}
