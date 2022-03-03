<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<h1>Main Page</h1>
<c:forEach items="${users}" var="user">
    <a href="<c:url value="/project/user"><c:param name="id" value="${user.id}"/></c:url> ">User: ${user.login}</a><br>
</c:forEach>
</body>
</html>