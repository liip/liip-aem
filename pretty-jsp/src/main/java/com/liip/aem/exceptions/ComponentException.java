package com.liip.aem.exceptions;

/**
 * @author Fabrice Hong
 * @date 10.02.15
 */
public class ComponentException extends Exception {
    public ComponentException(String message) {
        super(message);
    }

    public ComponentException(Throwable cause) {
        super(cause);
    }
}
