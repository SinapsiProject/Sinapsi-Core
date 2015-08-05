package com.sinapsi.engine;

/**
 * Created by Giuseppe on 05/08/15.
 */
public class MissingAnnotationException extends RuntimeException {
    public MissingAnnotationException(Class x){
        super("The class " + x.getName() + " does not override metadata getter methods and/or is not annotated correctly");
    }
}
