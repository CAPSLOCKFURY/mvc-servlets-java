<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="content_lang" value="${cookie.get(\"Content-Language\") == null ? \"en\" : cookie.get(\"Content-Language\").getValue()}"/>
<fmt:setLocale value="${content_lang}"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <title><fmt:message key="title.userRoomRequests"/></title>
</head>
<body>
<jsp:include page="header.jsp"/>
<h1><fmt:message key="title.userRoomRequests"/></h1>
<c:forEach var="message" items="${messages}">
    <h2 style="color:red">${message}</h2>
</c:forEach>
<c:set var="page" value="${param.page == null ? 1 : param.page}"/>
<tags:pagination page="${page}" entitiesPerPage="10" entitiesCount="${roomRequests.size()}"/>
<c:forEach var="roomRequest" items="${roomRequests}" begin="0" end="9" step="1">
    <hr>
    <h1><fmt:message key="roomRequests.capacity"/> : ${roomRequest.capacity}</h1>
    <h1><fmt:message key="roomRequests.roomClass"/> : ${roomRequest.roomClass}</h1>
    <h1><fmt:message key="roomRequests.checkInDate"/> : ${roomRequest.checkInDate}</h1>
    <h1><fmt:message key="roomRequests.checkOutDate"/> : ${roomRequest.checkOutDate}</h1>
    <c:if test="${roomRequest.comment != null}">
        <h1><fmt:message key="roomRequests.yourComment"/> : ${roomRequest.comment}</h1>
    </c:if>
    <c:if test="${roomRequest.managerComment != null}">
        <h1><fmt:message key="roomRequests.managerComment"/> : ${roomRequest.managerComment}</h1>
    </c:if>
    <h1><fmt:message key="roomRequests.status"/> : <fmt:message key="${'roomRequestStatus.'.concat(roomRequest.status.replace(' ', '_'))}"/></h1>
    <c:if test="${roomRequest.roomId != 0}">
        <h1><fmt:message key="roomRequests.assignedRoom"/> : <a href="<c:url value="/project/room"><c:param name="id" value="${roomRequest.roomId}"/></c:url> ">
${roomRequest.roomId}</a></h1>
        <c:if test="${roomRequest.status.equals('awaiting confirmation')}">
            <form method="post" action="<c:url value="/project/profile/my-room-requests/confirm"/>">
                <input type="hidden" name="requestId" value="${roomRequest.id}">
                <button type="submit"><fmt:message key="userRoomRequests.confirmRequest"/></button>
            </form>
            <form method="post" action="<c:url value="/project/profile/my-room-requests/decline"/>">
                <textarea name="comment">${roomRequest.comment}</textarea>
                <input type="hidden" name="requestId" value="${roomRequest.id}">
                <button type="submit"><fmt:message key="userRoomRequests.declineRoom"/></button>
            </form>
        </c:if>
    </c:if>
    <c:if test="${roomRequest.status.equals('awaiting')}">
        <a href="<c:url value="/project/profile/my-room-requests/disable"><c:param name="id" value="${roomRequest.id}"/></c:url>">
            <fmt:message key="userRoomRequests.disable"/>
        </a>
    </c:if>
    <hr>
</c:forEach>
<tags:pagination page="${page}" entitiesPerPage="10" entitiesCount="${roomRequests.size()}"/>
</body>
</html>
