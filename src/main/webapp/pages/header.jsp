<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/tlds/formlib" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="content_lang" value="${cookie.get(\"Content-Language\") == null ? \"en\" : cookie.get(\"Content-Language\").getValue()}"/>
<fmt:setLocale value="${content_lang}"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
        <a class="navbar-brand" href="<c:url value="/project"/>"><fmt:message key="header.home"/></a>
    </div>
    <div class="container-fluid justify-content-end">
        <ul class="navbar-nav">
            <c:if test="${sessionScope.user == null}">
                <li class="nav-item">
                    <a class="link-primary me-2 text-decoration-none" href="<c:url value="/project/login"/>"><fmt:message key="header.login"/></a>
                </li>
                <li class="nav-item">
                    <a class="link-primary me-2 text-decoration-none" href="<c:url value="/project/register"/>"><fmt:message key="header.register"/></a>
                </li>
            </c:if>
            <c:if test="${sessionScope.user != null}">
                <li class="nav-item">
                    <a class="link-info me-2 text-decoration-none text-nowrap" href="<c:url value="/project/profile"/>"><fmt:message key="header.myProfile"/></a>
                </li>
                <li class="nav-item">
                    <a class="link-info me-2 text-decoration-none text-nowrap" href="<c:url value="/project/room-request"/>"><fmt:message key="header.requestRoom"/></a>
                </li>
                <li class="nav-item">
                    <a class="link-info me-2 text-decoration-none text-nowrap" href="<c:url value="/project/profile/my-room-requests"/>"><fmt:message key="profile.myRoomRequests"/></a>
                </li>
                <li class="nav-item">
                    <a class="link-info me-2 text-decoration-none text-nowrap" href="<c:url value="/project/profile/my-billings"/>"><fmt:message key="profile.myBillings"/></a>
                </li>
            </c:if>
        </ul>
        <form class="d-flex my-0" action="<c:url value="/project/change-language"/>" method="get">
            <select class="form-control me-2 form-select" name="lang">
                <option value="en" <c:if test="${content_lang.equals(\"en\")}">selected</c:if>>English</option>
                <option value="ru" <c:if test="${content_lang.equals(\"ru\")}">selected</c:if>>Русский</option>
            </select>
            <button class="nav-item btn-sm btn-dark text-nowrap" type="submit"><fmt:message key="header.changeLang"/></button>
        </form>
    </div>
</nav>
</body>
</html>
