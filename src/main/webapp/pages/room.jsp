<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="/WEB-INF/tlds/formlib" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="content_lang" value="${cookie.get(\"Content-Language\") == null ? \"en\" : cookie.get(\"Content-Language\").getValue()}"/>
<fmt:setLocale value="${content_lang}"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <title><fmt:message key="title.room"/></title>
</head>
<body>
    <jsp:include page="header.jsp"/>
    <jsp:useBean id="room" scope="request" class="models.dto.RoomExtendedInfo"/>
    <div class="ms-2 d-flex my-2">
        <div class="row card bg-light">
            <h1><fmt:message key="rooms.roomNumber"/> : ${room.number}</h1>
            <h1><fmt:message key="rooms.roomName"/> : ${room.name}</h1>
            <h1><fmt:message key="rooms.className"/> : ${room.className}</h1>
            <h1><fmt:message key="rooms.roomCapacity"/> : ${room.capacity}</h1>
            <h1><fmt:message key="rooms.roomStatus"/> : <fmt:message key="${'roomStatus.'.concat(room.status)}"/></h1>
            <h1><fmt:message key="rooms.roomPrice"/> : ${room.price}</h1>
        </div>
    </div>
    <c:if test="${!room.status.equals('unavailable')}">
        <c:if test="${sessionScope.user != null}">
            <script type="text/javascript" src="${pageContext.request.contextPath}/pages/js/room-price-calculator.js" defer></script>
            <h3 class="ms-2"><fmt:message key="room.orderRoom"/> :</h3>
            <input type="hidden" id="pricePerDay" value="${room.price}">
            <script type="text/javascript" src="${pageContext.request.contextPath}/pages/js/set-min-date-today.js" defer></script>
            <div class="d-flex ms-2">
                <form method="post" class="form-group bg-light">
                    <form:renderForm formClassPath="forms.BookRoomForm"/>
                    <button class="btn btn-outline-success" type="submit"><fmt:message key="room.bookThisRoom"/></button>
                </form>
            </div>
            <div class="ms-2">
                <c:forEach var="error" items="${errors}">
                    <h2 class="text-danger">${error}</h2>
                </c:forEach>
            </div>
            <div class="d-flex ms-2">
                <h2><fmt:message key="room.estimatedPrice"/> :</h2>
                <h2 class="text-success" id="calculatedPrice"></h2>
            </div>
        </c:if>
        <c:if test="${sessionScope.user.role == 2}">
            <form method="post" action="<c:url value="/project/admin/room/close"/>">
                <input type="hidden" name="id" value="${room.id}">
                <form:renderForm formClassPath="forms.CloseRoomForm"/>
                <button type="submit"><fmt:message key="room.closeThisRoom"/></button>
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
                <button type="submit"><fmt:message key="room.openRoom"/></button>
            </form>
        </c:if>
    </c:if>
    <div class="ms-2">
        <h1><fmt:message key="room.roomDates"/>:</h1>
        <c:if test="${room.dates.size() == 0}">
            <h2><fmt:message key="room.roomIsFreeForAllDates"/></h2>
        </c:if>
        <c:forEach var="date" items="${room.dates}">
            <h2><fmt:message key="rooms.checkInDate"/> : ${date.checkInDate}| <fmt:message key="rooms.checkOutDate"/> : ${date.checkOutDate}</h2>
        </c:forEach>
    </div>
</body>
</html>
