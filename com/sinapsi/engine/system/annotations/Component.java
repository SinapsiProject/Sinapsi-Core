package com.sinapsi.engine.system.annotations;

import com.sinapsi.engine.SinapsiVersions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Giuseppe on 05/08/15.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Component {
    String value(); //the component name
    SinapsiVersions engineMinVersion() default SinapsiVersions.ANTARES;
}
