package com.liip.aem.request;

import com.adobe.granite.security.user.UserProperties;
import com.day.cq.personalization.UserPropertiesUtil;
import com.day.cq.wcm.api.LanguageManager;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.liip.aem.request.utils.PageUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.scripting.SlingBindings;
import org.apache.sling.api.scripting.SlingScriptHelper;
import org.apache.sling.scripting.jsp.util.TagUtil;
import org.apache.sling.settings.SlingSettingsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Session;
import javax.servlet.jsp.PageContext;
import java.util.Set;

/**
 * @author Fabrice Hong
 * @date 10.02.15
 */
public class RequestObjects {

    protected Logger logger;

    private SlingHttpServletRequest slingHttpServletRequest;
    private SlingHttpServletResponse slingHttpServletResponse;
    private SlingScriptHelper slingScriptHelper;
    private LanguageManager languageManager;
    private Resource resource;
    private ResourceResolver resourceResolver;
    private PageManager pageManager;
    private Page currentPage;
    private Page wrappingPage;
    private Page languageRootPage;
    private UserProperties userProperties;
    private Boolean isAnonymous;

    public RequestObjects(PageContext pageContext) {
        this(TagUtil.getRequest(pageContext), (SlingHttpServletResponse)pageContext.getResponse());
    }

    public RequestObjects(SlingHttpServletRequest request, SlingHttpServletResponse response) {
        this.slingHttpServletRequest = request;
        this.slingHttpServletResponse = response;
        this.logger = LoggerFactory.getLogger(getClass());
    }

    public SlingScriptHelper getScriptHelper() {
        if (this.slingScriptHelper==null) {

            final SlingBindings bindings = (SlingBindings) this.slingHttpServletRequest.getAttribute(SlingBindings.class.getName());
            slingScriptHelper = bindings.getSling();
        }
        return slingScriptHelper;
    }

    public LanguageManager getLanguageManager() {
        if (this.languageManager==null) {
            this.languageManager = getService(LanguageManager.class);
        }
        return this.languageManager;
    }

    public <T> T getService(Class<T> clazz) {
        return getScriptHelper().getService(clazz);
    }

    public Resource getResource() {
        if (this.resource==null) {
            this.resource = this.slingHttpServletRequest.getResource();
        }
        return this.resource;
    }

    public Page getCurrentPage() {
        if (this.currentPage==null) {
            this.currentPage = PageUtils.getPage(getPageManager(), getResource(), false);
        }
        return this.currentPage;
    }

    public Page getWrappingPage() {
        if (this.wrappingPage==null) {
            this.wrappingPage = PageUtils.getPage(getPageManager(), getResource(), true);
        }
        return this.wrappingPage;
    }

    public PageManager getPageManager() {
        if (this.pageManager==null) {
            this.pageManager = getResourceResolver().adaptTo(PageManager.class);
        }
        return pageManager;
    }

    public ResourceResolver getResourceResolver() {
        if (this.resourceResolver==null) {
            this.resourceResolver = this.slingHttpServletRequest.getResourceResolver();
        }
        return this.resourceResolver;
    }

    public Page getLanguageRootPage() {
        if (this.languageRootPage==null) {
            Page page = getWrappingPage();
            this.languageRootPage = PageUtils.getLanguageRootPage(getLanguageManager(), page);
        }
        return this.languageRootPage;

    }

    public Session getJcrSession() {
        return getResourceResolver().adaptTo(Session.class);
    }

    public UserProperties getUserProperties(){
        if(this.userProperties == null){
            this.userProperties = slingHttpServletRequest.adaptTo(UserProperties.class);
        }
        return this.userProperties;
    }

    public Boolean getIsAnonymous(){
        if(this.isAnonymous == null) {
            this.isAnonymous = UserPropertiesUtil.isAnonymous(getSlingHttpServletRequest());
        }
        return this.isAnonymous;
    }

    public SlingHttpServletRequest getSlingHttpServletRequest() {
        return slingHttpServletRequest;
    }

    public SlingHttpServletResponse getSlingHttpServletResponse() {
        return slingHttpServletResponse;
    }
}
