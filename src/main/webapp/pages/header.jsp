<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="/WEB-INF/tlds/mylib" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="en_US"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<body>
<a href="<c:url value="/project"/>"><fmt:message key="header.home"/></a>
<a href="<c:url value="/project/register"/>"><fmt:message key="header.register"/></a>
<a href="<c:url value="/project/login"/>"><fmt:message key="header.login"/></a>
</body>
</html>
