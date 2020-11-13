<%--
  Created by IntelliJ IDEA.
  User: Влад
  Date: 08.10.2020
  Time: 15:36
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


<c:if test="${empty sessionScope.get(\"results\")}">
    <h3 class="text-center text-info"><fmt:message key="have.no.admissions"/></h3>
</c:if>
<c:if test="${not empty sessionScope.get(\"results\")}">
    <h3 class="text-center text-info"><fmt:message key="list.subject.exam"/></h3>
    <div class="container-fluid">
        <div class="row custyle">
            <table class="table table-striped custab">
                <thead>
                <tr>
                    <th><fmt:message key="user.name"/></th>
                    <th><fmt:message key="users.idn"/></th>
                    <th><fmt:message key="total.exam.result"/></th>
                    <th><fmt:message key="certificate.point"/></th>
                    <th><fmt:message key="total.result"/></th>
                    <th class="text-center">Action</th>
                </tr>
                </thead>


                <c:forEach items="${sessionScope.get(\"results\")}" var="result">
                    <tr>
                        <td>${result.fullname} </td>
                        <td>${result.idn} </td>
                        <td>${result.totalExamResult} </td>
                        <td>${result.certificatePoint} </td>
                        <td>${result.totalResult}</td>
                        <c:if test="${result.isApproved eq 'false'}">
                            <td class="text-center">
                                <form method="post"
                                      action="/app/admin/confirm_user_for_statement?idAdmission=${result.id}&operation=approve">
                                    <button class="btn btn-info btn-xs" type="submit"><fmt:message
                                            key="statement.report.user"/></button>
                                </form>
                            </td>
                        </c:if>
                        <c:if test="${result.is_approved eq 'true'}">
                            <td class="text-center">
                                <form method="post"
                                      action="/app/admin/confirm_user_for_statement?idAdmission=${result.id}&operation=disapprove">
                                    <button class="btn btn-info btn-xs" type="submit"><fmt:message
                                            key="statement.unreport.user"/></button>
                                </form>
                            </td>
                        </c:if>

                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>

    <br>
    <td class="text-center">
        <form method="get" action="/app/admin/generate_early_statement">
            <button class="btn btn-info btn-xs" type="submit"><fmt:message key="generate.statement"/></button>
        </form>
    </td>
    <br>
    <td class="text-center">
        <form method="get" action="/app/admin/generate_late_statement">
            <button class="btn btn-info btn-xs" type="submit"><fmt:message key="generate.statement"/> (Final)</button>
        </form>
    </td>

</c:if>
</body>
</html>
