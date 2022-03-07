<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <jsp:include page="header.jsp"/>
    <jsp:useBean id="room" scope="request" class="models.dto.RoomExtendedInfo"/>
    <h1>Room Number: ${room.number}</h1>
    <h1>Room name: ${room.name}</h1>
    <h1>Room class: ${room.className}</h1>
    <h1>Room capacity: ${room.capacity}</h1>
    <h1>Room status: ${room.status}</h1>
    <h1>Room price: ${room.price}</h1>
    <h1>Room Dates:</h1>
    <c:if test="${room.dates.size() == 0}">
        <h2>Room is free for all dates</h2>
    </c:if>
    <c:forEach var="date" items="${room.dates}">
        <h2>Check In: ${date.checkInDate}| Check Out: ${date.checkOutDate}</h2>
    </c:forEach>
</body>
</html>
