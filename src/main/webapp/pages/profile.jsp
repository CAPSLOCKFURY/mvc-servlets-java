<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="header.jsp"/>
    <title>Profile</title>
</head>
<body>
<jsp:useBean id="user" scope="request" class="models.User"/>
<h1>Welcome: ${user.firstName} ${user.lastName}</h1>
<h2>Email: ${user.email}</h2>
<h2>Your balance: ${user.balance}</h2>
<h3>Role: ${user.role}</h3>
<a href="<c:url value="/project/profile/my-room-requests"/>">My Room Requests</a>
<a href="<c:url value="/project/profile/balance"/>">Add balance</a>
<c:if test="${sessionScope.user.role == 2}">
    <a href="<c:url value="/project/admin/room-requests"/>">Room Requests</a>
</c:if>
<a href="<c:url value="/project/logout"/>">Logout</a>
</body>
</html>
