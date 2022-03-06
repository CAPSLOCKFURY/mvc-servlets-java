<%@ page import="models.User" %>
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
<h1>All our rooms</h1>
<c:forEach var="room" items="${rooms}">
    <hr>
    <h1>-------------Room info----------------</h1>
    <h1>Room number: ${room.number}</h1>
    <h1>Room name: ${room.name}</h1>
    <h1>Room class: ${room.class_name}</h1>
    <h1>Room status: ${room.status}</h1>
    <h1>Room capacity: ${room.capacity}</h1>
    <h1>Room price: ${room.price}</h1>
    <h1>Room will be free at: ${room.check_out_date}</h1>
    <hr>
</c:forEach>
</body>
</html>