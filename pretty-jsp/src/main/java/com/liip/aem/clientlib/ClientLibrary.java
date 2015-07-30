package com.liip.aem.clientlib;

/**
 * Created by mcornut on 26.02.15.
 */
public final class ClientLibrary {
    private final String categories;
    private final String js;
    private final String css;
    private final String theme;
    private final Boolean themed;

    public static final String CLIENT_LIBS_BUFFER = "clientLibsBuffer";

    public ClientLibrary(String categories, String js, String css, String theme, Boolean themed) {
        this.categories = categories;
        this.js = js;
        this.css = css;
        this.theme = theme;
        this.themed = themed;
    }


    public String getCategories() {
        return categories;
    }
    public String getJs() {
        return js;
    }
    public String getCss() {
        return css;
    }
    public String getTheme() {
        return theme;
    }
    public Boolean getThemed() {
        return themed;
    }
}
