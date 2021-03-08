<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Admin's cabinet</title>
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
<span>Admin's Cabinet.</span>
<a href="mainController?command=main_page">To main page</a>
<div>
    <form name="toRoomType" method="get" action="mainController">
        <input type="hidden" name="command" value="room_type_administration_page">
        <input type="submit" value="Administrating room types">
    </form>
</div>
<div>
    <form name="toRooms" method="get" action="mainController">
        <input type="hidden" name="command" value="room_administration_page">
        <input type="submit" value="Administrating rooms">
    </form>
</div>
<div>
    <form name="getUser" method="get" action="mainController">
        <input type="hidden" name="command" value="get_users_information_by_email">
        <input type="email" name="userEmail" required value="Type user email">
        <input type="submit" value="Get user's information">
    </form>
</div>
<div>
    <span>${requestScope.dateError}</span>
    <form name="getReservationsByPeriod" method="get" action="mainController">
        <input type="hidden" name="command" value="get_reservations_by_period">
        <input type="date" name="fromDate" required value="${requestScope.fromDate}">
        <input type="date" name="beforeDate" required value="${requestScope.beforeDate}">
        <input type="submit" value="Get reservations">
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
            <span>Found user's information:</span><br>
            <span>Email:</span><br>
            <span>${requestScope.adminPageUser.email}</span><br>
            <span>First name:</span><br>
            <span>${requestScope.adminPageUser.firstName}</span><br>
            <span>Surname:</span><br>
            <span>${requestScope.adminPageUser.surName}</span><br>
            <span>Last in date:</span><br>
            <span>${requestScope.adminPageUser.lastInDate}</span><br>
        </div>
        <br>
        <c:if test="${not empty requestScope.adminPageUserReservations}">
            <div>
                <h3>User's reservations:</h3><br>
                <c:forEach var="reservation" items="${requestScope.adminPageUserReservations}">
                    <div>
                        <span>Reservation number:</span><br>
                        <span>${reservation.id}</span><br>
                        <span>Reservation in date:</span><br>
                        <span>${reservation.inDate}</span><br>
                        <span>Reservation out date:</span><br>
                        <span>${reservation.outDate}</span><br>
                        <span>Reservation creation time:</span><br>
                        <span>${reservation.creationTime}</span><br>
                        <span>Reservation book status:</span><br>
                        <span>${reservation.bookStatus}</span><br>
                        <span>Room number:</span><br>
                        <span>${reservation.room.roomNumber}</span><br>
                        <span>Room type:</span><br>
                        <span>${reservation.room.roomType.typeName}</span><br>
                        <c:if test="${not empty reservation.bill}">
                            Reservation's bill:<br>
                            <span>Total amount:</span><br>
                            <span>${reservation.bill.totalAmount}</span><br>
                            <span>Is paid:</span><br>
                            <span>${reservation.bill.paid}</span><br>
                        </c:if>
                        <h1>------------------------------------</h1>
                    </div>
                </c:forEach>
            </div>
        </c:if>
    </div>
</c:if>
<%--If reservations by period was found--%>
<c:if test="${not empty requestScope.reservationsByPeriod}">
    <div>
        Found reservations in period from ${requestScope.fromDate} to ${requestScope.beforeDate}:<br>
        <c:forEach var="reservation" items="${requestScope.reservationsByPeriod}">
            <div>
                <span>Reservation id:</span><br>
                <span>${reservation.id}</span><br>
                <span>Reservation in date:</span><br>
                <span>${reservation.inDate}</span><br>
                <span>Reservation outDate:</span><br>
                <span>${reservation.outDate}</span><br>
                <span>Reservation room number:</span><br>
                <span>${reservation.room.roomNumber}</span><br>
                <span>Reservation room type:</span><br>
                <span>${reservation.room.roomType.typeName}</span><br>
                <span>Reservation user email:</span><br>
                <span>${reservation.user.email}</span><br>
                <span>Reservation book status:</span><br>
                <span>${reservation.bookStatus}</span><br>
                <c:if test="${not empty reservation.bill}">
                    Reservation's bill:<br>
                    <span>Total amount:</span><br>
                    <span>${reservation.bill.totalAmount}</span><br>
                    <span>Is paid:</span><br>
                    <span>${reservation.bill.paid}</span><br>
                </c:if>
                <h3>-----------------------------------------------</h3>
            </div>
        </c:forEach>
    </div>
</c:if>
</body>
</html>
