<%@ taglib prefix="form" uri="/WEB-INF/tlds/formlib" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="content_lang" value="${cookie.get(\"Content-Language\") == null ? \"en\" : cookie.get(\"Content-Language\").getValue()}"/>
<fmt:setLocale value="${content_lang}"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <title><fmt:message key="title.configureReport"/></title>
</head>
<body>
    <jsp:include page="/pages/header.jsp"/>
    <h1><fmt:message key="title.configureReport"/></h1>
    <form method="post" action="<c:url value="/project/admin/report/pdf"/>">
        <form:renderForm formClassPath="forms.ReportConfigurationForm"/>
        <button type="submit"><fmt:message key="configureReport.generateReport"/></button>
    </form>
</body>
</html>
