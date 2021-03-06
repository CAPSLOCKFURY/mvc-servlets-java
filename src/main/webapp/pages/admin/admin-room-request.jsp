<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="url" uri="/WEB-INF/tlds/urllib" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="content_lang" value="${cookie.get(\"Content-Language\") == null ? \"en\" : cookie.get(\"Content-Language\").getValue()}"/>
<fmt:setLocale value="${content_lang}"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <title><fmt:message key="title.adminRoomRequest"/></title>
</head>
<body>
<jsp:include page="/pages/header.jsp"/>
<h1 class="ms-2"><fmt:message key="title.adminRoomRequest"/></h1>
<jsp:useBean id="roomRequest" scope="request" class="models.dto.AdminRoomRequestDTO"/>
<div class="d-flex">
    <div class="row card bg-light ms-2">
        <h1><fmt:message key="roomRequests.capacity"/> : ${roomRequest.capacity}</h1>
        <h1><fmt:message key="roomRequests.roomClass"/> : ${roomRequest.className}</h1>
        <h1><fmt:message key="roomRequests.admin.comment"/> : <c:out value="${roomRequest.comment}"/></h1>
        <h1><fmt:message key="roomRequests.managerComment"/> : <c:out value="${roomRequest.managerComment}"/></h1>
        <h1><fmt:message key="roomRequests.status"/> : <fmt:message key="${'roomRequestStatus.'.concat(roomRequest.status.replace(' ', '_'))}"/></h1>
        <h1><fmt:message key="roomRequests.checkInDate"/> : ${roomRequest.checkInDate}</h1>
        <h1><fmt:message key="roomRequests.checkOutDate"/> : ${roomRequest.checkOutDate}</h1>
        <h1><fmt:message key="roomRequests.admin.userLogin"/> : ${roomRequest.login}</h1>
        <h1><fmt:message key="roomRequests.admin.email"/> : ${roomRequest.email}</h1>
        <h1><fmt:message key="roomRequests.admin.firstName"/> : ${roomRequest.firstName}</h1>
        <h1><fmt:message key="roomRequests.admin.lastName"/> : ${roomRequest.lastName}</h1>
        <c:if test="${roomRequest.roomId != 0}">
            <h1><fmt:message key="roomRequests.assignedRoom"/>: <a target="_blank" href="<c:url value="/project/room"><c:param name="id" value="${roomRequest.roomId}"/></c:url>">
                    ${roomRequest.roomId}</a></h1>
        </c:if>
        <c:if test="${roomRequest.status.equals('awaiting') || roomRequest.status.equals('awaiting confirmation')}">
            <form method="post" action="<c:url value="/project/admin/room-request/close"/>">
                <div class="d-flex">
                    <input type="hidden" name="requestId" value="${roomRequest.id}">
                    <textarea class="form-control w-50 my-2" name="managerComment"></textarea>
                </div>
                <button class="btn btn-outline-danger my-2" type="submit"><fmt:message key="userRoomRequests.disable"/></button>
            </form>
        </c:if>
    </div>
</div>
<c:if test="${roomRequest.status.equals('awaiting')}">
    <h1 class="ms-2"><fmt:message key="adminRoomRequest.suitableRooms"/></h1>
    <form class="form-group" method="get">
        <div class="d-flex justify-content-start">
            <input type="hidden" name="id" value="${param.id}">
            <select class="form-select ms-2 me-1" style="width: 10%" name="orderColName">
                <option value="ID" <tags:selectedif test="${param.orderColName.equals('ID')}"/>><fmt:message key="rooms.order.id"/></option>
                <option value="PRICE" <tags:selectedif test="${param.orderColName.equals('PRICE')}"/>><fmt:message key="rooms.order.price"/></option>
                <option value="STATUS" <tags:selectedif test="${param.orderColName.equals('STATUS')}"/>><fmt:message key="rooms.order.status"/></option>
                <option value="CLASS" <tags:selectedif test="${param.orderColName.equals('CLASS')}"/>><fmt:message key="rooms.order.class"/></option>
                <option value="CAPACITY" <tags:selectedif test="${param.orderColName.equals('CAPACITY')}"/>><fmt:message key="rooms.order.capacity"/></option>
            </select>
            <select class="form-select mx-1" style="width: 10%;" name="orderDirection">
                <option value="ASC" <tags:selectedif test="${param.orderDirection.equals('ASC')}"/>><fmt:message key="asc"/></option>
                <option value="DESC" <tags:selectedif test="${param.orderDirection.equals('DESC')}"/>><fmt:message key="desc"/></option>
            </select>
            <button class="btn btn-outline-primary mx-1" type="submit"><fmt:message key="sortResults"/></button>
        </div>
    </form>
    <c:set var="page" value="${param.page == null ? 1 : param.page}"/>
    <tags:pagination page="${page}" entitiesPerPage="10" entitiesCount="${rooms.size()}"/>
    <c:forEach var="room" items="${rooms}" begin="0" end="9" step="1">
        <div class="d-flex my-2">
            <div class="row card bg-light ms-2">
                <h1><fmt:message key="rooms.roomNumber"/> : ${room.number}</h1>
                <h1><fmt:message key="rooms.roomName"/> : ${room.name}</h1>
                <h1><fmt:message key="rooms.className"/> : ${room.className}</h1>
                <h1><fmt:message key="rooms.roomStatus"/> : <fmt:message key="${'roomStatus.'.concat(room.status)}"/></h1>
                <h1><fmt:message key="rooms.roomCapacity"/> : ${room.capacity}</h1>
                <h1><fmt:message key="rooms.roomPrice"/> : ${room.price}</h1>
                <form method="post" action="<c:url value="/project/admin/room-request/assign"/>">
                    <input name="roomId" type="hidden" value=${room.id}>
                    <input name="requestId" type="hidden" value=${roomRequest.id}>
                    <button class="btn btn-outline-success" type="submit"><fmt:message key="adminRoomRequest.assignThisRoom"/></button>
                </form>
            </div>
        </div>
    </c:forEach>
</c:if>
<tags:pagination page="${page}" entitiesPerPage="10" entitiesCount="${rooms.size()}"/>
</body>
</html>
