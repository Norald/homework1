<%@ page import="model.User" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Date" %><%--
  Created by IntelliJ IDEA.
  User: Влад
  Date: 26.09.2020
  Time: 14:28
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


<div class="jumbotron jumbotron-fluid">
    <div class="container">
        <h1 class="display-4"><fmt:message key="home.welcome"/></h1>
        <p class="lead"><fmt:message key="home.welcome.text"/></p>
    </div>
</div>

<%@include file="../changeLanguage.jsp" %>


</body>
</html>
