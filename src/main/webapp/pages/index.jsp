<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="content_lang" value="${cookie.get(\"Content-Language\") == null ? \"en\" : cookie.get(\"Content-Language\").getValue()}"/>
<fmt:setLocale value="${content_lang}"/>
<fmt:setBundle basename="pagecontent"/>
<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="title.index"/></title>
</head>
<body>
<jsp:include page="header.jsp"/>
<h1><fmt:message key="index.allOutRooms"/></h1>
<form method="get">
    <select name="orderColName">
        <option value="ID" <tags:selectedif test="${param.orderColName.equals('ID')}"/>><fmt:message key="rooms.order.id"/></option>
        <option value="PRICE" <tags:selectedif test="${param.orderColName.equals('PRICE')}"/>><fmt:message key="rooms.order.price"/></option>
        <option value="STATUS" <tags:selectedif test="${param.orderColName.equals('STATUS')}"/>><fmt:message key="rooms.order.status"/></option>
        <option value="CLASS" <tags:selectedif test="${param.orderColName.equals('CLASS')}"/>><fmt:message key="rooms.order.class"/></option>
        <option value="CAPACITY" <tags:selectedif test="${param.orderColName.equals('CAPACITY')}"/>><fmt:message key="rooms.order.capacity"/></option>
    </select>
    <select name="orderDirection">
        <option value="ASC" <tags:selectedif test="${param.orderDirection.equals('ASC')}"/>><fmt:message key="asc"/></option>
        <option value="DESC" <tags:selectedif test="${param.orderDirection.equals('DESC')}"/>><fmt:message key="desc"/></option>
    </select>
    <button type="submit"><fmt:message key="sortResults"/></button>
</form>
<c:set var="page" value="${param.page == null ? 1 : param.page}"/>
<tags:pagination page="${page}" entitiesPerPage="10" entitiesCount="${rooms.size()}"/>
<c:forEach var="room" items="${rooms}" begin="0" end="9" step="1">
    <hr>
    <h1>-------------<fmt:message key="rooms.roomInfo"/>----------------</h1>
    <h1><fmt:message key="rooms.roomNumber"/>: ${room.number}</h1>
    <h1><fmt:message key="rooms.roomName"/>: ${room.name}</h1>
    <h1><fmt:message key="rooms.className"/>: ${room.className}</h1>
    <h1><fmt:message key="rooms.roomStatus"/> : <fmt:message key="${'roomStatus.'.concat(room.status)}"/></h1>
    <h1><fmt:message key="rooms.roomCapacity"/> : ${room.capacity}</h1>
    <h1><fmt:message key="rooms.roomPrice"/> : ${room.price}</h1>
    <h2><a href="<c:url value="/project/room">
                    <c:param name="id" value="${room.id}"/>
                 </c:url>"><fmt:message key="rooms.seeRoom"/></a></h2>
    <hr>
</c:forEach>
<tags:pagination page="${page}" entitiesPerPage="10" entitiesCount="${rooms.size()}"/>
</body>
</html>