package ch.liip.aem.exceptions;

/**
 * @author Fabrice Hong
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
