package ch.liip.aem.taglib;

import ch.liip.aem.exceptions.TaglibExceptionException;
import ch.liip.aem.request.utils.Preconditions;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

@SuppressWarnings("serial")
public class IsInListTag extends ComponentTagSupport {

    private String var;
    private Object collection;
    private Object target;

    @Override
    public int doStartTag() throws JspException {
        Preconditions.checkNotNull(collection, "Collection is null");
        Collection<String> actualCollection;
        if (collection instanceof String) {
            actualCollection = new HashSet<String>(Arrays.asList(new String[]{(String)collection}));
        } else if (collection instanceof String[]) {
            actualCollection = new HashSet<String>(Arrays.asList((String[])collection));
        } else {
            throw new TaglibExceptionException(String.format("Cannot handle object of type '%s'", collection.getClass().getName()));
        }
        Boolean result = actualCollection.contains(target);
        pageContext.setAttribute(var, result, PageContext.PAGE_SCOPE);
        return SKIP_BODY;
    }

    public void setVar(String var) {
        this.var = var;
    }

    public void setCollection(Object collection) {
        this.collection = collection;
    }

    public void setTarget(Object target) {
        this.target = target;
    }
}
