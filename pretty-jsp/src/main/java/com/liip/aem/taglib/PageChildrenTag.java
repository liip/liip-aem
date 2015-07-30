package com.liip.aem.taglib;


import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageFilter;
import com.liip.aem.exceptions.ComponentException;
import com.liip.aem.request.RequestObjects;
import com.liip.aem.utils.StrUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import java.io.IOException;
import java.util.*;

import static com.liip.aem.request.utils.Preconditions.checkNotNull;

/**
 * Created by mcornut on 04.02.15.
 */
public class PageChildrenTag extends ComponentTagSupport {

    private String path;
    private String var;
    private Integer maxItems;
    private String orderBy;
    private Boolean reverse;

    private final String DATE_FORMAT = "dd/MM/yy";

    public PageChildrenTag() {
        super();
    }

    @Override
    public int doEndTag() throws JspException {
        Page rootPage = null;
        RequestObjects requestObjects = createRequestObjects();

        if (reverse!=null) {
            checkNotNull(orderBy, "The parameter 'reverse' is definable only if the paramter 'orderBy' is defined");
        }

        try {
            rootPage = determineRootPage(requestObjects);
        } catch (ComponentException e) {
            try {
                this.pageContext.getOut().print(e.getMessage());
            } catch (IOException e2) {
                logger.error("Error printing to page", e);
            }
            return super.doEndTag();
        }

        // Result list
        List<Page> items = new ArrayList<Page>();

        Iterator<Page> children = rootPage.listChildren(new PageFilter(requestObjects.getSlingHttpServletRequest()));
        int count=0;
        while (children.hasNext() && (null==maxItems || count < maxItems)) {
            count++;
            Page child = children.next();
            items.add(child);
        }

        if(null != orderBy) {
            items = orderBy(items);
        }

        this.pageContext.setAttribute(var, items, PageContext.PAGE_SCOPE);
        return super.doEndTag();
    }

    private Page determineRootPage(RequestObjects requestObjects) throws ComponentException {
        String rootPagePath;
        if (StrUtils.isEmpty(this.path)) {
            rootPagePath = requestObjects.getCurrentPage().getPath();
        } else {
            rootPagePath = this.path;
        }

        if (rootPagePath == null) {
            throw new ComponentException("Unable to find rootPagePath. [path = " + this.path + "]");
        }

        Page rootPage = requestObjects.getPageManager().getPage(rootPagePath);
        if (rootPage==null) {
            throw new ComponentException("Unable to find rootPage [path = " + rootPagePath + "]");
        }
        return rootPage;
    }

    private List<Page> orderBy(List<Page> items){
        Collections.sort(items, new PageComparator(this.orderBy, this.reverse));
        return items;
    }

    public String getVar() {
        return var;
    }
    public void setVar(String var) {
        this.var = var;
    }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public Integer getMaxItems() {
        return maxItems;
    }
    public void setMaxItems(Integer maxItems) {
        this.maxItems = maxItems;
    }
    public String getOrderBy() {
        return orderBy;
    }
    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public void setReverse(Boolean reverse) {
        this.reverse = reverse;
    }
}

class PageComparator implements Comparator<Page> {
    private String orderBy;
    private boolean reverse;

    PageComparator(String orderBy, Boolean reverse) {
        this.orderBy = orderBy;
        this.reverse = reverse!=null && reverse;
    }

    public int compare(Page p1, Page p2) {
        Object field1 = p1.getProperties().get(orderBy);
        Object field2 = p2.getProperties().get(orderBy);

        Object field1ToCompare = reverse?field2:field1;
        Object field2ToCompare = reverse?field1:field2;

        if (field1ToCompare==null) {
            return 1;
        } else if (field2ToCompare==null) {
            return -1;
        } else if (field1ToCompare instanceof Comparable && field2ToCompare instanceof  Comparable) {
            int c = ((Comparable) field1ToCompare).compareTo(field2ToCompare);
            return c;
        } else {
            int i = field1ToCompare.toString().compareTo(field2ToCompare.toString());
            return i;
        }
    }
}


