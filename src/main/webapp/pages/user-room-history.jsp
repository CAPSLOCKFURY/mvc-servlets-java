<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <jsp:include page="header.jsp"/>
    <h1>Room history</h1>
    <c:forEach items="${rooms}" var="room">
        <hr>
        <h1>Room number: ${room.number}</h1>
        <h1>Room name: ${room.name}</h1>
        <h1>Room price: ${room.price}</h1>
        <h1>Room capacity: ${room.capacity}</h1>
        <h1>Room class: ${room.className}</h1>
        <h1>Check in date: ${room.checkInDate}</h1>
        <h1>Check out date: ${room.checkOutDate}</h1>
        <h2><a href="<c:url value="/project/room">
                    <c:param name="id" value="${room.id}"/>
                 </c:url>">See room</a></h2>
        <hr>
    </c:forEach>
</body>
</html>
