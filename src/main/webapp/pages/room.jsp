<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="/WEB-INF/tlds/formlib" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/pages/js/room-price-calculator.js" defer></script>
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
    <c:if test="${!room.status.equals('unavailable')}">
        <c:if test="${sessionScope.user != null}">
            <h1>Order room:</h1>
            <input type="hidden" id="pricePerDay" value="${room.price}">
            <script type="text/javascript" src="${pageContext.request.contextPath}/pages/js/set-min-date-today.js" defer></script>
            <form method="post">
                <form:renderForm formClassPath="forms.BookRoomForm"/>
                <h2>Estimated price:</h2>
                <h2 id="calculatedPrice"></h2>
                <button type="submit">Book this room</button>
            </form>
            <c:forEach var="error" items="${errors}">
                <h2 style="color:red">${error}</h2>
            </c:forEach>
        </c:if>
        <c:if test="${sessionScope.user.role == 2}">
            <form method="post" action="<c:url value="/project/admin/room/close"/>">
                <input type="hidden" name="id" value="${room.id}">
                <form:renderForm formClassPath="forms.CloseRoomForm"/>
                <button type="submit">Close this room</button>
            </form>
            <c:forEach var="error" items="${errors}">
                <h2 style="color:red">${error}</h2>
            </c:forEach>
        </c:if>
    </c:if>
    <c:if test="${room.status.equals('unavailable')}">
        <c:if test="${sessionScope.user.role == 2}">
            <form method="post" action="<c:url value="/project/admin/room/open"/>">
                <input type="hidden" name="roomId" value="${room.id}">
                <button type="submit">Open room</button>
            </form>
        </c:if>
    </c:if>
    <h1>Room Dates:</h1>
    <c:if test="${room.dates.size() == 0}">
        <h2>Room is free for all dates</h2>
    </c:if>
    <c:forEach var="date" items="${room.dates}">
        <h2>Check In: ${date.checkInDate}| Check Out: ${date.checkOutDate}</h2>
    </c:forEach>
</body>
</html>
