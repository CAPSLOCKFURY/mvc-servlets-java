<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<c:forEach var="roomRequest" items="${roomRequests}">
    <hr>
    <h1>Capacity: ${roomRequest.capacity}</h1>
    <h1>Room class: ${roomRequest.roomClass}</h1>
    <h1>Check In Date: ${roomRequest.checkInDate}</h1>
    <h1>Check Out Date: ${roomRequest.checkOutDate}</h1>
    <h1>Your Comment: ${roomRequest.comment}</h1>
    <h1>Status: ${roomRequest.status}</h1>
    <hr>
</c:forEach>
</body>
</html>
