<%--
  Created by IntelliJ IDEA.
  User: Влад
  Date: 01.10.2020
  Time: 11:18
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>

<c:set var="language"
       value="${not empty sessionScope.get(\"language\") ? sessionScope.get(\"language\") : not empty sessionScope.get(\"language\") ? sessionScope.get(\"language\") : 'uk'}"
       scope="session"/>

<fmt:setLocale value="${sessionScope.get(\"language\")}"/>

<fmt:setBundle basename="resource"/>
<html>
<head>
    <%--<%@include file="/app/jspf/scriptsBootstrap.jspf" %>--%>
</head>
<body>


<div class="dropdown open">
    <button type="button" class="btn btn-info btn-md dropdown-toggle" data-toggle="dropdown" aria-haspopup="true"
            aria-expanded="false">
        <c:if test="${sessionScope.get(\"language\") == 'uk'}">
            <fmt:message key="language"/>
        </c:if>
        <c:if test="${sessionScope.get(\"language\") == 'en'}">
            <fmt:message key="language"/>
        </c:if>
    </button>
    <div class="dropdown-menu">

        <c:if test="${sessionScope.get(\"language\") == 'uk'}">
            <a class="dropdown-item" value="en" href="/changeLang?lang=en"><fmt:message key="language.en"/></a>
            <a class="dropdown-item" value="uk" href="/changeLang?lang=uk"><fmt:message key="language.uk"/></a>
        </c:if>
        <c:if test="${sessionScope.get(\"language\") == 'en'}">
            <a class="dropdown-item" value="uk" href="/changeLang?lang=uk"><fmt:message key="language.uk"/></a>
            <a class="dropdown-item" value="en" href="/changeLang?lang=en"><fmt:message key="language.en"/></a>
        </c:if>

    </div>
</div>
</body>
</html>
