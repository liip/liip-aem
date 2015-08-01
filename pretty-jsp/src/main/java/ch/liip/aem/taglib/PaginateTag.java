package ch.liip.aem.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import java.util.List;

/**
 * @author Matthieu Cornut
 */
public class PaginateTag extends ComponentTagSupport{

    private String pageNumber;
    private Integer maxPerPage;
    private String var;
    private List items;

    public Integer DEFAULT_MAX_PER_PAGE = 5;

    public PaginateTag() {
        super();
    }

    @Override
    public int doEndTag() throws JspException {

        maxPerPage = (null != maxPerPage) ? maxPerPage : DEFAULT_MAX_PER_PAGE;

        int pageNumber;
        try {
            pageNumber = Integer.parseInt(this.pageNumber);
        }catch (NumberFormatException nfe){
            pageNumber = 1;
        }

        this.pageContext.setAttribute(var, buildPagination(pageNumber), PageContext.PAGE_SCOPE);

        return super.doEndTag();
    }

    private List getCurrentPageItems(List items, int pageNumber){
        int startIndex = (pageNumber * maxPerPage - maxPerPage);
        Integer endIndex = startIndex + maxPerPage - 1;
        endIndex = Math.min(items.size(), endIndex);
        return items.subList(startIndex, endIndex);
    }

    private PaginationResult buildPagination(int pageNumber){

        int numberOfPages = (items.size() / maxPerPage) + ((items.size() % maxPerPage > 0)?1:0);
        List currentPageItems = getCurrentPageItems(items, pageNumber);

        return new PaginationResult(currentPageItems, numberOfPages, pageNumber);
    }

    public String getPageNumber() {
        return pageNumber;
    }
    public void setPageNumber(String pageNumber) {
        this.pageNumber = pageNumber;
    }
    public Integer getMaxPerPage() {
        return maxPerPage;
    }
    public void setMaxPerPage(Integer maxPerPage) {
        this.maxPerPage = maxPerPage;
    }
    public String getVar() {
        return var;
    }
    public void setVar(String paginationVar) {
        this.var = paginationVar;
    }
    public List getItems() {
        return items;
    }
    public void setItems(List items) {
        this.items = items;
    }

    public static final class PaginationResult{
        private List items;
        private int numberOfPages;
        private int currentPage;

        public PaginationResult(List items, int numberOfPages, int currentPage) {
            this.items = items;
            this.numberOfPages = numberOfPages;
            this.currentPage = currentPage;
        }

        public List getItems() {
            return items;
        }
        public void setItems(List items) {
            this.items = items;
        }
        public int getNumberOfPages() {
            return numberOfPages;
        }
        public void setNumberOfPages(int numberOfPages) {
            this.numberOfPages = numberOfPages;
        }
        public int getCurrentPage() {
            return currentPage;
        }
        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }
    }
}
