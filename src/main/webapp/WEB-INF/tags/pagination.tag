<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="url" uri="/WEB-INF/tlds/urllib" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ tag description="Pagination" pageEncoding="UTF-8"%>
<c:set var="content_lang" value="${cookie.get(\"Content-Language\") == null ? \"en\" : cookie.get(\"Content-Language\").getValue()}"/>
<fmt:setLocale value="${content_lang}"/>
<fmt:setBundle basename="pagecontent"/>
<%@ attribute name="page" required="true" type="java.lang.Integer" %>
<%@ attribute name="entitiesPerPage" type="java.lang.Integer" required="true" %>
<%@ attribute name="entitiesCount" type="java.lang.Integer" required="true" %>
<c:if test="${getParams == null}">
    <c:set var="getParams">
        <url:joinGetParameters insertQuestionMark="false" ignoreParams="page"/>
    </c:set>
</c:if>
<c:if test="${page > 1}">
    <a class="ms-2" style="display: inline" href="?page=${page - 1}${getParams}"><fmt:message key="prevPage"/></a>
</c:if>
<c:if test="${page > 1 || entitiesCount > entitiesPerPage}">
<h4 class="ms-2" style="display: inline">${page}</h4>
</c:if>
<c:if test="${entitiesCount > entitiesPerPage}">
    <a class="ms-2" style="display: inline" href="?page=${page + 1}${getParams}"><fmt:message key="nextPage"/></a>
</c:if>
