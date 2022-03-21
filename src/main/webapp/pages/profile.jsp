<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="content_lang" value="${cookie.get(\"Content-Language\") == null ? \"en\" : cookie.get(\"Content-Language\").getValue()}"/>
<fmt:setLocale value="${content_lang}"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <jsp:include page="header.jsp"/>
    <title><fmt:message key="title.profile"/></title>
</head>
<body>
<div class="ms-2">
    <jsp:useBean id="user" scope="request" class="models.User"/>
    <h1 class="my-1"><fmt:message key="profile.welcome"/> : ${user.firstName} ${user.lastName}</h1>
    <h2 class="my-1"><fmt:message key="user.email"/>: ${user.email}</h2>
    <h2 class="my-1"><fmt:message key="profile.yourBalance"/> : ${user.balance}</h2>
</div>
    <div class="d-flex bg-light w-25">
        <div class="ms-2 row">
            <a class="link-primary my-1 text-decoration-none" href="<c:url value="/project/room-request"/>"><fmt:message key="header.requestRoom"/></a>
            <a class="link-primary my-1 text-decoration-none" href="<c:url value="/project/profile/my-room-requests"/>"><fmt:message key="profile.myRoomRequests"/></a>
            <a class="link-primary my-1 text-decoration-none" href="<c:url value="/project/profile/my-billings"/>"><fmt:message key="profile.myBillings"/></a>
            <a class="link-primary my-1 text-decoration-none" href="<c:url value="/project/profile/room-history"/>"><fmt:message key="profile.myRoomHistory"/></a>
            <a class="link-primary my-1 text-decoration-none" href="<c:url value="/project/profile/balance"/>"><fmt:message key="profile.addBalance"/></a>
            <c:if test="${sessionScope.user.role == 2}">
                <a class="link-primary my-1 text-decoration-none" href="<c:url value="/project/admin/room-requests"/>"><fmt:message key="profile.admin.usersRoomRequest"/></a>
                <a class="link-primary my-1 text-decoration-none" href="<c:url value="/project/admin/report"/>"><fmt:message key="profile.admin.generatePdfReport"/></a>
            </c:if>
            <a class="link-primary my-1 text-decoration-none" href="<c:url value="/project/profile/update"/>"><fmt:message key="profile.updateProfile"/></a>
            <a class="link-primary my-1 text-decoration-none" href="<c:url value="/project/profile/change-password"/>"><fmt:message key="profile.changePassword"/></a>
            <a class="link-danger my-1 text-decoration-none" href="<c:url value="/project/logout"/>"><fmt:message key="profile.logout"/></a>
        </div>
    </div>
</body>
</html>
