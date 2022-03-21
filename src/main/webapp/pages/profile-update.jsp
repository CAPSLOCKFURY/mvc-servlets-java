<%@ taglib prefix="form" uri="/WEB-INF/tlds/formlib" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="content_lang" value="${cookie.get(\"Content-Language\") == null ? \"en\" : cookie.get(\"Content-Language\").getValue()}"/>
<fmt:setLocale value="${content_lang}"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <title><fmt:message key="title.profileUpdate"/></title>
</head>
<body>
    <jsp:include page="header.jsp"/>
    <h1 class="ms-2"><fmt:message key="title.profileUpdate"/></h1>
    <div class="d-flex ms-2">
        <form class="form-group" method="post">
            <input class="form-control my-2" name="firstName" type="text" value="${dbUser.firstName}">
            <input class="form-control my-2" name="lastName" type="text" value="${dbUser.lastName}">
            <button class="btn btn-outline-info" type="submit"><fmt:message key="updateProfile.updateProfile"/></button>
        </form>
    </div>
<div class="ms-2">
    <c:forEach var="error" items="${errors}">
        <h2 class="text-danger">${error}</h2>
    </c:forEach>
</div>
</body>
</html>
