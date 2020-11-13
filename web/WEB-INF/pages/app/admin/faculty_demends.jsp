<%--
  Created by IntelliJ IDEA.
  User: Влад
  Date: 06.10.2020
  Time: 12:50
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
<body>
<%@include file="/WEB-INF/pages/app/jspf/navbar_admin.jspf" %>

<c:set var="faculty" value="${requestScope.get(\"faculty\")}"/>
<h2 class="text-center text-info">${faculty.name}</h2>

<br>
<h3 class="text-left text-info"><fmt:message key="add.demends.to.faculty"/></h3>

<form class="form-inline" action="/app/admin/faculty_demend" method="post" name="add_mark_form">

    <div class="form-group mb-2">
        <select class="form-control" name="demendSelect">
            <c:forEach items="${requestScope.get(\"examAvailableList\")}" var="examAv">
                <option value="${examAv.id}">${examAv.name}</option>
            </c:forEach>
        </select>
    </div>
    <div>
        <input type="hidden" name="idFaculty" value="${faculty.id}"/>
    </div>
    <button type="submit" class="btn btn-info btn-xs"><fmt:message key="marks.button.send"/></button>
</form>


<c:if test="${not empty requestScope.get(\"examList\")}">
    <h3 class="text-center text-info"><fmt:message key="demends.faculty"/></h3>
    <div class="container-fluid">
        <div class="row custyle">
            <table class="table table-striped custab">
                <thead>
                <tr>
                    <th><fmt:message key="exam.name"/></th>
                    <th><fmt:message key="faculty.description"/></th>
                    <th class="text-center"><fmt:message key="marks.button.delete"/></th>
                </tr>
                </thead>


                <c:forEach items="${requestScope.get(\"examList\")}" var="examList">
                    <tr>
                        <td><c:out value="${examList.name}"/></td>
                        <td><c:out value="${examList.description}"/></td>
                        <td class="text-center">
                            <form method="post"
                                  action="/app/admin/delete_demend?idFaculty=${faculty.id}&idExam=${examList.id}">
                                <button type="submit" class="btn btn-info btn-xs"><fmt:message
                                        key="home.button.delete"/></button>
                            </form>
                        </td>

                    </tr>
                    <br>
                </c:forEach>
            </table>
        </div>
    </div>
</c:if>


</body>
</html>
