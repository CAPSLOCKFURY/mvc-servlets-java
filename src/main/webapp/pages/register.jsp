<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/tlds/formlib" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Register</title>
</head>
<body>
<jsp:include page="header.jsp"/>
    <h1>Register</h1>
    <form method="post">
        <form:renderForm formClassPath="forms.RegisterForm"/>
        <button type="submit">Register</button>
    </form>
    <c:forEach var="error" items="${errors}">
        <h2 style="color:red">${error}</h2>
    </c:forEach>
</body>
</html>
