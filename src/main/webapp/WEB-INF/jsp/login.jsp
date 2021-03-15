<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <fmt:setLocale value="${sessionScope.localization}"/>
    <fmt:setBundle basename="local" var="loc"/>
    <fmt:message bundle="${loc}" key="local.loginpage.button.eng" var="eng_button"/>
    <fmt:message bundle="${loc}" key="local.loginpage.button.rus" var="rus_button"/>
    <fmt:message bundle="${loc}" key="local.loginpage.string.welcome" var="welcome"/>
    <fmt:message bundle="${loc}" key="local.loginpage.string.type_your_email" var="type_email"/>
    <fmt:message bundle="${loc}" key="local.loginpage.string.type_your_password" var="type_password"/>
    <fmt:message bundle="${loc}" key="local.loginpage.string.ref.to_registration_page" var="to_registration_page"/>
    <fmt:message bundle="${loc}" key="local.loginpage.string.ref.to_main_page" var="to_main_page"/>
    <fmt:message bundle="${loc}" key="local.loginpage.button.login" var="login_button"/>
    <title>Login page</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        .errorText {
            color: red;
            font-size: small;
        }
    </style>
</head>
<body class="body">
<div class="main_content">
    <%--Смена локали--%>
    <header>
        <div class="lang_buttons">
            <form action="mainController" method="get">
                <input type="hidden" name="command" value="change_locale">
                <input type="hidden" name="lang" value="ru">
                <input type="submit" value="${rus_button}">
            </form>
            <form action="mainController" method="get">
                <input type="hidden" name="command" value="change_locale">
                <input type="hidden" name="lang" value="eng">
                <input type="submit" value="${eng_button}">
            </form>
        </div>
        <div class="welcome_to_page">
            <h2>${welcome}</h2>
        </div>
        <div class="references">
            <a href="mainController?command=registration_page">${to_registration_page}</a>
            <a href="mainController?command=main_page">${to_main_page}</a>
        </div>
    </header>
    <div class="horizontal_center">
        <form action="mainController" method="post" class="center_form">
            <input type="hidden" name="command" value="login">
            <div>${type_email}</div>
            <div><input type="email" name="email" required value="${requestScope.email}"></div>
            <div class="errorText">${requestScope.errors.get("emailError")}<br></div>
            <div>${type_password}</div>
            <div><input type="password" name="password" required></div>
            <div class="errorText">${requestScope.errors.get("passwordError")}<br></div>
            <div><input type="submit" value="${login_button}"></div>
        </form>
    </div>
</div>
</body>
</html>
