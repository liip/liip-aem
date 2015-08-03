package ch.liip.aem.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@SuppressWarnings("serial")
public class DateFormatTag extends ComponentTagSupport {

    private String var;
    private String format;
    private Calendar date;

    @Override
    public int doStartTag() throws JspException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        pageContext.setAttribute(var, simpleDateFormat.format(date.getTime()));
        return SKIP_BODY;
    }

    public void setVar(String var) {
        this.var = var;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

}
