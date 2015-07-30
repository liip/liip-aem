package com.liip.aem.request.utils;

import com.liip.aem.exceptions.TaglibExceptionException;

/**
 * @author Fabrice Hong
 * @date 17.02.15
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
