package com.sinapsi.model;

/**
 * Every class that needs to get and extract actual parameters at
 * "execution time" (i.e. when a trigger activates) should implement this
 * interface.
 */
public interface ParameterizedActual {

    public String getActualParameters();

    public void setActualParameters(String params);

}
