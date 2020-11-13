<%--
  Created by IntelliJ IDEA.
  User: Влад
  Date: 29.09.2020
  Time: 12:42
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
    <link rel="stylesheet" href="css/user_admissions.css">


</head>
<body>
<%@include file="jspf/navbar.jspf" %>

<c:if test="${empty requestScope.get(\"results\")}">
    <h3 class="text-center text-info"><fmt:message key="marks.have.no.marks"/></h3>
</c:if>
<c:if test="${not empty requestScope.get(\"results\")}">


    <h3 class="text-center text-info"><fmt:message key="home.your.marks"/></h3>

    <div class="container-fluid">
        <div class="row custyle">
            <table class="table table-striped custab">
                <c:forEach items="${requestScope.get(\"results\")}" var="result">
                    <tr>
                        <td>${result.subjectExam.name}</td>
                        <td>${result.result}</td>
                        <td>${result.dateOfExam}</td>
                        <td class="text-center">
                            <form method="post" action="/app/mark_del?subjectid=${result.subjectExam.id}">
                                <button type="submit" name="faculty_name" value="${admission.key}"
                                        class="btn btn-info btn-xs"><fmt:message key="marks.button.delete"/></button>
                            </form>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</c:if>


<h3 class="text-left text-info"><fmt:message key="adding.mark"/></h3>

<form class="form-inline" action="/app/add_mark" method="post" name="add_mark_form">


    <div class="form-group mb-2">
        <select class="form-control" name="marksSelect">
            <c:forEach items="${requestScope.get(\"exams\")}" var="exam">
                <option value="${exam.id}">${exam.name}</option>
            </c:forEach>
        </select>
    </div>
    <div class="form-group mx-sm-3 mb-2">
        <input type="number" placeholder="<fmt:message key="marks.enter.your.mark"/>" name="mark" required="required"
               pattern="^[0-9]{3}"></p>
    </div>
    <button type="submit" class="btn btn-info btn-xs"><fmt:message key="marks.button.send"/></button>
</form>


</body>
</html>
