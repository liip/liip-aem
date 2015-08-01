package ch.liip.aem.taglib;

import ch.liip.aem.request.RequestObjects;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

@SuppressWarnings("serial")
public class RequestObjectsTag extends ComponentTagSupport {

    private String var;

    @Override
    public int doStartTag() throws JspException {
        RequestObjects requestObjects = createRequestObjects();
        pageContext.setAttribute(var, requestObjects, PageContext.PAGE_SCOPE);
        return Tag.SKIP_BODY;
    }

    public void setVar(String var) {
        this.var = var;
    }
}
