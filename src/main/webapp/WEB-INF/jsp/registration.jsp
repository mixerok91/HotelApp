<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <fmt:setLocale value="${sessionScope.localization}"/>
    <fmt:setBundle basename="local" var="loc"/>
    <fmt:message bundle="${loc}" key="local.registrationpage.button.eng" var="eng_button"/>
    <fmt:message bundle="${loc}" key="local.registrationpage.button.rus" var="rus_button"/>
    <fmt:message bundle="${loc}" key="local.registrationpage.string.welcome" var="welcome"/>
    <fmt:message bundle="${loc}" key="local.registrationpage.ref.to_login_page" var="to_login_page"/>
    <fmt:message bundle="${loc}" key="local.registrationpage.ref.to_main_page" var="to_main_page"/>
    <fmt:message bundle="${loc}" key="local.registrationpage.string.registration_form" var="registration_form"/>
    <fmt:message bundle="${loc}" key="local.registrationpage.string.type_your_email" var="type_your_email"/>
    <fmt:message bundle="${loc}" key="local.registrationpage.string.type_your_password" var="type_your_password"/>
    <fmt:message bundle="${loc}" key="local.registrationpage.string.type_first_name" var="type_your_firstname"/>
    <fmt:message bundle="${loc}" key="local.registrationpage.string.type_surname" var="type_your_surname"/>
    <fmt:message bundle="${loc}" key="local.registrationpage.button.register_new_user" var="register_button"/>
    <title>Registration</title>
    <style>
        .errorText {
            color: red;
            font-size: small;
        }
    </style>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body class="body">
<div class="main_content">
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
            <h3>${welcome}</h3>
        </div>
        <div class="references">
            <a href="mainController?command=login_page">${to_login_page}</a>
            <a href="mainController?command=main_page">${to_main_page}</a>
        </div>
    </header>
    <div class="horizontal_center">
        <div class="center_form" style="font-size: 14px">${registration_form}</div>
        <form action="mainController" method="post" class="center_form">
            <input type="hidden" name="command" value="registration">
            <div>${type_your_email}</div>
            <div><input type="email" name="email" required value="${requestScope.user.email}"></div>
            <div class="errorText">${requestScope.errors.get("emailError")}<br></div>
            <div>${type_your_password}</div>
            <div><input type="password" name="password" required></div>
            <div class="errorText">${requestScope.errors.get("passwordError")}<br></div>
            <div>${type_your_firstname}</div>
            <div><input type="text" name="firstName" required value="${requestScope.user.firstName}"></div>
            <div class="errorText">${requestScope.errors.get("firsNameError")}<br></div>
            <div>${type_your_surname}</div>
            <div><input type="text" name="surName" required value="${requestScope.user.surName}"></div>
            <div class="errorText">${requestScope.errors.get("surNameError")}<br></div>
            <div><input type="submit" value="${register_button}"></div>
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
        </form>
    </div>
</div>
</body>
</html>
