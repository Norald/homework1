<%--
  Created by IntelliJ IDEA.
  User: Влад
  Date: 28.09.2020
  Time: 13:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.get(\"language\")}"/>
<fmt:setBundle basename="resource"/>

<html>
<head>
    <title>Title</title>
    <%@include file="jspf/scriptsBootstrap.jspf" %>
    <style>
        .hm-gradient {
            background-image: linear-gradient(to top, #f3e7e9 0%, #e3eeff 99%, #e3eeff 100%);
        }

        .darken-grey-text {
            color: #2E2E2E;
        }
    </style>

</head>
<body>
<%@include file="jspf/navbar.jspf" %>


<c:set var="faculty" value="${requestScope.get(\"faculty\")}"/>


<div class="jumbotron">

    <h1 class="h1-reponsive mb-3 blue-text"><strong>${faculty.name}</strong></h1>
    <p class="lead">${faculty.description}</p>
    <hr class="my-4">
    <p><fmt:message key="budget.places"/>: ${faculty.budgetAmount}
    </p>
    <p><fmt:message key="total.places"/>: ${faculty.totalAmount}
    </p>


    <c:choose>
        <c:when test="${requestScope.get(\"able to apply\")==false}">
            <h2 class="text-left text-info"><fmt:message key="faculty.cant.admit"/></h2>
            <br/>
        </c:when>
        <c:otherwise>
            <form method="post" action="/app/participate">
                <input type="hidden" name="faculty_id" value="${faculty.id}"/>
                <button type="submit" class="btn btn-info"><fmt:message
                        key="faculty.button.send.request"></fmt:message></button>
            </form>
            <br/>
        </c:otherwise>
    </c:choose>
</div>


</body>
</html>
