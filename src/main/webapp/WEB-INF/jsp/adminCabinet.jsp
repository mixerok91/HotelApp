<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Admin's cabinet</title>
    <fmt:setLocale value="${sessionScope.localization}"/>
    <fmt:setBundle basename="local" var="loc"/>
    <fmt:message bundle="${loc}" key="local.admincabinetpage.button.eng" var="eng_button"/>
    <fmt:message bundle="${loc}" key="local.admincabinetpage.button.rus" var="rus_button"/>
    <fmt:message bundle="${loc}" key="local.admincabinetpage.string.admin_cabinet" var="admin_cabinet"/>
    <fmt:message bundle="${loc}" key="local.admincabinetpage.button.administrating_room_types" var="administrating_room_types"/>
    <fmt:message bundle="${loc}" key="local.admincabinetpage.button.administrating_rooms" var="administrating_rooms"/>
    <fmt:message bundle="${loc}" key="local.admincabinetpage.button.get_user_info" var="get_user_info"/>
    <fmt:message bundle="${loc}" key="local.admincabinetpage.button.get_reservations" var="get_reservations"/>
    <fmt:message bundle="${loc}" key="local.admincabinetpage.string.enter_user_email" var="enter_user_email"/>
    <fmt:message bundle="${loc}" key="local.admincabinetpage.string.found_reservations_in_period" var="found_reservation_in_period"/>
    <fmt:message bundle="${loc}" key="local.admincabinetpage.string.from" var="from"/>
    <fmt:message bundle="${loc}" key="local.admincabinetpage.string.to" var="to"/>
    <fmt:message bundle="${loc}" key="local.admincabinetpage.string.reservation_id" var="reservation_id"/>
    <fmt:message bundle="${loc}" key="local.admincabinetpage.string.reservation_in_date" var="reservation_in_date"/>
    <fmt:message bundle="${loc}" key="local.admincabinetpage.string.reservation_out_date" var="reservation_out_date"/>
    <fmt:message bundle="${loc}" key="local.admincabinetpage.string.room_number" var="room_number"/>
    <fmt:message bundle="${loc}" key="local.admincabinetpage.string.room_type_name" var="room_type_name"/>
    <fmt:message bundle="${loc}" key="local.admincabinetpage.string.user_email" var="user_email"/>
    <fmt:message bundle="${loc}" key="local.admincabinetpage.string.reservation_status" var="reservation_status"/>
    <fmt:message bundle="${loc}" key="local.admincabinetpage.string.reservations_bill" var="reservation_bill"/>
    <fmt:message bundle="${loc}" key="local.admincabinetpage.string.total_amount" var="total_amount"/>
    <fmt:message bundle="${loc}" key="local.admincabinetpage.string.is_paid" var="is_paid"/>
    <fmt:message bundle="${loc}" key="local.admincabinetpage.string.paid" var="paid"/>
    <fmt:message bundle="${loc}" key="local.admincabinetpage.string.not_paid" var="not_paid"/>
    <fmt:message bundle="${loc}" key="local.admincabinetpage.string.users_reservations" var="users_reservations"/>
    <fmt:message bundle="${loc}" key="local.admincabinetpage.string.found_users_info" var="found_users_info"/>
    <fmt:message bundle="${loc}" key="local.admincabinetpage.string.email" var="email"/>
    <fmt:message bundle="${loc}" key="local.admincabinetpage.string.first_name" var="first_name"/>
    <fmt:message bundle="${loc}" key="local.admincabinetpage.string.surname" var="surname"/>
    <fmt:message bundle="${loc}" key="local.admincabinetpage.string.last_in_date" var="last_in_date"/>
