<%--
  Created by IntelliJ IDEA.
  User: Влад
  Date: 07.10.2020
  Time: 11:14
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


<div id="login">
    <div class="container">
        <div id="login-row" class="row justify-content-center align-items-center">
            <div id="login-column" class="col-md-6">
                <div id="login-box" class="col-md-12">
                    <form id="login-form" class="form" action="/app/admin/add_subject_exam" method="post"
                          accept-charset="UTF-8">
                        <h3 class="text-center text-info"><fmt:message key="add.subject.exam"/></h3>
                        <div class="form-group">
                            <label for="name" class="text-info"><fmt:message key="exam.name"/></label><br>
                            <input id="name" name="name" type="text" required="required" class="form-control"
                                   pattern="^[A-Za-z0-9]+$">
                        </div>
                        <div class="form-group">
                            <label for="description" class="text-info"><fmt:message key="exam.description"/></label><br>
                            <input id="description" type="text" name="description" required="required"
                                   class="form-control" pattern="^[A-Za-z0-9]+$">
                        </div>
                        <div class="form-group">
                            <label for="name_ua" class="text-info"><fmt:message key="exam.name"/>(UA)</label><br>
                            <input id="name_ua" type="text" name="name_ua" required="required"
                                   pattern="^[А-Яа-яҐЄІЇіїєґ0-9]+$" class="form-control">
                        </div>
                        <div class="form-group">
                            <label for="description_ua" class="text-info"><fmt:message
                                    key="exam.description"/>(UA)</label><br>
                            <input id="description_ua" type="text" name="description_ua" required="required"
                                   pattern="^[А-Яа-яҐЄІЇіїєґ0-9]+$" class="form-control">
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


<c:if test="${not empty requestScope.get(\"subjectExams\")}">
    <h3 class="text-center text-info"><fmt:message key="list.subject.exam"/></h3>
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


                <c:forEach items="${requestScope.get(\"subjectExams\")}" var="subjectExam">
                    <tr>
                        <td><c:out value="${subjectExam.name}"/></td>
                        <td><c:out value="${subjectExam.description}"/></td>
                        <td class="text-center">
                            <form method="post"
                                  action="/app/admin/delete_subject_exam?id=${subjectExam.id}">
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

<br>
<div class="pagination">

    <c:forEach var="numb" begin="1" end="${requestScope.get(\"noOfPages\")}" step="1">
        <c:if test="${numb == requestScope.get(\"pageSubjectExam\")}">
            <a class="active" href="/app/admin/subject_exams?page=${numb}"><c:out value="${numb}"></c:out></a>
        </c:if>
        <c:if test="${numb != requestScope.get(\"pageSubjectExam\")}">
            <a href="/app/admin/subject_exams?page=${numb}"><c:out value="${numb}"></c:out></a>
        </c:if>
    </c:forEach>
</div>

</body>
</html>
