<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <jsp:include page="/pages/header.jsp"/>
    <h1>Admin room requests</h1>
    <form method="get">
        <select name="requestStatus">
            <option value="AWAITING" <tags:selectedif test="${param.requestStatus.equals('AWAITING')}"/>>awaiting</option>
            <option value="AWAITING_CONFIRMATION" <tags:selectedif test="${param.requestStatus.equals('AWAITING_CONFIRMATION')}"/>>awaiting confirmation</option>
            <option value="AWAITING_PAYMENT" <tags:selectedif test="${param.requestStatus.equals('AWAITING_PAYMENT')}"/>>awaiting payment</option>
            <option value="PAID" <tags:selectedif test="${param.requestStatus.equals('PAID')}"/>>paid</option>
            <option value="CLOSED" <tags:selectedif test="${param.requestStatus.equals('CLOSED')}"/>>closed</option>
        </select>
        <select name="requestOrdering">
            <option value="ID" <tags:selectedif test="${param.requestOrdering.equals('ID')}"/>>id</option>
            <option value="CHECK_IN_DATE" <tags:selectedif test="${param.requestOrdering.equals('CHECK_IN_DATE')}"/>>check in date</option>
            <option value="CHECK_OUT_DATE" <tags:selectedif test="${param.requestOrdering.equals('CHECK_OUT_DATE')}"/>>check out date</option>
            <option value="CAPACITY" <tags:selectedif test="${param.requestOrdering.equals('CAPACITY')}"/>>capacity</option>
        </select>
        <select name="orderDirection">
            <option value="ASC" <tags:selectedif test="${param.orderDirection.equals('ASC')}"/>>ascending</option>
            <option value="DESC" <tags:selectedif test="${param.orderDirection.equals('DESC')}"/>>descending</option>
        </select>
        <button type="submit">filter requests</button>
    </form>
    <c:set var="page" value="${param.page == null ? 1 : param.page}"/>
    <tags:pagination page="${page}" entitiesPerPage="10" entitiesCount="${roomRequests.size()}"/>
    <c:forEach var="roomRequest" items="${roomRequests}" begin="0" end="9" step="1">
        <hr>
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
        <h2><a href="<c:url value="/project/admin/room-request"><c:param name="id" value="${roomRequest.id}"/></c:url> ">See request</a></h2>
        <hr>
    </c:forEach>
    <tags:pagination page="${page}" entitiesPerPage="10" entitiesCount="${roomRequests.size()}"/>
</body>
</html>
