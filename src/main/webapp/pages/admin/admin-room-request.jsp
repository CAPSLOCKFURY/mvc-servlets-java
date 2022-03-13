<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<jsp:include page="/pages/header.jsp"/>
<jsp:useBean id="roomRequest" scope="request" class="models.dto.AdminRoomRequestDTO"/>
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
<hr>
<c:if test="${roomRequest.status.equals('awaiting') || roomRequest.status.equals('awaiting confirmation')}">
<form method="post" action="<c:url value="/project/admin/room-request/close"/>">
    <input type="hidden" name="requestId" value="${roomRequest.id}">
    <textarea name="managerComment"></textarea>
    <button type="submit">Close this request</button>
</form>
</c:if>
<c:if test="${roomRequest.status.equals('awaiting')}">
    <h1>Suitable rooms</h1>
    <c:set var="page" value="${param.page == null ? 1 : param.page}"/>
    <tags:pagination page="${page}" entitiesPerPage="10" entitiesCount="${rooms.size()}"/>
    <c:forEach var="room" items="${rooms}" begin="0" end="9" step="1">
        <h1>Room number: ${room.number}</h1>
        <h1>Room name: ${room.name}</h1>
        <h1>Room class: ${room.className}</h1>
        <h1>Room status: ${room.status}</h1>
        <h1>Room capacity: ${room.capacity}</h1>
        <h1>Room price: ${room.price}</h1>
        <form method="post" action="<c:url value="/project/admin/room-request/assign"/>">
            <input name="roomId" type="hidden" value=${room.id}>
            <input name="requestId" type="hidden" value=${roomRequest.id}>
            <button type="submit">Assign this room to request</button>
        </form>
        <hr>
    </c:forEach>
</c:if>
<tags:pagination page="${page}" entitiesPerPage="10" entitiesCount="${rooms.size()}"/>
</body>
</html>
