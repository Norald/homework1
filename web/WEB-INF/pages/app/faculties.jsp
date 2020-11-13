<%--
  Created by IntelliJ IDEA.
  User: Влад
  Date: 27.09.2020
  Time: 23:37
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
    <link rel="stylesheet" href="css/pagination.css" type="text/css" media="all">
    <link rel="stylesheet" href="css/product_list.css" type="text/css" media="all">

    <%@include file="jspf/scriptsBootstrap.jspf" %>

</head>
<body>
<%@include file="jspf/navbar.jspf" %>
<br>
<h3 class="text-center text-info"><fmt:message key="faculties.list"/></h3>
<br>
<div class="container-fluid">
    <div class="buttons">
        <div class="btn-group btn-group-sm" role="group" aria-label="Vertical example">
            <form action="/app/faculties" method="post" name="sort">
                <button type="submit" class="btn btn-info" value="sortAZ" name="sort"><fmt:message
                        key="faculties.sort.az"/></button>
                <button type="submit" class="btn btn-info" value="sortZA" name="sort"><fmt:message
                        key="faculties.sort.za"/></button>
                <button type="submit" class="btn btn-info" value="sortBudget" name="sort"><fmt:message
                        key="faculties.sort.budget"/></button>
                <button type="submit" class="btn btn-info" value="sortTotal" name="sort"><fmt:message
                        key="faculties.sort.total"/></button>
            </form>
        </div>
    </div>
</div>


<c:forEach items="${requestScope.get(\"facultiesList\")}" var="faculty">
    <div class="container-fluid">
        <div class="col-xs-12 full-width">
            <div class="prod-info-main prod-wrap clearfix">
                <div class="row">
                    <div class="col-md-5 col-sm-12 col-xs-12">
                        <div class="product-image">
                            <img src="https://srishtiias.in/wp-content/uploads/2018/08/faculty.jpg" alt="194x228"
                                 class="img-fluid">
                        </div>
                    </div>
                    <div class="col-md-7 col-sm-12 col-xs-12">
                        <div class="product-deatil">
                            <h5 class="name">
                                <a href="/app/faculty?id=${faculty.id}">
                                        ${faculty.name}
                                </a>
                            </h5>
                            <p class="price-container">
                                <span>${faculty.budgetAmount} - <fmt:message
                                        key="budget.places"/>, ${faculty.totalAmount} - <fmt:message
                                        key="total.places"/></span>
                            </p>
                            <span class="tag1"></span>
                        </div>
                        <div class="description">
                            <p>${faculty.description} </p>
                        </div>
                        <div class="product-info smart-form">
                            <div class="row">
                                <div class="col-md-12">
                                    <a href="/app/faculty?id=${faculty.id}" class="btn btn-info"><span><fmt:message
                                            key="faculty.button.send.request"/></span></a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    </div>
</c:forEach>
<br>

<div class="pagination">

    <c:forEach var="numb" begin="1" end="${requestScope.get(\"noOfPages\")}" step="1">
        <c:if test="${numb == requestScope.get(\"pageFaculty\")}">
            <a class="active" href="/app/faculties?page=${numb}"><c:out value="${numb}"></c:out></a>
        </c:if>
        <c:if test="${numb != requestScope.get(\"pageFaculty\")}">
            <a href="/app/faculties?page=${numb}"><c:out value="${numb}"></c:out></a>
        </c:if>
    </c:forEach>
</div>

</body>
</html>
