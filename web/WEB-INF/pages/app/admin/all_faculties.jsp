<%--
  User: Влад
  Date: 05.10.2020
  Time: 17:45
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


<div id="login">
    <div class="container">
        <div id="login-row" class="row justify-content-center align-items-center">
            <div id="login-column" class="col-md-6">
                <div id="login-box" class="col-md-12">
                    <form id="login-form" class="form" action="/app/admin/add_faculty" method="post"
                          accept-charset="UTF-8">
                        <h3 class="text-center text-info"><fmt:message key="faculty.add"/></h3>
                        <div class="form-group">
                            <label for="name" class="text-info"><fmt:message key="home.faculty.name"/></label><br>
                            <input id="name" name="name" type="text" required="required" class="form-control"
                                   pattern="^[A-Za-z0-9]+$">
                        </div>
                        <div class="form-group">
                            <label for="description" class="text-info"><fmt:message
                                    key="faculty.description"/></label><br>
                            <input id="description" type="text" name="description" required="required"
                                   class="form-control" pattern="^[A-Za-z0-9]+$">
                        </div>
                        <div class="form-group">
                            <label for="name_ua" class="text-info"><fmt:message
                                    key="home.faculty.name"/>(UA)</label><br>
                            <input id="name_ua" type="text" name="name_ua" required="required"
                                   pattern="^[А-Яа-яҐЄІЇіїєґ0-9]+$" class="form-control">
                        </div>
                        <div class="form-group">
                            <label for="description_ua" class="text-info"><fmt:message
                                    key="faculty.description"/>(UA)</label><br>
                            <input id="description_ua" type="text" name="description_ua" required="required"
                                   pattern="^[А-Яа-яҐЄІЇіїєґ0-9]+$" class="form-control">
                        </div>
                        <div class="form-group">
                            <label for="budget_amount" class="text-info"><fmt:message
                                    key="faculty.budget.amount"/></label><br>
                            <input id="budget_amount" type="number" name="budget_amount" required="required"
                                   pattern="[0-9]{3}" class="form-control">
                        </div>
                        <div class="form-group">
                            <label for="total_amount" class="text-info"><fmt:message
                                    key="faculty.total.amount"/></label><br>
                            <input id="total_amount" type="number" name="total_amount" required="required"
                                   pattern="[0-9]{3}" class="form-control">
                        </div>

                        <div class="form-group">
                            <input type="submit" name="submit" class="btn btn-info btn-md"
                                   value="<fmt:message key="button.send" />">
                        </div>
                        <br>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>


<c:if test="${not empty requestScope.get(\"facultiesList\")}">
    <h3 class="text-center text-info"><fmt:message key="faculties.list"/></h3>
    <div class="container-fluid">
        <div class="row custyle">
            <table class="table table-striped custab">
                <thead>
                <tr>
                    <th><fmt:message key="home.faculty.name"/></th>
                    <th><fmt:message key="faculty.description"/></th>
                    <th><fmt:message key="budget.places"/></th>
                    <th><fmt:message key="total.places"/></th>
                    <th class="text-center"><fmt:message key="home.button.delete"/></th>
                    <th class="text-center"><fmt:message key="button.change.admissions"/></th>
                </tr>
                </thead>
                <c:forEach var="faculty" items="${requestScope.get(\"facultiesList\")}">

                    <tr>
                        <td>${faculty.name}</td>
                        <td>${faculty.description}</td>
                        <td>${faculty.budgetAmount}</td>
                        <td>${faculty.totalAmount}</td>
                        <td class="text-center">
                            <form method="post" action="/app/admin/delete_faculty?id=${faculty.id}">
                                <button type="submit" class="btn btn-info btn-xs"><fmt:message
                                        key="home.button.delete"/></button>
                            </form>
                        <td class="text-center">
                            <form method="post" action="/app/admin/faculty_admissions?id=${faculty.id}">
                                <button type="submit" class="btn btn-info btn-xs"><fmt:message
                                        key="button.change.admissions"/></button>
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
            <a class="active" href="/app/admin/all_faculties?page=${numb}"><c:out value="${numb}"></c:out></a>
        </c:if>
        <c:if test="${numb != requestScope.get(\"pageFaculty\")}">
            <a href="/app/admin/all_faculties?page=${numb}"><c:out value="${numb}"></c:out></a>
        </c:if>
    </c:forEach>
</div>


</body>
</html>
