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
<jsp:useBean id="user" scope="request" class="models.User"/>
<h1><fmt:message key="profile.welcome"/> : ${user.firstName} ${user.lastName}</h1>
<h2><fmt:message key="user.email"/>: ${user.email}</h2>
<h2><fmt:message key="profile.yourBalance"/> : ${user.balance}</h2>
<a href="<c:url value="/project/profile/update"/>"><fmt:message key="profile.updateProfile"/></a>
<a href="<c:url value="/project/profile/my-room-requests"/>"><fmt:message key="profile.myRoomRequests"/></a>
<a href="<c:url value="/project/profile/my-billings"/>"><fmt:message key="profile.myBillings"/></a>
<a href="<c:url value="/project/profile/room-history"/>"><fmt:message key="profile.myRoomHistory"/></a>
<a href="<c:url value="/project/profile/balance"/>"><fmt:message key="profile.addBalance"/></a>
<c:if test="${sessionScope.user.role == 2}">
    <a href="<c:url value="/project/admin/room-requests"/>"><fmt:message key="profile.admin.usersRoomRequest"/></a>
    <a href="<c:url value="/project/admin/report"/>"><fmt:message key="profile.admin.generatePdfReport"/></a>
</c:if>
<a href="<c:url value="/project/profile/change-password"/>"><fmt:message key="profile.changePassword"/></a>
<a href="<c:url value="/project/logout"/>"><fmt:message key="profile.logout"/></a>
</body>
</html>
