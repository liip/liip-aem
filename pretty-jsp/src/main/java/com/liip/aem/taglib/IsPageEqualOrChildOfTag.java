package com.liip.aem.taglib;


import com.day.cq.wcm.api.Page;
import com.liip.aem.request.RequestObjects;
import com.liip.aem.request.utils.PageUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @author Matthieu Cornut
 */
public class IsPageEqualOrChildOfTag extends ComponentTagSupport {

    private String var;

    private Page pageToTest;

    private Page targetPage;
    @Override
    public int doStartTag() throws JspException {
        RequestObjects requestObjects = createRequestObjects();
        Page actualPageToTest = determinePageToTest(requestObjects);
        boolean result = PageUtils.isPageEqualOrChildOf(actualPageToTest, this.targetPage);
        this.pageContext.setAttribute(var, result, PageContext.PAGE_SCOPE);
        return SKIP_BODY;
    }

    private Page determinePageToTest(RequestObjects requestObjects) {
        return this.pageToTest!=null?this.pageToTest:requestObjects.getCurrentPage();
    }

    public void setPageToTest(Page pageToTest) {
        this.pageToTest = pageToTest;
    }

    public void setTargetPage(Page targetPage) {
        this.targetPage = targetPage;
    }

    public void setVar(String var) {
        this.var = var;
    }
}
