package ch.liip.aem.taglib;


import org.apache.sling.api.resource.Resource;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Matthieu Cornut
 */
public class ListChildrenTag extends ComponentTagSupport {

    private String relPath;
    private String absPath;
    private String var;
    private Integer maxItems;
    private Resource resource;

    public ListChildrenTag() {
        super();
    }

    @Override
    public int doEndTag() throws JspException {
        Resource rootResource = getActualResource(this.resource, this.relPath, this.absPath);

        // Result list
        List<Resource> items = new ArrayList<Resource>();

        Iterator<Resource> resourceIterator = rootResource.listChildren();
        int count=0;
        while (resourceIterator.hasNext() && (null==maxItems || count < maxItems)) {
            count++;
            Resource child = resourceIterator.next();
            items.add(child);
        }

        this.pageContext.setAttribute(var, items, PageContext.PAGE_SCOPE);
        return super.doEndTag();
    }

    public String getVar() {
        return var;
    }
    public void setVar(String var) {
        this.var = var;
    }
    public String getRelPath() {
        return relPath;
    }
    public void setRelPath(String relPath) {
        this.relPath = relPath;
    }
    public Integer getMaxItems() {
        return maxItems;
    }
    public void setMaxItems(Integer maxItems) {
        this.maxItems = maxItems;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public void setAbsPath(String absPath) {
        this.absPath = absPath;
    }
}