package com.sinapsi.model.impl;

import com.sinapsi.engine.SinapsiPlatforms;
import com.sinapsi.model.MacroComponent;
import com.sinapsi.model.module.SinapsiModuleDescriptor;

import java.util.HashMap;

/**
 * Abstraction of Action, this class is not the real representation of Action object, but gives support to database manager
 * 
 */
public class ActionDescriptor extends CommunicationInfo implements MacroComponent {
    private int minVersion;
	private String name;
	private String formalParams;

	/**
	 * Default ctor
	 */
	public ActionDescriptor(int minVersion, String name, String formalParams) {
		super();
		this.minVersion = minVersion;
		this.name = name;
        this.formalParams = formalParams;
	}

	@Override
	public int getMinVersion() {
		return minVersion;
	}

    @Override
    public ComponentTypes getComponentType() {
        return ComponentTypes.ACTION;
    }

    @Override
    public HashMap<String, Integer> getSystemRequirementKeys() {
        return null;
    }

	@Override
	public String getDesignedPlatform() {
		return SinapsiPlatforms.PLATFORM_ALL;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getFormalParameters() {
		return formalParams;
	}

	@Override
	public SinapsiModuleDescriptor getBelongingSinapsiModule() {
		return null;
	}
}
