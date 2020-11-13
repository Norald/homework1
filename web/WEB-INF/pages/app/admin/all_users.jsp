<%--
  Created by IntelliJ IDEA.
  User: Влад
  Date: 06.10.2020
  Time: 18:32
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


<c:if test="${not empty requestScope.get(\"usersList\")}">
    <h3 class="text-center text-info"><fmt:message key="users.list"/></h3>
    <div class="container-fluid">
        <div class="row custyle">
            <table class="table table-striped custab">
                <thead>
                <tr>
                    <th><fmt:message key="users.email"/></th>
                    <th><fmt:message key="users.idn"/></th>
                    <th class="text-center">BLOCK</th>
                    <th class="text-center">CHANGE ROLE</th>
                </tr>
                </thead>


                <c:forEach items="${requestScope.get(\"usersList\")}" var="users">
                    <tr>
                        <td><c:out value="${users.email}"/></td>
                        <td><c:out value="${users.idn}"/></td>
                        <c:if test="${users.blocked == '0'}">
                            <td class="text-center">
                                <form method="post"
                                      action="/app/admin/block_user?id=${users.id}&operation=block">
                                    <button type="submit" class="btn btn-info btn-xs"><fmt:message
                                            key="button.block"/></button>
                                </form>
                            </td>
                        </c:if>
                        <c:if test="${users.blocked ne '0'}">
                            <td class="text-center">
                                <form method="post"
                                      action="<c:url value="/app/admin/block_user?id=${users.id}&operation=unblock"/>">
                                    <button type="submit" class="btn btn-info btn-xs"><fmt:message
                                            key="button.unblock"/></button>
                                </form>
                            </td>
                        </c:if>
                        <c:if test="${users.user_role_id == '2'}">
                            <td class="text-center">
                                <form method="post"
                                      action="<c:url value="/app/admin/user_set_role?id=${users.id}&operation=makeUser"/>">
                                    <button type="submit" class="btn btn-info btn-xs"><fmt:message
                                            key="button.set.user.role"/></button>
                                </form>
                            </td>
                        </c:if>
                        <c:if test="${users.user_role_id ne '2'}">
                            <td class="text-center">
                                <form method="post"
                                      action="<c:url value="/app/admin/user_set_role?id=${users.id}&operation=makeAdmin"/>">
                                    <button type="submit" class="btn btn-info btn-xs"><fmt:message
                                            key="button.set.admin.role"/></button>
                                </form>
                            </td>
                        </c:if>
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
        <c:if test="${numb == requestScope.get(\"pageFaculty\")}">
            <a class="active" href="/app/admin/users?page=${numb}"><c:out value="${numb}"></c:out></a>
        </c:if>
        <c:if test="${numb != requestScope.get(\"pageFaculty\")}">
            <a href="/app/admin/users?page=${numb}"><c:out value="${numb}"></c:out></a>
        </c:if>
    </c:forEach>
</div>

</body>
</html>
