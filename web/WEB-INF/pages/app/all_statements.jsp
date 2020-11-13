<%--
  Created by IntelliJ IDEA.
  User: Влад
  Date: 12.10.2020
  Time: 12:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<fmt:setLocale value="${sessionScope.get(\"language\")}"/>
<fmt:setBundle basename="resource"/>
<html>
<head>
    <%@include file="jspf/scriptsBootstrap.jspf" %>
</head>
<body>
<%@include file="jspf/navbar.jspf" %>

<c:forEach items="${files}" var="file">
    <br/>
    <a href="/app/download_statement?name=${file.name}"><strong> <c:out value="${file.name}"/></strong></a>
    <c:if test="${sessionScope.get(\"role\")== 'ADMIN'}">
        <form method="post" action="/app/admin/delete_statement?name=${file.name}">
            <button type="submit"><fmt:message key="home.button.delete"/></button>
        </form>
    </c:if>
</c:forEach>

</body>
</html>
