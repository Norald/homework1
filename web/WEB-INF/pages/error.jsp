<%--
  Created by IntelliJ IDEA.
  User: Влад
  Date: 26.09.2020
  Time: 14:49
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>

<c:set var="language"
       value="${not empty sessionScope.get(\"language\") ? sessionScope.get(\"language\") : not empty sessionScope.get(\"language\") ? sessionScope.get(\"language\") : 'uk'}"
       scope="session"/>
<fmt:setLocale value="${sessionScope.get(\"language\")}"/>
<fmt:setBundle basename="resource"/>
<html>
<head>
    <%@include file="app/jspf/scriptsBootstrap.jspf" %>
    <style>
        .clearfix:before,
        .clearfix:after {
            display: table;

            content: ' ';
        }

        .clearfix:after {
            clear: both;
        }

        body {
            background: #f0f0f0 !important;
        }

        .page-404 .outer {
            position: absolute;
            top: 0;

            display: table;

            width: 100%;
            height: 100%;
        }

        .page-404 .outer .middle {
            display: table-cell;

            vertical-align: middle;
        }

        .page-404 .outer .middle .inner {
            width: 300px;
            margin-right: auto;
            margin-left: auto;
        }

        .page-404 .outer .middle .inner .inner-circle {
            height: 300px;

            border-radius: 50%;
            background-color: #ffffff;
        }

        .page-404 .outer .middle .inner .inner-circle:hover i {
            color: #17a2b8 !important;
            background-color: #f5f5f5;
            box-shadow: 0 0 0 15px #39bbdb;
        }

        .page-404 .outer .middle .inner .inner-circle:hover span {
            color: #17a2b8;
        }

        .page-404 .outer .middle .inner .inner-circle i {
            font-size: 5em;
            line-height: 1em;

            float: right;

            width: 1.6em;
            height: 1.6em;
            margin-top: -.7em;
            margin-right: -.5em;
            padding: 20px;

            -webkit-transition: all .4s;
            transition: all .4s;
            text-align: center;

            color: #f5f5f5 !important;
            border-radius: 50%;
            background-color: #17a2b8;
            box-shadow: 0 0 0 15px #f0f0f0;
        }

        .page-404 .outer .middle .inner .inner-circle span {
            font-size: 11em;
            font-weight: 700;
            line-height: 1.2em;

            display: block;

            -webkit-transition: all .4s;
            transition: all .4s;
            text-align: center;

            color: #e0e0e0;
        }

        .page-404 .outer .middle .inner .inner-status {
            font-size: 20px;

            display: block;

            margin-top: 20px;
            margin-bottom: 5px;

            text-align: center;

            color: #17a2b8;
        }

        .page-404 .outer .middle .inner .inner-detail {
            line-height: 1.4em;

            display: block;

            margin-bottom: 10px;

            text-align: center;

            color: #999999;
        }
    </style>
</head>
<body>

<div class="page-404">
    <div class="outer">
        <div class="middle">
            <div class="inner">
                <!--BEGIN CONTENT-->
                <div class="inner-circle"><i class="fa fa-cogs"></i><span>Error</span></div>
                <span class="inner-status"><c:out value='${requestScope.get("error")}'/></span>
                <c:if test="${sessionScope.get(\"auth\")=='authorised'}">
                    <div id="register-link" class="text-right">
                        <a href="app/home" class="text-info"><fmt:message key="button.return.home"/></a>
                    </div>
                </c:if>
                <c:if test="${sessionScope.get(\"auth\")!='authorised'}">
                    <div id="register-link" class="text-right">
                        <a href="start.jsp" class="text-info"><fmt:message key="button.return.home"/></a>
                    </div>
                </c:if>
                <!--END CONTENT-->
            </div>
        </div>
    </div>
</div>
</body>
</html>
