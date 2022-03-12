<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag description="Make option selected given if statement" pageEncoding="UTF-8"%>
<%@ attribute name="test" required="true" type="java.lang.Boolean" %>

<c:if test="${test}">
    selected
</c:if>