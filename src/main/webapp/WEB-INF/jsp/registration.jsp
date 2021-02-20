<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Registration</title>
    <style>
        .errorText {
            color: red;
            font-size: small;
        }
    </style>
</head>
<body>
<div>
    <h3>Welcome to registration page</h3>
</div>
<div>
    <a href="userController?command=login_page">To login page</a><br>
    <a href="mainController?command=main_page">To main page</a>
    <h4>Registration form:</h4>
    <div>
        <form action="userController" method="post">
            <input type="hidden" name="command" value="registration">
            <div>Type your email:</div>
            <div><input type="email" name="email" required value="${requestScope.user.email}"></div>
            <div class="errorText">${requestScope.errors.get("emailError")}<br></div>
            <div>Type your password:</div>
            <div><input type="password" name="password" required></div>
            <div class="errorText">${requestScope.errors.get("passwordError")}<br></div>
            <div>Type your First Name:</div>
            <div><input type="text" name="firstName" required value="${requestScope.user.firstName}"></div>
            <div class="errorText">${requestScope.errors.get("firsNameError")}<br></div>
            <div>Type your Surname:</div>
            <div><input type="text" name="surName" required value="${requestScope.user.surName}"></div>
            <div class="errorText">${requestScope.errors.get("surNameError")}<br></div>
            <div><input type="submit" value="Register new user"></div>
        </form>
    </div>
</div>
</body>
</html>
