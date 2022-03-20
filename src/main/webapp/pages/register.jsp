<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/tlds/formlib" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="content_lang" value="${cookie.get(\"Content-Language\") == null ? \"en\" : cookie.get(\"Content-Language\").getValue()}"/>
<fmt:setLocale value="${content_lang}"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <title><fmt:message key="title.registration"/></title>
</head>
<body>
<jsp:include page="header.jsp"/>
<h1 class="ms-2"><fmt:message key="title.registration"/></h1>
<div class="d-flex ms-2">
    <form method="post" class="form-group">
        <form:renderForm formClassPath="forms.RegisterForm"/>
        <button class="btn btn-danger" type="submit"><fmt:message key="register.register"/></button>
    </form>
</div>
<c:forEach var="error" items="${errors}">
    <h2 class="text-danger">${error}</h2>
</c:forEach>
</body>
</html>
