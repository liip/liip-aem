package ch.liip.aem.taglib;

import ch.liip.aem.request.RequestObjects;
import ch.liip.aem.request.utils.Preconditions;
import com.day.cq.wcm.api.Page;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

@SuppressWarnings("serial")
public class PropertiesTag extends ComponentTagSupport {

    private String var;
    private String relPath;
    private String absPath;
    private Page page;
    private Resource resource;

    @Override
    public int doStartTag() throws JspException {
        Preconditions.checkTrue(this.resource == null || this.page == null,
                String.format("You cannot use page attribute and resource attribute in the same time. Page = '%s', Resource = '%s'",
                        this.page,
                        this.resource)
        );
        Resource resourceChild = getActualResource(this.resource, this.relPath, this.absPath);
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
    public void setRelPath(String relPath) {
        this.relPath = relPath;
    }
    public void setPage(Page page) {
        this.page = page;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public void setAbsPath(String absPath) {
        this.absPath = absPath;
    }
}
