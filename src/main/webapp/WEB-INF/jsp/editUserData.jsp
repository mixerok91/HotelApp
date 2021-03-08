<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Edit user information</title>
    <style>
        .errorText {
            color: red;
            font-size: small;
        }
    </style>
</head>
<body>
<%--Смена локали--%>
<h1>${sessionScope.get("localization")}</h1>
<form action="mainController" method="get">
    <input type="hidden" name="command" value="change_locale">
    <input type="hidden" name="lang" value="ru">
    <input type="submit" value="RU">
</form>
<form action="mainController" method="get">
    <input type="hidden" name="command" value="change_locale">
    <input type="hidden" name="lang" value="eng">
    <input type="submit" value="EN">
</form>
<div>
    <h2>Edit user data.</h2>
    <div>
        <form action="mainController" method="post">
            <input type="hidden" name="command" value="edit_user_data">
            <div>Current email:</div>
            <div>${sessionScope.user.email}</div>
            <div>Type old password:</div>
            <div><input type="password" name="oldPassword" required></div>
            <div class="errorText">${requestScope.errors.get("oldPasswordError")}<br></div>
            <div>Type new password:</div>
            <div><input type="password" name="newPassword" required></div>
            <div class="errorText">${requestScope.errors.get("newPasswordValidationError")}<br></div>
            <div>Type your First Name:</div>
            <div><input type="text" name="firstName" required value="${sessionScope.user.firstName}"></div>
            <div class="errorText">${requestScope.errors.get("firsNameError")}<br></div>
            <div>Type your Surname:</div>
            <div><input type="text" name="surName" required value="${sessionScope.user.surName}"></div>
            <div class="errorText">${requestScope.errors.get("surNameError")}<br></div>
            <div><input type="submit" value="Save user change"></div>
        </form>
    </div>
</div>
</body>
</html>
