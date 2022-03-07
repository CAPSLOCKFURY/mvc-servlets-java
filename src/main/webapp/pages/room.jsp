<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <jsp:include page="header.jsp"/>
    <jsp:useBean id="room" scope="request" class="models.Room"/>
    <h1>Room Number: ${room.number}</h1>
    <h1>Room name: ${room.name}</h1>
    <h1>Room class: ${room.className}</h1>
    <h1>Room capacity: ${room.capacity}</h1>
    <h1>Room status: ${room.status}</h1>
    <h1>Room price: ${room.price}</h1>
</body>
</html>
