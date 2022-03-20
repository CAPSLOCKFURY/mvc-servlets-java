<%@ taglib prefix="form" uri="/WEB-INF/tlds/formlib" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="content_lang" value="${cookie.get(\"Content-Language\") == null ? \"en\" : cookie.get(\"Content-Language\").getValue()}"/>
<fmt:setLocale value="${content_lang}"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <title><fmt:message key="title.balance"/></title>
</head>
<body>
<jsp:include page="header.jsp"/>
<h1><fmt:message key="title.balance"/></h1>
<form method="post">
    <form:renderForm formClassPath="forms.AddBalanceForm"/>
    <button type="submit"><fmt:message key="balance.addBalance"/></button>
</form>
<c:forEach var="error" items="${errors}">
    <h2 style="color:red">${error}</h2>
</c:forEach>
</body>
</html>
