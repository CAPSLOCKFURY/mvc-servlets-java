<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <jsp:include page="/pages/header.jsp"/>
    <h1>Admin room requests</h1>
    <c:forEach var="roomRequest" items="${roomRequests}">
        <hr>
        <h1>Room capacity: ${roomRequest.capacity}</h1>
        <h1>Room class: ${roomRequest.className}</h1>
        <h1>Comment: ${roomRequest.comment}</h1>
        <h1>Request status: ${roomRequest.status}</h1>
        <h1>Check in date: ${roomRequest.checkInDate}</h1>
        <h1>Check out date: ${roomRequest.checkOutDate}</h1>
        <h1>User login: ${roomRequest.login}</h1>
        <h1>User email: ${roomRequest.email}</h1>
        <h1>First name: ${roomRequest.firstName}</h1>
        <h1>Last name: ${roomRequest.lastName}</h1>
        <h2><a href="<c:url value="/project/admin/room-request"><c:param name="id" value="${roomRequest.id}"/></c:url> ">See request</a></h2>
        <hr>
    </c:forEach>
</body>
</html>
