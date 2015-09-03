package ch.liip.aem.taglib;

import ch.liip.aem.exceptions.TaglibExceptionException;
import ch.liip.aem.request.RequestObjects;
import ch.liip.aem.request.utils.ResourceUtils;
import org.apache.sling.api.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.jsp.tagext.TagSupport;

/**
 * @author Fabrice Hong
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

    protected Resource getActualResource(Resource resource, String relPath, String absPath) {
        if (absPath!=null) {
            return createRequestObjects().getResourceResolver().getResource(absPath);
        }
        Resource actualResource;
        if (resource !=null) {
            actualResource = resource;
        } else { // then page must be used
            RequestObjects requestObjects = createRequestObjects();
            actualResource = requestObjects.getResource();
        }
        Resource resourceChild = ResourceUtils.getResourceChild(actualResource, relPath);
        if (resourceChild==null) {
            throw new TaglibExceptionException(String.format("Cannot find relPath '%s' on resource '%s'", relPath, actualResource));
        }
        return resourceChild;
    }
}
