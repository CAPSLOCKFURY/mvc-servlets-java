<%@ taglib prefix="form" uri="/WEB-INF/tlds/formlib" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <jsp:include page="header.jsp"/>
    <form method="post">
        <input name="firstName" type="text" placeholder="First name" value="${dbUser.firstName}">
        <input name="lastName" type="text" placeholder="Last name" value="${dbUser.lastName}">
        <button type="submit">Update Profile</button>
        <c:forEach var="error" items="${errors}">
            <h2 style="color:red">${error}</h2>
        </c:forEach>
    </form>
</body>
</html>
