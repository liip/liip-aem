package ch.liip.aem.taglib;

import ch.liip.aem.exceptions.TaglibExceptionException;
import ch.liip.aem.request.RequestObjects;
import ch.liip.aem.request.utils.Preconditions;
import ch.liip.aem.request.utils.ResourceUtils;
import com.day.cq.wcm.api.Page;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

@SuppressWarnings("serial")
public class PropertiesTag extends ComponentTagSupport {

    private String var;
    private String path;
    private Page page;
    private Resource resource;

    @Override
    public int doStartTag() throws JspException {
        Preconditions.checkTrue(this.resource == null || this.page == null,
                String.format("You cannot use page attribute and resource attribute in the same time. Page = '%s', Resource = '%s'",
                        this.page,
                        this.resource)
        );
        Resource actualResource;
        if (this.resource!=null) {
            actualResource = this.resource;
        } else { // then page must be used
            RequestObjects requestObjects = createRequestObjects();
            Page actualPage = determinePage(requestObjects);
            actualResource = actualPage.getContentResource();
        }
        Resource resourceChild = ResourceUtils.getResourceChild(actualResource, this.path);
        if (resourceChild==null) {
            throw new TaglibExceptionException(String.format("Cannot find path '%s' on resource '%s'", this.path, actualResource));
        }
        ValueMap properties = resourceChild.adaptTo(ValueMap.class);
        pageContext.setAttribute(var, properties, PageContext.PAGE_SCOPE);
        return Tag.SKIP_BODY;
    }

    private Page determinePage(RequestObjects requestObjects) {
        return this.page!=null?this.page: requestObjects.getCurrentPage();
    }

    public void setVar(String var) {
        this.var = var;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public void setPage(Page page) {
        this.page = page;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }
}
