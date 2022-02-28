<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="/WEB-INF/tlds/mylib" prefix="t" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<t:mytag message="Test message attribute"/>
<h1><%= "Hello World!" %>
</h1>
<br/>
<a href=${pageContext.request.contextPath}/project/register>Register</a>
</body>
</html>