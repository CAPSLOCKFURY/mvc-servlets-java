<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="content_lang" value="${cookie.get(\"Content-Language\") == null ? \"en\" : cookie.get(\"Content-Language\").getValue()}"/>
<fmt:setLocale value="${content_lang}"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <title><fmt:message key="title.userBillings"/></title>
</head>
<body>
<jsp:include page="header.jsp"/>
<h1 class="ms-2"><fmt:message key="title.userBillings"/></h1>
<div class="ms-2">
<c:forEach var="message" items="${messages}">
    <h2 class="text-danger">${message}</h2>
</c:forEach>
</div>
<c:set var="page" value="${param.page == null ? 1 : param.page}"/>
<tags:pagination page="${page}" entitiesPerPage="10" entitiesCount="${billings.size()}"/>
<c:forEach var="billing" items="${billings}" begin="0" end="9" step="1">
    <div class="d-flex">
        <div class="row card bg-light my-2 ms-2">
            <h1><fmt:message key="billings.price"/> : ${billing.price}</h1>
            <h1><fmt:message key="billings.payEndDate"/> : ${billing.payEndDate}</h1>
            <h1><fmt:message key="billings.paid"/> : <c:if test="${billing.paid == true}"><fmt:message key="true"/></c:if><c:if test="${billing.paid == false}"><fmt:message key="false"/></c:if></h1>
            <c:if test="${!billing.paid}">
                <form method="post" action="<c:url value="/project/profile/my-billings/pay"/>">
                    <input type="hidden" name="billingId" value="${billing.id}">
                    <button class="btn btn-success" type="submit"><fmt:message key="userBillings.payBilling"/></button>
                </form>
            </c:if>
        </div>
    </div>
</c:forEach>
<tags:pagination page="${page}" entitiesPerPage="10" entitiesCount="${billings.size()}"/>
</body>
</html>
