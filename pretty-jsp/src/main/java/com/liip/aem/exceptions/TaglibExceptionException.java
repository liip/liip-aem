package com.liip.aem.exceptions;

/**
 * @author Fabrice Hong
 * @date 17.02.15
 */
public class TaglibExceptionException extends RuntimeException {

    public TaglibExceptionException(String message) {
        super(message);
    }

    public TaglibExceptionException(String message, Throwable cause) {
        super(message, cause);
    }

    public TaglibExceptionException(Throwable cause) {
        super(cause);
    }
}
