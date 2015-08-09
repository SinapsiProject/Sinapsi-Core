package com.sinapsi.engine.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Giuseppe on 05/08/15.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Requirement {

    public int value();

    public String name();
}
