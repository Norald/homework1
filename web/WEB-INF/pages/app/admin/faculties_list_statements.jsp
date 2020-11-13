<%--
  Created by IntelliJ IDEA.
  User: Влад
  Date: 08.10.2020
  Time: 13:36
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
    <link rel="stylesheet" href="/WEB-INF/pages/app/css/pagination.css">
    <%@include file="/WEB-INF/pages/app/jspf/scriptsBootstrap.jspf" %>
</head>
<body>
<%@include file="/WEB-INF/pages/app/jspf/navbar_admin.jspf" %>


<c:if test="${not empty requestScope.get(\"facultiesList\")}">
    <h3 class="text-center text-info"><fmt:message key="faculties.list"/></h3>
    <div class="container-fluid">
        <div class="row custyle">
            <table class="table table-striped custab">
                <thead>
                <tr>
                    <th><fmt:message key="home.faculty.name"/></th>
                    <th><fmt:message key="budget.places"/></th>
                    <th><fmt:message key="total.places"/></th>
                    <th class="text-center"><fmt:message key="marks.button.send"/></th>
                </tr>
                </thead>
                <c:forEach var="faculty" items="${requestScope.get(\"facultiesList\")}">

                    <tr>
                        <td>${faculty.name}</td>
                        <td>${faculty.budgetAmount}</td>
                        <td>${faculty.totalAmount}</td>
                        <td class="text-center">
                            <form method="post" action="/app/admin/generate_statement">
                                <input name="date" type="date" required="required"/>
                                <input name="id" type="number" hidden="hidden" value="${faculty.id}" checked="checked"/>
                                <button type="submit" class="btn btn-info btn-xs"><fmt:message
                                        key="button.send"/></button>
                            </form>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</c:if>


<br>


<div class="pagination">

    <c:forEach var="numb" begin="1" end="${requestScope.get(\"noOfPages\")}" step="1">
        <c:if test="${numb == requestScope.get(\"pageFaculty\")}">
            <a class="active" href="/app/admin/generation_statements?page=${numb}"><c:out value="${numb}"></c:out></a>
        </c:if>
        <c:if test="${numb != requestScope.get(\"pageFaculty\")}">
            <a href="/app/admin/generation_statements?page=${numb}"><c:out value="${numb}"></c:out></a>
        </c:if>
    </c:forEach>
</div>


</body>
</html>
