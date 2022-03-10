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
    <c:if test="${roomRequest.roomId != 0}">
        <h1>Assigned room: <a href="<c:url value="/project/room"><c:param name="id" value="${roomRequest.roomId}"/></c:url> ">
${roomRequest.roomId}</a></h1>
        <form method="post" action="<c:url value="/project/profile/my-room-requests/confirm"/>">
            <input type="hidden" name="requestId" value="${roomRequest.id}">
            <button type="submit">Confirm request</button>
        </form>
    </c:if>
    <a href="<c:url value="/project/profile/my-room-requests/disable"><c:param name="id" value="${roomRequest.id}"/></c:url>">Disable</a>
    <hr>
</c:forEach>
</body>
</html>
