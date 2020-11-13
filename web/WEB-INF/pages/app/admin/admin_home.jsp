<%--
  Created by IntelliJ IDEA.
  User: Влад
  Date: 04.10.2020
  Time: 11:01
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
    <%@include file="/WEB-INF/pages/app/jspf/scriptsBootstrap.jspf" %>

</head>
<body>
<%@include file="/WEB-INF/pages/app/jspf/navbar_admin.jspf" %>

<h3 class="text-center text-info"><fmt:message key="home.admin"/></h3>


</body>
</html>
