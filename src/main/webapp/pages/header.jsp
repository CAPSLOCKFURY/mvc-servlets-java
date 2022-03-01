<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/tlds/formlib" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="content_lang" value="${cookie.get(\"Content-Language\") == null ? \"en\" : cookie.get(\"Content-Language\").getValue()}"/>
<fmt:setLocale value="${content_lang}"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<body>
<a href="<c:url value="/project"/>"><fmt:message key="header.home"/></a>
<a href="<c:url value="/project/register"/>"><fmt:message key="header.register"/></a>
<a href="<c:url value="/project/login"/>"><fmt:message key="header.login"/></a>
<form action="<c:url value="/project/change-language"/>" method="get">
    <select name="lang">
        <option value="en" <c:if test="${content_lang.equals(\"en\")}">selected</c:if>>English</option>
        <option value="ru" <c:if test="${content_lang.equals(\"ru\")}">selected</c:if>>Русский</option>
    </select>
    <button type="submit">Change Language</button>
</form>
</body>
</html>
