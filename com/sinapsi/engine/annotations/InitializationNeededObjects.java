package com.sinapsi.engine.annotations;

import com.sinapsi.engine.system.PlatformDependantObjectProvider;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Giuseppe on 05/08/15.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface InitializationNeededObjects {
    PlatformDependantObjectProvider.ObjectKey[] value();
}
