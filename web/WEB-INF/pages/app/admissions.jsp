<%--
  Created by IntelliJ IDEA.
  User: Влад
  Date: 02.10.2020
  Time: 20:33
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
    <link rel="stylesheet" href="css/user_admissions.css">

</head>
<body>
<%@include file="jspf/navbar.jspf" %>

<c:if test="${empty sessionScope.get(\"admissions map\")}">
    <h3 class="text-center text-info"><fmt:message key="home.have.no.admissions"/></h3>
</c:if>


<c:if test="${not empty sessionScope.get(\"admissions map\")}">
    <h3 class="text-center text-info"><fmt:message key="home.your.admissions"/></h3>
    <div class="container-fluid">
        <div class="row custyle">
            <table class="table table-striped custab">
                <thead>
                <tr>
                    <th><fmt:message key="home.faculty.name"/></th>
                    <th><fmt:message key="home.date.admission"/></th>
                    <th class="text-center"><fmt:message key="home.button.delete"/></th>
                </tr>
                </thead>
                <c:forEach var="admission" items="${sessionScope.get(\"admissions map\")}">

                    <tr>
                        <td><c:out value="${admission.key}"/></td>
                        <td><c:out value="${admission.value}"/></td>
                        <td class="text-center">
                            <form method="post" action="/app/admission_del">
                                <button type="submit" name="faculty_name" value="${admission.key}"
                                        class="btn btn-info btn-xs"><fmt:message key="home.button.delete"/></button>
                            </form>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</c:if>

</body>
</html>
