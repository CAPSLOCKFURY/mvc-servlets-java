<%@ taglib prefix="form" uri="/WEB-INF/tlds/formlib" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <jsp:include page="/pages/header.jsp"/>
    <form method="post" action="<c:url value="/project/admin/report/pdf"/>">
        <form:renderForm formClassPath="forms.ReportConfigurationForm"/>
        <button type="submit">Generate Report</button>
    </form>
</body>
</html>
