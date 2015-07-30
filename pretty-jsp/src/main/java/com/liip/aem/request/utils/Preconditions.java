package com.liip.aem.request.utils;

import com.liip.aem.exceptions.TaglibExceptionException;

/**
 * @author Fabrice Hong
 */
public class Preconditions {

    public static void checkNotNull(Object object, String message) {
        if (object==null) {
            throw new TaglibExceptionException(message);
        }
    }

    public static void checkTrue(boolean condition, String message) {
        if (!condition) {
            throw new TaglibExceptionException(message);
        }
    }

    public static void checkFalse(boolean condition, String message) {
        if (condition) {
            throw new TaglibExceptionException(message);
        }
    }
}
