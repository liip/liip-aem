package ch.liip.aem.taglib;

import ch.liip.aem.clientlib.ClientLibrary;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import java.util.ArrayList;

/**
 * @author Matthieu Cornut
 */
public class IncludeClientLibTag extends ComponentTagSupport {

    private String categories;
    private String js;
    private String css;
    private String theme;
    private Boolean themed;

    public IncludeClientLibTag() {
        super();
    }

    @Override
    public int doEndTag() throws JspException {

        ArrayList<ClientLibrary> clientLibsBuffer = getBuffer();
        clientLibsBuffer.add(new ClientLibrary(categories, js, css, theme, themed));

        return super.doEndTag();
    }

    @SuppressWarnings("unchecked")
    private ArrayList<ClientLibrary> getBuffer(){
        ArrayList<ClientLibrary> clientLibsBuffer = (ArrayList<ClientLibrary>) this.pageContext.getAttribute(ClientLibrary.CLIENT_LIBS_BUFFER, PageContext.REQUEST_SCOPE);
        if(null == clientLibsBuffer){
            clientLibsBuffer = new ArrayList<ClientLibrary>();
            this.pageContext.setAttribute(ClientLibrary.CLIENT_LIBS_BUFFER, clientLibsBuffer, PageContext.REQUEST_SCOPE);
        }
        return clientLibsBuffer;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getJs() {
        return js;
    }

    public void setJs(String js) {
        this.js = js;
    }

    public String getCss() {
        return css;
    }

    public void setCss(String css) {
        this.css = css;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public Boolean getThemed() {
        return themed;
    }

    public void setThemed(Boolean themed) {
        this.themed = themed;
    }

}
