<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<h1>My billings</h1>
    <c:forEach var="billing" items="${billings}">
        <hr>
        <h1>Price: ${billing.price}</h1>
        <h1>Pay end date: ${billing.payEndDate}</h1>
        <hr>
    </c:forEach>
</body>
</html>
