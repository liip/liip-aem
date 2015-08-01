package ch.liip.aem.exceptions;

/**
 * @author Fabrice Hong
 */
public class ComponentException extends Exception {
    public ComponentException(String message) {
        super(message);
    }

    public ComponentException(Throwable cause) {
        super(cause);
    }
}
