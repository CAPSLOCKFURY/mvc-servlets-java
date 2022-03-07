<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="header.jsp"/>
    <title>Profile</title>
</head>
<body>
<h1>Welcome: ${sessionScope.user.firstName} ${sessionScope.user.lastName}</h1>
<h2>Email: ${sessionScope.user.email}</h2>
<h2>Your balance: ${sessionScope.user.balance}</h2>
<h3>Role: ${sessionScope.user.role}</h3>
<a href="<c:url value="/project/profile/my-room-requests"/>">My Room Requests</a>
<c:if test="${sessionScope.user.role == 2}">
    <a href="<c:url value="/project/admin/room-requests"/>">Room Requests</a>
</c:if>
<a href="<c:url value="/project/logout"/>">Logout</a>
</body>
</html>
