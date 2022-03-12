<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<h1>My billings</h1>
<c:forEach var="message" items="${messages}">
    <h2 style="color:red">${message}</h2>
</c:forEach>
<c:set var="page" value="${param.page == null ? 1 : param.page}"/>
<tags:pagination page="${page}" entitiesPerPage="10" entitiesCount="${billings.size()}"/>
<c:forEach var="billing" items="${billings}" begin="0" end="9" step="1">
    <hr>
    <h1>Price: ${billing.price}</h1>
    <h1>Pay end date: ${billing.payEndDate}</h1>
    <h1>Paid: ${billing.paid}</h1>
    <c:if test="${!billing.paid}">
        <form method="post" action="<c:url value="/project/profile/my-billings/pay"/>">
            <input type="hidden" name="billingId" value="${billing.id}">
            <button type="submit">Pay billing</button>
        </form>
    </c:if>
    <hr>
</c:forEach>
<tags:pagination page="${page}" entitiesPerPage="10" entitiesCount="${billings.size()}"/>
</body>
</html>
