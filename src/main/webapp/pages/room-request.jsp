<%@ taglib prefix="form" uri="/WEB-INF/tlds/formlib" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<h1>Room request</h1>
<form method="post">
    <form:renderForm formClassPath="forms.RoomRequestForm"/>
    <button type="submit">Make request</button>
</form>
<c:forEach var="error" items="${errors}">
    <h2 style="color:red">${error}</h2>
</c:forEach>
<script>
    checkInDate.min = new Date().toISOString().split("T")[0];
</script>
</body>
</html>
