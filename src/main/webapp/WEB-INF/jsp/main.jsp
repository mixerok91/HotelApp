<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Main page</title>
    <fmt:setLocale value="${sessionScope.localization}"/>
    <fmt:setBundle basename="local" var="loc"/>
    <fmt:message bundle="${loc}" key="local.mainpage.button.eng" var="eng_button"/>
    <fmt:message bundle="${loc}" key="local.mainpage.button.rus" var="rus_button"/>
    <fmt:message bundle="${loc}" key="local.mainpage.string.welcome" var="welcome"/>
    <fmt:message bundle="${loc}" key="local.mainpage.string.hello_user" var="hello"/>
    <fmt:message bundle="${loc}" key="local.mainpage.string.room_types_description" var="description"/>
    <fmt:message bundle="${loc}" key="local.mainpage.ref.to_login_page" var="to_login_page"/>
    <fmt:message bundle="${loc}" key="local.mainpage.ref.to_registration_page" var="to_registration_page"/>
    <fmt:message bundle="${loc}" key="local.mainpage.ref.to_room_reservation" var="to_reservation_page"/>
    <fmt:message bundle="${loc}" key="local.mainpage.ref.to_user_cabinet" var="to_user_cabinet"/>
    <fmt:message bundle="${loc}" key="local.mainpage.ref.to_admin_cabinet" var="to_admin_cabinet"/>
    <fmt:message bundle="${loc}" key="local.mainpage.ref.logout" var="logout"/>
    <fmt:message bundle="${loc}" key="local.mainpage.string.room_type_name" var="room_type_name"/>
    <fmt:message bundle="${loc}" key="local.mainpage.string.room_type_description" var="room_type_description"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body class="body">
<c:if test="${sessionScope.roomTypes == null}">
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
            <h1>${welcome}</h1>
            <c:if test="${sessionScope.user != null}">
                <div>${hello} ${sessionScope.user.firstName}</div>
            </c:if>
        </div>
        <%--Ссылка на логинацию/регистрацию или логаут--%>
        <div class="references">
            <c:if test="${sessionScope.user == null}">
                <a href="mainController?command=registration_page">${to_registration_page}</a>
                <a href="mainController?command=login_page">${to_login_page}</a>
            </c:if>
            <c:if test="${sessionScope.user != null}">
                <a href="mainController?command=logout">${logout}</a>
                <%--В кабинет узера--%>
                <a href="mainController?command=user_cabinet_page">${to_user_cabinet}</a>
                <%--В кабинет админа--%>
                <c:if test="${sessionScope.user.role.name().equals('ADMIN')}">
                    <a href="mainController?command=admin_cabinet_page">${to_admin_cabinet}</a>
                </c:if>
            </c:if>
            <%--К бронированию номера--%>
            <a href="mainController?command=reservation_page">${to_reservation_page}</a>
        </div>
    </header>


    <br>
    <div class="horizontal_center">
        <div><h3>${description}</h3></div>
        <div>
            <ul>
                <c:forEach var="roomType" items="${sessionScope.roomTypes}">
                    <li>
                        <div>
                            <div>
                                    ${room_type_name}<br>
                                    ${roomType.typeName}
                            </div>
                            <div>
                                    ${room_type_description}<br>
                                <c:choose>
                                    <c:when test="${sessionScope.localization.equals('ru')}">
                                        ${roomType.descriptionRus}
                                    </c:when>
                                    <c:otherwise>
                                        ${roomType.descriptionEng}
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>
</div>
</body>
</html>
