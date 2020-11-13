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
        body {
            margin: 0;
            padding: 0;
            background-color: #17a2b8;
            height: 100vh;
        }

        #login .container #login-row #login-column #login-box {
            margin-top: 120px;
            max-width: 600px;
            height: 1650px;
            border: 1px solid #9C9C9C;
            background-color: #EAEAEA;
        }

        #login .container #login-row #login-column #login-box #login-form {
            padding: 20px;
        }

        #login .container #login-row #login-column #login-box #login-form #register-link {
            margin-top: -85px;
        }
    </style>
</head>
<body>


<div id="login">
    <div class="container">
        <div id="login-row" class="row justify-content-center align-items-center">
            <div id="login-column" class="col-md-6">
                <div id="login-box" class="col-md-12">
                    <form id="login-form" class="form" action="/doRegistration" method="post">
                        <h3 class="text-center text-info"><fmt:message key="start.text.registration"/></h3>
                        <div class="form-group">
                            <label for="username" class="text-info"><fmt:message
                                    key="registration.enter.email"/></label><br>
                            <input type="email" name="email" id="username" required="required" class="form-control">
                        </div>
                        <div class="form-group">
                            <label for="password1" class="text-info"><fmt:message
                                    key="registration.enter.password"/></label><br>
                            <input type="text" name="pass1" id="password1" required="required" class="form-control">
                        </div>
                        <div class="form-group">
                            <label for="password2" class="text-info"><fmt:message
                                    key="registration.enter.password.repeat"/></label><br>
                            <input type="text" name="pass2" id="password2" required="required" class="form-control">
                        </div>
                        <div class="form-group">
                            <label for="idn" class="text-info"><fmt:message key="registration.enter.idn"/></label><br>
                            <input id="idn" type="number" name="idn" required="required" pattern="[0-9]{10}"
                                   class="form-control">
                        </div>
                        <div class="form-group">
                            <label for="name" class="text-info"><fmt:message
                                    key="registration.enter.english.name"/></label><br>
                            <input id="name" input type="text" name="name" required="required" pattern="^[A-Za-z0-9]+$"
                                   class="form-control">
                        </div>
                        <div class="form-group">
                            <label for="surname" class="text-info"><fmt:message
                                    key="registration.enter.english.surname"/></label><br>
                            <input id="surname" input type="text" name="surname" required="required"
                                   pattern="^[A-Za-z0-9]+$" class="form-control">
                        </div>
                        <div class="form-group">
                            <label for="patronymic" class="text-info"><fmt:message
                                    key="registration.enter.english.patronymic"/></label><br>
                            <input id="patronymic" input type="text" name="patronymic" required="required"
                                   pattern="^[A-Za-z0-9]+$" class="form-control">
                        </div>
                        <div class="form-group">
                            <label for="city" class="text-info"><fmt:message
                                    key="registration.enter.english.city"/></label><br>
                            <input id="city" type="text" name="city" required="required" pattern="^[A-Za-z0-9 ]+$"
                                   class="form-control">
                        </div>
                        <div class="form-group">
                            <label for="region" class="text-info"><fmt:message
                                    key="registration.enter.english.region"/></label><br>
                            <input id="region" type="text" name="region" required="required" pattern="^[A-Za-z0-9 ]+$"
                                   class="form-control">
                        </div>
                        <div class="form-group">
                            <label for="school_name" class="text-info"><fmt:message
                                    key="registration.enter.english.school.name"/></label><br>
                            <input id="school_name" type="text" name="school_name" required="required"
                                   pattern="^[A-Za-z0-9 ]+$" class="form-control">
                        </div>
                        <div class="form-group">
                            <label for="certificate_point" class="text-info"><fmt:message
                                    key="registration.enter.average.certificate.point"/></label><br>
                            <input id="certificate_point" type="number" name="average_certificate_point"
                                   required="required" pattern="[0-9]{3}" class="form-control">
                        </div>
                        <div class="form-group">
                            <label for="name_ua" class="text-info"><fmt:message
                                    key="registration.enter.ukraine.name"/></label><br>
                            <input id="name_ua" input type="text" name="name_ua" required="required"
                                   pattern="^[А-Яа-яҐЄІЇіїєґ0-9]+$" class="form-control">
                        </div>
                        <div class="form-group">
                            <label for="surname_ua" class="text-info"><fmt:message
                                    key="registration.enter.ukraine.surname"/></label><br>
                            <input id="surname_ua" input type="text" name="surname_ua" required="required"
                                   pattern="^[А-Яа-яҐЄІЇіїєґ0-9]+$" class="form-control">
                        </div>
                        <div class="form-group">
                            <label for="patronymic_ua" class="text-info"><fmt:message
                                    key="registration.enter.ukraine.patronymic"/></label><br>
                            <input id="patronymic_ua" input type="text" name="patronymic_ua" required="required"
                                   pattern="^[А-Яа-яҐЄІЇіїєґ0-9]+$" class="form-control">
                        </div>
                        <div class="form-group">
                            <label for="city_ua" class="text-info"><fmt:message
                                    key="registration.enter.ukraine.city"/></label><br>
                            <input id="city_ua" input type="text" name="city_ua" required="required"
                                   pattern="^[А-Яа-яҐЄІЇіїєґ0-9 ]+$" class="form-control">
                        </div>
                        <div class="form-group">
                            <label for="region_ua" class="text-info"><fmt:message
                                    key="registration.enter.ukraine.region"/></label><br>
                            <input id="region_ua" input type="text" name="region_ua" required="required"
                                   pattern="^[А-Яа-яҐЄІЇіїєґ0-9 ]+$" class="form-control">
                        </div>
                        <div class="form-group">
                            <label for="school_name_ua" class="text-info"><fmt:message
                                    key="registration.enter.ukraine.school.name"/></label><br>
                            <input id="school_name_ua" input type="text" name="school_name_ua" required="required"
                                   pattern="^[А-Яа-яҐЄІЇіїєґ0-9 ]+$" class="form-control">
                        </div>

                        <div class="form-group">
                            <input type="submit" name="submit" class="btn btn-info btn-md"
                                   value="<fmt:message key="button.send" />">
                        </div>
                        <%--                        <br>--%>
                        <%--&lt;%&ndash;                        <div id="register-link" class="text-right">&ndash;%&gt;--%>
                        <%--&lt;%&ndash;                            <a href="/registration" class="text-info"><fmt:message key="start.text.registration" /></a>&ndash;%&gt;--%>
                        <%--&lt;%&ndash;                        </div>&ndash;%&gt;--%>
                        <%--                        <br>--%>
                        <br>
                        <%@include file="changeLanguage.jsp" %>
                        <div id="register-link" class="text-right">
                            <a href="/start" class="text-info"><fmt:message key="registration.back"/></a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>