package com.liip.aem.request;

/**
 * @author Fabrice Hong
 * @date 19.02.15
 */
public class RequestAware {
    protected RequestObjects requestObjects;

    public RequestAware(RequestObjects requestObjects) {
        this.requestObjects = requestObjects;
    }

}
