<%@ page import="models.User" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<h1>All our rooms</h1>
<form method="get">
    <select name="orderColName">
        <option value="ID" <tags:selectedif test="${param.orderColName.equals('ID')}"/>>id</option>
        <option value="PRICE" <tags:selectedif test="${param.orderColName.equals('PRICE')}"/>>price</option>
        <option value="STATUS" <tags:selectedif test="${param.orderColName.equals('STATUS')}"/>>status</option>
        <option value="CLASS" <tags:selectedif test="${param.orderColName.equals('CLASS')}"/>>room class</option>
        <option value="CAPACITY" <tags:selectedif test="${param.orderColName.equals('CAPACITY')}"/>>capacity</option>
    </select>
    <select name="orderDirection">
        <option value="ASC" <tags:selectedif test="${param.orderDirection.equals('ASC')}"/>>Ascending</option>
        <option value="DESC" <tags:selectedif test="${param.orderDirection.equals('DESC')}"/>>Descending</option>
    </select>
    <button type="submit">Sort results</button>
</form>
<c:set var="page" value="${param.page == null ? 1 : param.page}"/>
<tags:pagination page="${page}" entitiesPerPage="10" entitiesCount="${rooms.size()}"/>
<c:forEach var="room" items="${rooms}" begin="0" end="9" step="1">
    <hr>
    <h1>-------------Room info----------------</h1>
    <h1>Room number: ${room.number}</h1>
    <h1>Room name: ${room.name}</h1>
    <h1>Room class: ${room.className}</h1>
    <h1>Room status: ${room.status}</h1>
    <h1>Room capacity: ${room.capacity}</h1>
    <h1>Room price: ${room.price}</h1>
    <h2><a href="<c:url value="/project/room">
                    <c:param name="id" value="${room.id}"/>
                 </c:url>">See room</a></h2>
    <hr>
</c:forEach>
<tags:pagination page="${page}" entitiesPerPage="10" entitiesCount="${rooms.size()}"/>
</body>
</html>