<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Reservation confirm</title>
    <fmt:setLocale value="${sessionScope.localization}"/>
    <fmt:setBundle basename="local" var="loc"/>
    <fmt:message bundle="${loc}" key="local.reservationconfirmpage.button.eng" var="eng_button"/>
    <fmt:message bundle="${loc}" key="local.reservationconfirmpage.button.rus" var="rus_button"/>
    <fmt:message bundle="${loc}" key="local.reservationconfirmpage.string.reservation_confirmation" var="confirming_reservation"/>
    <fmt:message bundle="${loc}" key="local.reservationconfirmpage.string.reserving_room_description" var="reserving_room_description"/>
    <fmt:message bundle="${loc}" key="local.reservationconfirmpage.string.dates" var="dates"/>
    <fmt:message bundle="${loc}" key="local.reservationconfirmpage.string.from" var="from"/>
    <fmt:message bundle="${loc}" key="local.reservationconfirmpage.string.to" var="to"/>
    <fmt:message bundle="${loc}" key="local.reservationconfirmpage.string.room_type_name" var="room_type_name"/>
    <fmt:message bundle="${loc}" key="local.reservationconfirmpage.string.room_number" var="room_number"/>
    <fmt:message bundle="${loc}" key="local.reservationconfirmpage.string.room_description" var="room_description"/>
    <fmt:message bundle="${loc}" key="local.reservationconfirmpage.string.user_who_reserving_room" var="user_who_reserving_room"/>
    <fmt:message bundle="${loc}" key="local.reservationconfirmpage.string.user_first_name" var="first_name"/>
    <fmt:message bundle="${loc}" key="local.reservationconfirmpage.string.user_surname" var="surname"/>
    <fmt:message bundle="${loc}" key="local.reservationconfirmpage.string.user_email" var="email"/>
    <fmt:message bundle="${loc}" key="local.reservationconfirmpage.string.price" var="price"/>
    <fmt:message bundle="${loc}" key="local.reservationconfirmpage.string.price_per_day" var="price_per_day"/>
    <fmt:message bundle="${loc}" key="local.reservationconfirmpage.string.total_amount" var="total_amount"/>
    <fmt:message bundle="${loc}" key="local.reservationconfirmpage.button.confirm_reservation" var="confirm_reservation"/>
    <fmt:message bundle="${loc}" key="local.reservationconfirmpage.button.cancel_reservation" var="cancel_reservation"/>
    <fmt:message bundle="${loc}" key="local.reservationconfirmpage.ref.to_main_page" var="to_main_page"/>

</head>
<body>
<c:if test="${empty sessionScope.user.role}">
    <c:redirect url="/mainController?command=main_page"/>
</c:if>
<%--Смена локали--%>
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
<h3>${confirming_reservation}</h3>
<div>
    <div>
        ${reserving_room_description}<br>
        <div>
            ${dates}<br>
            ${from} ${sessionScope.selectedBill.reservation.inDate} ${to} ${sessionScope.selectedBill.reservation.outDate}
        </div><br>
        <div>
            ${room_type_name}<br>
            ${sessionScope.selectedBill.reservation.room.roomType.typeName}
        </div><br>
        <div>
            ${room_number}<br>
            ${sessionScope.selectedBill.reservation.room.roomNumber}
        </div><br>
        <div>
            ${room_description}<br>
            <c:choose>
                <c:when test="${sessionScope.localization.equals('ru')}">
                    ${sessionScope.selectedBill.reservation.room.roomType.descriptionRus}
                </c:when>
                <c:otherwise>
                    ${sessionScope.selectedBill.reservation.room.roomType.descriptionEng}
                </c:otherwise>
            </c:choose>
        </div><br>
    </div><br>
    <div>
        ${user_who_reserving_room}
        <div>
            ${first_name}<br>
            ${sessionScope.selectedBill.reservation.user.firstName}
        </div><br>
        <div>
            ${surname}<br>
            ${sessionScope.selectedBill.reservation.user.surName}
        </div><br>
        <div>
            ${email}<br>
            ${sessionScope.selectedBill.reservation.user.email}
        </div><br>
    </div><br>
    <div>
        ${price}
        <div>
            ${price_per_day}<br>
            ${sessionScope.selectedBill.reservation.room.dayCost}$
        </div><br>
        <div>
            ${total_amount}<br>
            ${sessionScope.selectedBill.totalAmount}$
        </div><br>
    </div>
    <form action="mainController" name="post">
        <input type="hidden" name="command" value="reservation_confirm">
        <input type="submit" value="${confirm_reservation}">
    </form>
    <form action="mainController" name="get">
        <input type="hidden" name="command" value="reservation_undo">
        <input type="submit" value="${cancel_reservation}">
    </form>
</div>

<a href="mainController?command=main_page">${to_main_page}</a>
</body>
</html>
