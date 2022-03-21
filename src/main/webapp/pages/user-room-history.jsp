<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="content_lang" value="${cookie.get(\"Content-Language\") == null ? \"en\" : cookie.get(\"Content-Language\").getValue()}"/>
<fmt:setLocale value="${content_lang}"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <title><fmt:message key="title.userRoomHistory"/></title>
</head>
<body>
<jsp:include page="header.jsp"/>
<h1 class="ms-2"><fmt:message key="title.userRoomHistory"/></h1>
<c:set var="page" value="${param.page == null ? 1 : param.page}"/>
<tags:pagination page="${page}" entitiesPerPage="10" entitiesCount="${rooms.size()}"/>
    <c:forEach items="${rooms}" var="room" begin="0" end="9" step="1">
        <div class="d-flex">
            <div class="row card bg-light ms-2 my-2">
                <h1><fmt:message key="rooms.roomNumber"/> : ${room.number}</h1>
                <h1><fmt:message key="rooms.roomName"/> : ${room.name}</h1>
                <h1><fmt:message key="rooms.roomPrice"/> : ${room.price}</h1>
                <h1><fmt:message key="rooms.roomCapacity"/> : ${room.capacity}</h1>
                <h1><fmt:message key="rooms.className"/> : ${room.className}</h1>
                <h1><fmt:message key="rooms.checkInDate"/> : ${room.checkInDate}</h1>
                <h1><fmt:message key="rooms.checkOutDate"/> : ${room.checkOutDate}</h1>
                <h2><a href="<c:url value="/project/room">
                            <c:param name="id" value="${room.id}"/>
                        </c:url>"><fmt:message key="rooms.seeRoom"/></a></h2>
            </div>
        </div>
    </c:forEach>
<tags:pagination page="${page}" entitiesPerPage="10" entitiesCount="${rooms.size()}"/>
</body>
</html>
