<%--
  Created by IntelliJ IDEA.
  User: Влад
  Date: 03.10.2020
  Time: 22:09
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
    <title>Title</title>
    <%@include file="jspf/scriptsBootstrap.jspf" %>

</head>
<body>
<%@include file="jspf/navbar.jspf" %>

<c:set var="user" value="${requestScope.get(\"user\")}"/>
<c:set var="details1" value="${requestScope.get(\"details1\")}"/>
<c:set var="details2" value="${requestScope.get(\"details2\")}"/>


<div id="login">
    <div class="container">
        <div id="login-row" class="row justify-content-center align-items-center">
            <div id="login-column" class="col-md-6">
                <div id="login-box" class="col-md-12">
                    <form id="login-form" class="form" action="/app/changeinfo" method="post" accept-charset="UTF-8">
                        <h3 class="text-center text-info"><fmt:message key="info.your.personal.info"/></h3>
                        <div class="form-group">
                            <label for="username" class="text-info"><fmt:message key="info.email"/></label><br>
                            <input type="email" value="${user.email}" name="email" id="username" required="required"
                                   class="form-control">
                        </div>
                        <div class="form-group">
                            <label for="password1" class="text-info"><fmt:message key="info.password"/></label><br>
                            <input type="text" value="${user.password}" name="pass1" id="password1" required="required"
                                   class="form-control">
                        </div>
                        <div class="form-group">
                            <label for="idn" class="text-info"><fmt:message key="info.idn"/></label><br>
                            <input id="idn" value="${user.idn}" type="number" name="idn" required="required"
                                   pattern="[0-9]{10}" class="form-control">
                        </div>
                        <div class="form-group">
                            <label for="name" class="text-info"><fmt:message key="info.name"/></label><br>
                            <input id="name" input type="text" value="${details2.name}" name="name" required="required"
                                   pattern="^[A-Za-z0-9]+$" class="form-control">
                        </div>
                        <div class="form-group">
                            <label for="surname" class="text-info"><fmt:message key="info.surname"/></label><br>
                            <input id="surname" input type="text" value="${details2.surname}" name="surname"
                                   required="required" pattern="^[A-Za-z0-9]+$" class="form-control">
                        </div>
                        <div class="form-group">
                            <label for="patronymic" class="text-info"><fmt:message key="info.patronymic"/></label><br>
                            <input id="patronymic" input type="text" value="${details2.patronymic}" name="patronymic"
                                   required="required" pattern="^[A-Za-z0-9]+$" class="form-control">
                        </div>
                        <div class="form-group">
                            <label for="city" class="text-info"><fmt:message key="info.city"/></label><br>
                            <input id="city" type="text" name="city" value="${details2.city}" required="required"
                                   pattern="^[A-Za-z0-9 ]+$" class="form-control">
                        </div>
                        <div class="form-group">
                            <label for="region" class="text-info"><fmt:message key="info.region"/></label><br>
                            <input id="region" type="text" name="region" value="${details2.region}" required="required"
                                   pattern="^[A-Za-z0-9 ]+$" class="form-control">
                        </div>
                        <div class="form-group">
                            <label for="school_name" class="text-info"><fmt:message key="info.school.name"/></label><br>
                            <input id="school_name" type="text" name="school_name" value="${details2.schoolName}"
                                   required="required" pattern="^[A-Za-z0-9 ]+$" class="form-control">
                        </div>
                        <div class="form-group">
                            <label for="certificate_point" class="text-info"><fmt:message
                                    key="info.average.certificate.point"/></label><br>
                            <input id="certificate_point" type="number" value="${details2.averageСertificate}"
                                   name="average_certificate_point" required="required" pattern="[0-9]{3}"
                                   class="form-control">
                        </div>
                        <div class="form-group">
                            <label for="name_ua" class="text-info"><fmt:message key="info.name"/> (UA)</label><br>
                            <input id="name_ua" input type="text" value="${details1.name}" name="name_ua"
                                   required="required" pattern="^[А-Яа-яҐЄІЇіїєґ0-9]+$" class="form-control">
                        </div>
                        <div class="form-group">
                            <label for="surname_ua" class="text-info"><fmt:message key="info.surname"/> (UA)</label><br>
                            <input id="surname_ua" input type="text" value="${details1.surname}" name="surname_ua"
                                   required="required" pattern="^[А-Яа-яҐЄІЇіїєґ0-9]+$" class="form-control">
                        </div>
                        <div class="form-group">
                            <label for="patronymic_ua" class="text-info"><fmt:message key="info.patronymic"/>
                                (UA)</label><br>
                            <input id="patronymic_ua" value="${details1.patronymic}" input type="text"
                                   name="patronymic_ua" required="required" pattern="^[А-Яа-яҐЄІЇіїєґ0-9]+$"
                                   class="form-control">
                        </div>
                        <div class="form-group">
                            <label for="city_ua" class="text-info"><fmt:message key="info.city"/> (UA)</label><br>
                            <input id="city_ua" input type="text" value="${details1.city}" name="city_ua"
                                   required="required" pattern="^[А-Яа-яҐЄІЇіїєґ0-9 ]+$" class="form-control">
                        </div>
                        <div class="form-group">
                            <label for="region_ua" class="text-info"><fmt:message key="info.region"/> (UA)</label><br>
                            <input id="region_ua" input type="text" value="${details1.region}" name="region_ua"
                                   required="required" pattern="^[А-Яа-яҐЄІЇіїєґ0-9 ]+$" class="form-control">
                        </div>
                        <div class="form-group">
                            <label for="school_name_ua" class="text-info"><fmt:message key="info.school.name"/>
                                (UA)</label><br>
                            <input id="school_name_ua" input type="text" value="${details1.schoolName}"
                                   name="school_name_ua" required="required" pattern="^[А-Яа-яҐЄІЇіїєґ0-9 ]+$"
                                   class="form-control">
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
</body>
</html>
