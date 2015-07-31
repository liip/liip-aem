# Pretty-jsp
Java taglib for AEM / CQ projects.

Designed to eradicate Java from JSP file, forever and ever.

## Top navigation example

```html
<%@ taglib prefix="pretty" uri="https://github.com/liip/liip-aem/tree/master/pretty-jsp" %>

<pretty:pageChildren path="/content/site/en" var="navPages">
<ul class="nav navbar-nav">
    <pretty:properties var="topNavProps" resource="${resource}"/>
    <c:forEach items="${navPages}" var="navPage">
        <pretty:isPageEqualOrChildOf var="underThisNavPage" targetPage="${navPage}"/>
        <pretty:properties var="navPageResourceProps" page="${navPage}"/>
        <pretty:isInList var="dropdown" collection="${topNavProps.subMenuPages}" target="${navPage.path}"/>
        <li class="${underThisNavPage ? 'active' : ''}">
            <a href="${navPage.path}.html"><span>${navPage.title}</span></a>
        </li>
    </c:forEach>
</ul>
```