<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/WEB-INF/tlds/formlib" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="content_lang" value="${cookie.get(\"Content-Language\") == null ? \"en\" : cookie.get(\"Content-Language\").getValue()}"/>
<fmt:setLocale value="${content_lang}"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <title><fmt:message key="title.login"/></title>
</head>
<body>
<jsp:include page="header.jsp"/>
<form method="post">
    <h1><fmt:message key="title.login"/></h1>
    <form method="post">
        <form:renderForm formClassPath="forms.LoginForm"/>
        <button type="submit"><fmt:message key="title.login"/></button>
    </form>
    <c:forEach var="error" items="${errors}">
        <h2 style="color:red">${error}</h2>
    </c:forEach>
</form>
</body>
</html>