</head>
<body>
<c:if test="${sessionScope.user.role != 'ADMIN'}">
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
<div>
    <span>${admin_cabinet}</span>
    <div>
        <form name="toRoomType" method="get" action="mainController">
            <input type="hidden" name="command" value="room_type_administration_page">
            <input type="submit" value="${administrating_room_types}">
        </form>
    </div>
    <div>
        <form name="toRooms" method="get" action="mainController">
            <input type="hidden" name="command" value="room_administration_page">
            <input type="submit" value="${administrating_rooms}">
        </form>
    </div>
    <div>
        <form name="getUser" method="get" action="mainController">
            <input type="hidden" name="command" value="get_users_information_by_email">
            <input type="email" name="userEmail" required value="${enter_user_email}">
            <input type="submit" value="${get_user_info}">
        </form>
    </div>
    <div>
        <span>${requestScope.dateError}</span>
        <form name="getReservationsByPeriod" method="get" action="mainController">
            <input type="hidden" name="command" value="get_reservations_by_period">
            <input type="date" name="fromDate" required value="${requestScope.fromDate}">
            <input type="date" name="beforeDate" required value="${requestScope.beforeDate}">
            <input type="submit" value="${get_reservations}">
        </form>
    </div>
    <%--Message:--%>
    <div>
        <span>${requestScope.adminPageMessage}</span><br>
    </div>
    <%--If user found:--%>
    <c:if test="${not empty requestScope.adminPageUser}">
        <div>
            <div>
                <span>${found_users_info}</span><br>
                <span>${email}</span><br>
                <span>${requestScope.adminPageUser.email}</span><br>
                <span>${first_name}</span><br>
                <span>${requestScope.adminPageUser.firstName}</span><br>
                <span>${surname}</span><br>
                <span>${requestScope.adminPageUser.surName}</span><br>
                <span>${last_in_date}</span><br>
                <span>${requestScope.adminPageUser.lastInDate}</span><br>
            </div>
            <br>
            <c:if test="${not empty requestScope.adminPageUserReservations}">
                <div>
                    <h3>${users_reservations}</h3><br>
                    <c:forEach var="reservation" items="${requestScope.adminPageUserReservations}">
                        <div>
                            <span>${reservation_id}</span><br>
                            <span>${reservation.id}</span><br>
                            <span>${reservation_in_date}</span><br>
                            <span>${reservation.inDate}</span><br>
                            <span>${reservation_out_date}</span><br>
                            <span>${reservation.outDate}</span><br>
                            <span>${room_number}</span><br>
                            <span>${reservation.room.roomNumber}</span><br>
                            <span>${room_type_name}</span><br>
                            <span>${reservation.room.roomType.typeName}</span><br>
                            <span>${user_email}</span><br>
                            <span>${reservation.user.email}</span><br>
                            <span>${reservation_status}</span><br>
                            <span>${reservation.bookStatus}</span><br>
                            <c:if test="${not empty reservation.bill}">
                                ${reservation_bill}<br>
                                <span>${total_amount}</span><br>
                                <span>${reservation.bill.totalAmount}</span><br>
                                <span>${is_paid}</span><br>
                                <div>
                                    <c:if test="${reservation.bill.paid}">
                                        ${paid}
                                    </c:if>
                                    <c:if test="${reservation.bill.paid == false}">
                                        ${not_paid}
                                    </c:if>
                                </div>
                            </c:if>
                            <h3>-----------------------------------------------</h3>
                        </div>
                    </c:forEach>
                </div>
            </c:if>
        </div>
    </c:if>
    <%--If reservations by period was found--%>
    <c:if test="${not empty requestScope.reservationsByPeriod}">
        <div>
            ${found_reservation_in_period} ${from} ${requestScope.fromDate} ${to} ${requestScope.beforeDate}:<br>
            <c:forEach var="reservation" items="${requestScope.reservationsByPeriod}">
                <div>
                    <span>${reservation_id}</span><br>
                    <span>${reservation.id}</span><br>
                    <span>${reservation_in_date}</span><br>
                    <span>${reservation.inDate}</span><br>
                    <span>${reservation_out_date}</span><br>
                    <span>${reservation.outDate}</span><br>
                    <span>${room_number}</span><br>
                    <span>${reservation.room.roomNumber}</span><br>
                    <span>${room_type_name}</span><br>
                    <span>${reservation.room.roomType.typeName}</span><br>
                    <span>${user_email}</span><br>
                    <span>${reservation.user.email}</span><br>
                    <span>${reservation_status}</span><br>
                    <span>${reservation.bookStatus}</span><br>
                    <c:if test="${not empty reservation.bill}">
                        ${reservation_bill}<br>
                        <span>${total_amount}</span><br>
                        <span>${reservation.bill.totalAmount}</span><br>
                        <span>${is_paid}</span><br>
                        <div>
                            <c:if test="${reservation.bill.paid}">
                                ${paid}
                            </c:if>
                            <c:if test="${reservation.bill.paid == false}">
                                ${not_paid}
                            </c:if>
                        </div>
                    </c:if>
                    <h3>-----------------------------------------------</h3>
                </div>
            </c:forEach>
        </div>
    </c:if>
</div>
<a href="mainController?command=main_page">To main page</a>
</body>
</html>
