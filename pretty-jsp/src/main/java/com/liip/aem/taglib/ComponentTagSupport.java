package com.liip.aem.taglib;

import com.liip.aem.request.RequestObjects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.jsp.tagext.TagSupport;

/**
 * @author Fabrice Hong
 * @date 10.02.15
 */
public class ComponentTagSupport extends TagSupport {
    private RequestObjects requestObjects;
    protected Logger logger;

    public ComponentTagSupport() {
        this.logger = LoggerFactory.getLogger(getClass());
    }

    public RequestObjects createRequestObjects() {
        return new RequestObjects(this.pageContext);
    }
}
