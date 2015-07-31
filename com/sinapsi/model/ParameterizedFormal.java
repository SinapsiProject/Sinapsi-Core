package com.sinapsi.model;

/**
 * Every class that needs to indicate formal parameters
 * for its instantiation (i.e. when a new trigger for a macro is defined)
 * should implement this interface.
 */
public interface ParameterizedFormal {
    public String getFormalParameters();
}
