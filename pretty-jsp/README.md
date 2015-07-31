# Pretty-jsp
Java taglib to eradicate Java from JSP file in AEM/CQ projects, forever and ever

## Top navigation example

```html
<pjsp:pageChildren path="/content/site/en" var="navPages">
<ul class="nav navbar-nav">
    <pjsp:properties var="topNavProps" resource="${resource}"/>
    <c:forEach items="${navPages}" var="navPage">
        <pjsp:isPageEqualOrChildOf var="underThisNavPage" targetPage="${navPage}"/>
        <pjsp:properties var="navPageResourceProps" page="${navPage}"/>
        <pjsp:isInList var="dropdown" collection="${topNavProps.subMenuPages}" target="${navPage.path}"/>
        <li class="${underThisNavPage ? 'active' : ''}">
            <a href="${navPage.path}.html"><span>${navPage.title}</span></a>
        </li>
    </c:forEach>
</ul>
```