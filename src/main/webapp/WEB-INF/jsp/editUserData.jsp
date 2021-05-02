<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="mytag" uri="selfMadeTag" %>
<html>
<head>
    <title>Edit user information</title>
    <fmt:setLocale value="${sessionScope.localization}"/>
    <fmt:setBundle basename="local" var="loc"/>
    <fmt:message bundle="${loc}" key="local.edituserdatapage.button.eng" var="eng_button"/>
    <fmt:message bundle="${loc}" key="local.edituserdatapage.button.rus" var="rus_button"/>
    <fmt:message bundle="${loc}" key="local.edituserdatapage.string.edit_user_data" var="edit_user_data"/>
    <fmt:message bundle="${loc}" key="local.edituserdatapage.string.current_email" var="current_email"/>
    <fmt:message bundle="${loc}" key="local.edituserdatapage.string.enter_old_password" var="enter_old_password"/>
    <fmt:message bundle="${loc}" key="local.edituserdatapage.string.enter_new_password" var="enter_new_password"/>
    <fmt:message bundle="${loc}" key="local.edituserdatapage.string.enter_firstname" var="enter_firstname"/>
    <fmt:message bundle="${loc}" key="local.edituserdatapage.string.enter_surname" var="enter_surname"/>
    <fmt:message bundle="${loc}" key="local.edituserdatapage.button.save_changes" var="save_changes"/>
    <fmt:message bundle="${loc}" key="local.edituserdatapage.ref.to_main_page" var="to_main_page"/>
    <fmt:message bundle="${loc}" key="local.edituserdatapage.ref.to_user_cabinet_page" var="to_user_cabinet_page"/>
    <style>
        .errorText {
            color: red;
            font-size: small;
        }
    </style>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body class="body">
<c:if test="${empty sessionScope.user.role}">
    <c:redirect url="/mainController?command=main_page"/>
</c:if>
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
            <h2>${edit_user_data}</h2>
        </div>
        <div class="references">
            <a href="mainController?command=main_page">${to_main_page}</a>
            <a href="mainController?command=user_cabinet_page">${to_user_cabinet_page}</a>
        </div>
    </header>
    <div class="horizontal_center">
        <form action="mainController" method="post" class="center_form">
            <input type="hidden" name="command" value="edit_user_data">
            <div>${current_email}</div>
            <div>${sessionScope.user.email}</div>
            <div>${enter_old_password}</div>
            <div><input type="password" name="oldPassword" required></div>
            <div class="errorText">${requestScope.errors.get("oldPasswordError")}<br></div>
            <div>${enter_new_password}</div>
            <div><input type="password" name="newPassword" required></div>
            <div class="errorText">${requestScope.errors.get("newPasswordValidationError")}<br></div>
            <div>${enter_firstname}</div>
            <div><input type="text" name="firstName" required value="${sessionScope.user.firstName}"></div>
            <div class="errorText">${requestScope.errors.get("firsNameError")}<br></div>
            <div>${enter_surname}</div>
            <div><input type="text" name="surName" required value="${sessionScope.user.surName}"></div>
            <div class="errorText">${requestScope.errors.get("surNameError")}<br></div>
            <div><input type="submit" value="${save_changes}"></div>
        </form>
    </div>
</div>
<mytag:madeByStepanov/>
</body>
</html>
