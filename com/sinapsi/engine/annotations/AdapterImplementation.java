package com.sinapsi.engine.annotations;

import com.sinapsi.engine.SinapsiPlatforms;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to annotate classes which implement ComponentSystemAdapter
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AdapterImplementation {
    String value(); //Adapter name
    String platform() default SinapsiPlatforms.PLATFORM_ALL;
}
