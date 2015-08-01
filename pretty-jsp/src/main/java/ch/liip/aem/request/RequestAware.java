package ch.liip.aem.request;

/**
 * @author Fabrice Hong
 */
public class RequestAware {
    protected RequestObjects requestObjects;

    public RequestAware(RequestObjects requestObjects) {
        this.requestObjects = requestObjects;
    }

}
