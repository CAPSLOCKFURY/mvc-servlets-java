<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<c:forEach var="message" items="${messages}">
    <h2 style="color:red">${message}</h2>
</c:forEach>
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
        <c:if test="${roomRequest.status.equals('awaiting confirmation')}">
            <form method="post" action="<c:url value="/project/profile/my-room-requests/confirm"/>">
                <input type="hidden" name="requestId" value="${roomRequest.id}">
                <button type="submit">Confirm request</button>
            </form>
            <form method="post" action="<c:url value="/project/profile/my-room-requests/decline"/>">
                <textarea name="comment">${roomRequest.comment}</textarea>
                <input type="hidden" name="requestId" value="${roomRequest.id}">
                <button type="submit">Decline room</button>
            </form>
        </c:if>
    </c:if>
    <c:if test="${roomRequest.status.equals('awaiting')}">
        <a href="<c:url value="/project/profile/my-room-requests/disable"><c:param name="id" value="${roomRequest.id}"/></c:url>">Disable</a>
    </c:if>
    <hr>
</c:forEach>
</body>
</html>
