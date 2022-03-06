<%@ taglib prefix="form" uri="/WEB-INF/tlds/formlib" %>
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
</form>
</body>
</html>
