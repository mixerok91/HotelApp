<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>User's Cabinet</title>
    <fmt:setLocale value="${sessionScope.localization}"/>
    <fmt:setBundle basename="local" var="loc"/>
    <fmt:message bundle="${loc}" key="local.usercabinetpage.button.eng" var="eng_button"/>
    <fmt:message bundle="${loc}" key="local.usercabinetpage.button.rus" var="rus_button"/>
    <fmt:message bundle="${loc}" key="local.usercabinetpage.string.users_cabinet" var="users_cabinet"/>
    <fmt:message bundle="${loc}" key="local.usercabinetpage.button.show_all_reservations" var="show_all_reservations"/>
    <fmt:message bundle="${loc}" key="local.usercabinetpage.button.show_actual_reservations"
                 var="show_actual_reservations"/>
    <fmt:message bundle="${loc}" key="local.usercabinetpage.button.edit_user_info" var="edit_user_info"/>
    <fmt:message bundle="${loc}" key="local.usercabinetpage.button.pay_for_reservation" var="pay_for_reservation"/>
    <fmt:message bundle="${loc}" key="local.usercabinetpage.button.cancel_for_reservation"
                 var="cancel_for_reservation"/>
    <fmt:message bundle="${loc}" key="local.usercabinetpage.ref.to_main_page" var="to_main_page"/>
    <fmt:message bundle="${loc}" key="local.usercabinetpage.string.dates" var="dates"/>
    <fmt:message bundle="${loc}" key="local.usercabinetpage.string.from" var="from"/>
    <fmt:message bundle="${loc}" key="local.usercabinetpage.string.to" var="to"/>
    <fmt:message bundle="${loc}" key="local.usercabinetpage.string.room_type_name" var="room_type_name"/>
    <fmt:message bundle="${loc}" key="local.usercabinetpage.string.room_number" var="room_number"/>
    <fmt:message bundle="${loc}" key="local.usercabinetpage.string.room_description" var="room_description"/>
    <fmt:message bundle="${loc}" key="local.usercabinetpage.string.price" var="price"/>
    <fmt:message bundle="${loc}" key="local.usercabinetpage.string.price_per_day" var="price_per_day"/>
    <fmt:message bundle="${loc}" key="local.usercabinetpage.string.total_amount" var="total_amount"/>
    <fmt:message bundle="${loc}" key="local.usercabinetpage.string.users_reservations" var="users_reservations"/>
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
            <h1>${users_cabinet}</h1>
        </div>
        <div class="references">
            <a href="mainController?command=main_page">${to_main_page}</a>
        </div>
    </header>

    <div class="horizontal_center">
        <div class="cabinet_buttons">
            <div class="cabinet_button">
                <form action="mainController" method="get">
                    <input type="hidden" name="command" value="show_all_users_reservations">
                    <input type="submit" value="${show_all_reservations}">
                </form>
            </div>
            <div class="cabinet_button">
                <form action="mainController" method="get">
                    <input type="hidden" name="command" value="user_cabinet_page">
                    <input type="submit" value="${show_actual_reservations}">
                </form>
            </div>
            <div class="cabinet_button">
                <form action="mainController" method="get">
                    <input type="hidden" name="command" value="edit_user_data_page">
                    <input type="submit" value="${edit_user_info}">
                </form>
            </div>
        </div>
        <div class="horizontal_center">
            <c:if test="${sessionScope.userReservations.size() > 0}">
                <div><h3>${users_reservations}</h3></div>
                <ul>
                    <c:forEach items="${sessionScope.userReservations}" var="reservation">
                        <li>
                            <div>
                                    ${dates}<br>
                                    ${from} ${reservation.inDate} ${to} ${reservation.outDate}
                            </div>
                            <div>
                                    ${room_type_name}<br>
                                    ${reservation.room.roomType.typeName}
                            </div>
                            <div>
                                    ${room_number}<br>
                                    ${reservation.room.roomNumber}
                            </div>
                            <div>
                                    ${room_description}<br>
                                <c:choose>
                                    <c:when test="${sessionScope.localization.equals('ru')}">
                                        ${reservation.room.roomType.descriptionRus}
                                    </c:when>
                                    <c:otherwise>
                                        ${reservation.room.roomType.descriptionEng}
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div>
                                    ${price}
                                <div>
                                        ${price_per_day}<br>
                                        ${reservation.room.dayCost}$
                                </div>
                                <br>
                                <div>
                                        ${total_amount}<br>
                                        ${reservation.room.dayCost * (reservation.outDate.toEpochDay() - reservation.inDate.toEpochDay())}$
                                </div>
                                <br>
                            </div>
                            <div>
                                <c:if test="${reservation.bookStatus.name() eq 'RESERVED'}">
                                    <form action="mainController" method="post">
                                        <input type="hidden" name="command" value="reservation_payment">
                                        <input type="hidden" name="reservationId" value="${reservation.id}">
                                        <input type="submit" value="${pay_for_reservation}">
                                    </form>
                                    <form action="mainController" method="post">
                                        <input type="hidden" name="command" value="reservation_cancel">
                                        <input type="hidden" name="reservationId" value="${reservation.id}">
                                        <input type="submit" value="${cancel_for_reservation}">
                                    </form>
                                </c:if>
                            </div>
                        </li>
                    </c:forEach>
                </ul>
            </c:if>
        </div>
    </div>
</div>
</body>
</html>