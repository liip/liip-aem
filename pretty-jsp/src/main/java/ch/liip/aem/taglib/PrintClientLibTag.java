package ch.liip.aem.taglib;

import ch.liip.aem.clientlib.ClientLibrary;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import java.util.ArrayList;

/**
 * @author Matthieu Cornut
 */
public class PrintClientLibTag extends ComponentTagSupport {

    @Override
    @SuppressWarnings("unchecked")
    public int doEndTag() throws JspException {

        ArrayList<ClientLibrary> clientLibsBuffer = (ArrayList<ClientLibrary>)
                this.pageContext.getAttribute(ClientLibrary.CLIENT_LIBS_BUFFER, PageContext.REQUEST_SCOPE);

        if(null != clientLibsBuffer){
            for(ClientLibrary clientLib : clientLibsBuffer){
                printInPlace(clientLib);
            }
        }

        return super.doEndTag();
    }

    private void printInPlace(ClientLibrary clientLibrary) throws JspException {
        com.adobe.granite.ui.tags.IncludeClientLibraryTag adobeTag = new com.adobe.granite.ui.tags.IncludeClientLibraryTag();
        adobeTag.setPageContext(pageContext);
        adobeTag.setCategories(clientLibrary.getCategories());
        adobeTag.setJs(clientLibrary.getJs());
        adobeTag.setCss(clientLibrary.getCss());
        adobeTag.setTheme(clientLibrary.getTheme());
        adobeTag.setThemed(clientLibrary.getThemed() == Boolean.TRUE);
        adobeTag.doEndTag();
    }
}
