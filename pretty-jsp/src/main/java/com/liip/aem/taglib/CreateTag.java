package com.liip.aem.taglib;

import com.liip.aem.exceptions.TaglibExceptionException;
import com.liip.aem.request.Action;
import com.liip.aem.request.RequestAware;
import com.liip.aem.request.RequestObjects;
import org.apache.sling.commons.classloader.DynamicClassLoaderManager;

import javax.servlet.jsp.JspException;
import java.lang.reflect.Constructor;

/**
 * Created by mcornut on 13.02.15.
 */
public class CreateTag extends ComponentTagSupport {

    private Class<?> type;

    @Override
    public int doStartTag() throws JspException {

        RequestObjects requestObjects = new RequestObjects(this.pageContext);

        Object instance = createClass(requestObjects);

        if (instance instanceof Action) {
            ((Action)instance).execute();
        }

        return SKIP_BODY;
    }

    private Object createClass(RequestObjects requestObjects) {
        try {

            DynamicClassLoaderManager dynamicClassLoaderManager = requestObjects.getScriptHelper().getService(DynamicClassLoaderManager.class);
            Class clazz = dynamicClassLoaderManager.getDynamicClassLoader().loadClass(this.type.getName());

            Object instance;
            Constructor constructor;
            if (RequestAware.class.isAssignableFrom(clazz)) {
                constructor = clazz.getConstructor(RequestObjects.class);
                instance = constructor.newInstance(requestObjects);
            } else {
                constructor = clazz.getConstructor();
                instance = constructor.newInstance();
            }

            return instance;

        } catch (Throwable e) {
            throw new TaglibExceptionException(String.format("Unable to create class of type '%s' in resource '%s'", this.type.getName(), requestObjects.getResource()), e);
        }
    }

    public void setType(Class<?> type) {
        this.type = type;
    }
}
