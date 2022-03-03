<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<jsp:useBean scope="request" id="user" class="models.User"/>
<h1>User with id: ${user.id}</h1>
<h1>Email ${user.email}</h1>
</body>
</html>
