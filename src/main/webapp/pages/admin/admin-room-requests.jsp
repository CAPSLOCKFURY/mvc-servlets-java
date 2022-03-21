<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="content_lang" value="${cookie.get(\"Content-Language\") == null ? \"en\" : cookie.get(\"Content-Language\").getValue()}"/>
<fmt:setLocale value="${content_lang}"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <title><fmt:message key="title.adminRoomRequests"/></title>
</head>
<body>
    <jsp:include page="/pages/header.jsp"/>
    <h1><fmt:message key="title.adminRoomRequests"/></h1>
    <form method="get" class="form-group">
        <div class="d-flex justify-content-start">
            <select class="form-select ms-2 me-1" style="width: 15%" name="requestStatus">
                <option value="AWAITING" <tags:selectedif test="${param.requestStatus.equals('AWAITING')}"/>><fmt:message key="roomRequests.filter.awaiting"/></option>
                <option value="AWAITING_CONFIRMATION" <tags:selectedif test="${param.requestStatus.equals('AWAITING_CONFIRMATION')}"/>><fmt:message key="roomRequests.filter.awaitingConfirmation"/></option>
                <option value="AWAITING_PAYMENT" <tags:selectedif test="${param.requestStatus.equals('AWAITING_PAYMENT')}"/>><fmt:message key="roomRequests.filter.awaitingPayment"/></option>
                <option value="PAID" <tags:selectedif test="${param.requestStatus.equals('PAID')}"/>><fmt:message key="roomRequests.filter.paid"/></option>
                <option value="CLOSED" <tags:selectedif test="${param.requestStatus.equals('CLOSED')}"/>><fmt:message key="roomRequests.filter.closed"/></option>
            </select>
            <select class="form-select mx-1" style="width: 10%" name="requestOrdering">
                <option value="ID" <tags:selectedif test="${param.requestOrdering.equals('ID')}"/>><fmt:message key="roomRequests.order.id"/></option>
                <option value="CHECK_IN_DATE" <tags:selectedif test="${param.requestOrdering.equals('CHECK_IN_DATE')}"/>><fmt:message key="roomRequests.order.checkInDate"/></option>
                <option value="CHECK_OUT_DATE" <tags:selectedif test="${param.requestOrdering.equals('CHECK_OUT_DATE')}"/>><fmt:message key="roomRequests.order.checkOutDate"/></option>
                <option value="CAPACITY" <tags:selectedif test="${param.requestOrdering.equals('CAPACITY')}"/>><fmt:message key="roomRequests.order.capacity"/></option>
            </select>
            <select class="form-select mx-1" style="width: 10%" name="orderDirection">
                <option value="ASC" <tags:selectedif test="${param.orderDirection.equals('ASC')}"/>><fmt:message key="asc"/></option>
                <option value="DESC" <tags:selectedif test="${param.orderDirection.equals('DESC')}"/>><fmt:message key="desc"/></option>
            </select>
            <button class="btn btn-outline-primary mx-1" type="submit"><fmt:message key="sortResults"/></button>
        </div>
    </form>
    <c:set var="page" value="${param.page == null ? 1 : param.page}"/>
    <tags:pagination page="${page}" entitiesPerPage="10" entitiesCount="${roomRequests.size()}"/>
    <c:forEach var="roomRequest" items="${roomRequests}" begin="0" end="9" step="1">
        <div class="d-flex">
            <div class="row my-2 ms-2 card bg-light">
                <h1><fmt:message key="roomRequests.capacity"/> : ${roomRequest.capacity}</h1>
                <h1><fmt:message key="roomRequests.roomClass"/> : ${roomRequest.className}</h1>
                <h1><fmt:message key="roomRequests.admin.comment"/> : ${roomRequest.comment}</h1>
                <h1><fmt:message key="roomRequests.managerComment"/> : ${roomRequest.managerComment}</h1>
                <h1><fmt:message key="roomRequests.status"/> : <fmt:message key="${'roomRequestStatus.'.concat(roomRequest.status.replace(' ', '_'))}"/></h1>
                <h1><fmt:message key="roomRequests.checkInDate"/> : ${roomRequest.checkInDate}</h1>
                <h1><fmt:message key="roomRequests.checkOutDate"/> : ${roomRequest.checkOutDate}</h1>
                <h1><fmt:message key="roomRequests.admin.userLogin"/> : ${roomRequest.login}</h1>
                <h1><fmt:message key="roomRequests.admin.email"/> : ${roomRequest.email}</h1>
                <h1><fmt:message key="roomRequests.admin.firstName"/> : ${roomRequest.firstName}</h1>
                <h1><fmt:message key="roomRequests.admin.lastName"/> : ${roomRequest.lastName}</h1>
                <c:if test="${roomRequest.roomId != 0}">
                    <h1><fmt:message key="roomRequests.assignedRoom"/> : <a target="_blank" href="<c:url value="/project/room"><c:param name="id" value="${roomRequest.roomId}"/></c:url>">
                            ${roomRequest.roomId}</a></h1>
                </c:if>
                <h2><a href="<c:url value="/project/admin/room-request"><c:param name="id" value="${roomRequest.id}"/></c:url> ">
                    <fmt:message key="adminRoomRequests.seeRequest"/>
                </a></h2>
            </div>
        </div>
    </c:forEach>
    <tags:pagination page="${page}" entitiesPerPage="10" entitiesCount="${roomRequests.size()}"/>
</body>
</html>
