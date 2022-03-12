<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="url" uri="/WEB-INF/tlds/urllib" %>
<%@ tag description="Pagination" pageEncoding="UTF-8"%>
<%@ attribute name="page" required="true" type="java.lang.Integer" %>
<%@ attribute name="entitiesPerPage" type="java.lang.Integer" required="true" %>
<%@ attribute name="entitiesCount" type="java.lang.Integer" required="true" %>
<c:if test="${getParams != null}">
    <c:set var="getParams">
        <url:joinGetParameters insertQuestionMark="false" ignoreParams="page"/>
    </c:set>
</c:if>
<c:if test="${page > 1}">
    <a href="?page=${page - 1}${getParams}">Previous page</a>
</c:if>
<c:if test="${entitiesCount > entitiesPerPage}">
    <a href="?page=${page + 1}${getParams}">Next Page</a>
</c:if>