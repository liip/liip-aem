package com.liip.aem.request.utils;

import com.day.cq.wcm.api.LanguageManager;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.sling.api.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Fabrice Hong
 * @date 10.02.15
 */
public class PageUtils {

    public static final int ROOT_PAGE_DEPTH = 3;

    protected static final Logger logger = LoggerFactory.getLogger(PageUtils.class);

    // TODO: change this strategy
    public static Page getLanguageRootPage(LanguageManager languageManager, Page referencePage) {

        Page homepage = languageManager.getLanguageRoot(referencePage.getContentResource());
        if (homepage == null) {
            logger.error(String.format("warning, impossible to get the root page. Failing back to depth strategy " +
                    "(depth=%d)", ROOT_PAGE_DEPTH));
            homepage = referencePage.getAbsoluteParent(ROOT_PAGE_DEPTH);
            if (homepage == null) {
                throw new IllegalStateException(String.format("Impossible to get the languageRoot with the depth " +
                        "strategy failback (depth=%d)", ROOT_PAGE_DEPTH));
            }
        } else if (homepage.getDepth() != ROOT_PAGE_DEPTH) {
            logger.error(String.format("warning, the the rootpage should be at depth (expected depth is %d ",
                    ROOT_PAGE_DEPTH));
        }
        return homepage;
    }

    public static boolean isPageEqualOrChildOf(Page pageToTest, Page parent) {
        if (pageToTest==null) {
            return false;
        }
        if (pageToTest.equals(parent)) {
            return true;
        }

        return isPageEqualOrChildOf(pageToTest.getParent(), parent);
    }

    public static Page getPage(PageManager pageManager, Resource resource, boolean findInHierarchy) {
        Preconditions.checkNotNull(resource, "Trying to get the page from a null resource");
        Page containingPage = pageManager.getContainingPage(resource);
        if (containingPage!=null) {
            return containingPage;
        }
        if (findInHierarchy) {
            Resource parent = resource.getParent();
            Preconditions.checkNotNull(parent, String.format("The parent of '%s' doesn't exists", resource));
            return getPage(pageManager, parent, findInHierarchy);
        }
        return null;
    }

}
