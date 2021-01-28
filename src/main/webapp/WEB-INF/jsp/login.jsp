<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Login page</title>
    <style>
        .errorText {
            color: red;
            font-size: small;
        }
    </style>
</head>
<body>
<div>
    <h2>Welcome to login page</h2>
    <div>
        <form action="userController" method="post">
            <input type="hidden" name="command" value="login">
            <div>Type your email:</div>
            <div><input type="email" name="email" required value="${requestScope.email}"></div>
            <div class="errorText">${requestScope.emailError}<br></div>
            <div>Type your password:</div>
            <div><input type="password" name="password" required></div>
            <div class="errorText">${requestScope.passwordError}<br></div>
            <div><input type="submit" value="Login in"></div>
        </form>
    </div>
</div>
</body>
</html>
