package com.sinapsi.engine.annotations;

/**
 * Created by Giuseppe on 05/08/15.
 */
@SuppressWarnings("serial")
public class MissingAnnotationException extends RuntimeException {
    public MissingAnnotationException(Class x){
        super("The class " + x.getName() + " does not override metadata getter methods and/or is not annotated correctly");
    }
}
