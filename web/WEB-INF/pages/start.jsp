<!-- обратите внимание на spring тэги -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>



<c:set var="language" value="${not empty sessionScope.get(\"language\") ? sessionScope.get(\"language\") : not empty sessionScope.get(\"language\") ? sessionScope.get(\"language\") : 'uk'}" scope="session" />
<fmt:setLocale value="${language}" />


<fmt:setBundle basename="resource"/>
<html>
<head>
  <%@include file="jspf/scriptsBootstrap.jspf" %>
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
      height: 360px;
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
          <form id="login-form" class="form" action="login" method="post">
            <h3 class="text-center text-info"><fmt:message key="start.text.autorisation" /></h3>
            <div class="form-group">
              <label for="username" class="text-info"><fmt:message key="start.text.email" /></label><br>
              <input type="text" name="login" id="username" required="required" class="form-control">
            </div>
            <div class="form-group">
              <label for="password" class="text-info"><fmt:message key="start.text.password" /></label><br>
              <input type="text" name="pass" id="password" required="required" class="form-control">
            </div>
            <div class="form-group">
              <input type="submit" name="submit" class="btn btn-info btn-md" value="<fmt:message key="button.send" />">
            </div>
            <br>
            <div id="register-link" class="text-right">
              <a href="/registration" class="text-info"><fmt:message key="start.text.registration" /></a>
            </div>
            <br>
            <br>
            <%@include file="changeLanguage.jsp" %>
          </form>
        </div>
      </div>
    </div>
  </div>
</div>


</body>
</html>
