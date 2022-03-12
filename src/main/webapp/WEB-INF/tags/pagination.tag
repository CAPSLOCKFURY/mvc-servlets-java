<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="Pagination" pageEncoding="UTF-8"%>
<%@ attribute name="page" required="true" type="java.lang.Integer" %>
<%@ attribute name="entitiesPerPage" type="java.lang.Integer" required="true" %>
<%@ attribute name="entitiesCount" type="java.lang.Integer" required="true" %>
<c:if test="${page > 1}">
    <a href="?page=${page - 1}">Previous page</a>
</c:if>
<c:if test="${entitiesCount > entitiesPerPage}">
    <a href="?page=${page + 1}">Next Page</a>
</c:if>