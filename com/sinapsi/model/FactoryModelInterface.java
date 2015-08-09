package com.sinapsi.model;

import com.sinapsi.engine.RequirementResolver;
import com.sinapsi.model.impl.ActionDescriptor;
import com.sinapsi.model.impl.TriggerDescriptor;
import com.sinapsi.model.module.SinapsiModule;
import com.sinapsi.model.module.SinapsiModuleDescriptor;
import com.sinapsi.model.module.SinapsiModuleMember;
import com.sinapsi.model.module.SinapsiModuleName;

import java.util.List;

/**
 * The default Model Factory Interface
 *
 */
public interface FactoryModelInterface {

    /**
     * Creates a new instance of UserInterface
     *
     * @param id the user id in the db
     * @param email the user's email
     * @param password the user's password
     * @return a new UserInterface instance
     */
    public UserInterface newUser(int id, String email, String password, boolean active, String role);

    
    /**
     * Create a new instance of DeviceInterface
     *
     * @param id the device id in the db
     * @param name the device name i.e. "Office Phone"
     * @param model the model of the device i.e. "Nexus 5"
     * @param type the type of the device i.e. "AndroidSmartphone"
     * @param user the owner of the device
     * @param clientVersion the version of the sinapsi client running on a device
     * @return a new DeviceInterface instance
     */
    public DeviceInterface newDevice(int id, String name, String model, String type, UserInterface user, int clientVersion);

    /**
     * Creates a new instance of MacroInterface
     *
     * @param name the name chosen for the macro
     * @param id the id of the macro in the db
     * @return a new MacroInterface instance
     */
    public MacroInterface newMacro(String name, int id);
    
    /**
     * Creates a new action abstract representation
     * @param minVersion min version of the abstract action
     * @param name the name of the abstract action
     * @param module
     * @return
     */
    public ActionDescriptor newActionDescriptor(int minVersion, String name, String formalParameters);
    
    /**
     * Creates a new trigger abstract representation
     * @param minVersion min version of the abstract trigger
     * @param name the name of the abstract trigger
     * @param module
     * @return
     */
    public TriggerDescriptor newTriggerDescriptor(int minVersion, String name, String formalParameters);

    /**
     * Creates a new module descriptor
     * @param minVersion the minimum engine version this module requires
     * @param defVersion the engine version this module was designed for
     * @param name the name of the module
     * @param devId the developer id
     * @param platform the platform
     * @param neededRoles
     * @param filledRoles
     * @return a new SinapsiModuleDescriptor
     */
    public SinapsiModuleDescriptor newModuleDescriptor(int minVersion, int defVersion, String name, String devId, String platform, String[] neededRoles, String[] filledRoles, SinapsiModuleName... dependencies);

    @SuppressWarnings("unchecked")
    public SinapsiModule newModule(int minVersion, int defVersion, String name, String devId, String platform, RequirementResolver resolver, String[] neededRoles, String[] filledRoles, SinapsiModuleName[] dependencies, Class<? extends SinapsiModuleMember>... members);
}
