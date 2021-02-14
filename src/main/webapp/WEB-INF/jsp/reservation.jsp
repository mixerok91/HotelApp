<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Room reservation page</title>
    <style>
        .errorText {
            color: red;
            font-size: small;
        }
    </style>
</head>
<body>
<h1>Reservation page</h1>
<a href="mainController?command=main_page">To main page</a>
<div>
    <%-- Форма для поиска свободного номера на нужные даты--%>
    <form action="reservationController" method="get">
        <input type="hidden" name="command" value="find_suitable_rooms">
        <div class="errorText">${requestScope.dateError}</div>
        <div>In date:</div>
        <div><input type="date" name="inDate" required value="${requestScope.inDate}"></div>
        <div>Out date:</div>
        <div><input type="date" name="outDate" required value="${requestScope.outDate}"></div>
        <div>Room type:</div>
        <div>
            <select name="roomType">
                <option>Any type</option>
                <c:forEach items="${requestScope.roomTypes}" var="roomType">
                    <option>${roomType.typeName}</option>
                </c:forEach>
            </select>
        </div>
        <div><input type="submit" value="Search suitable rooms"></div>
    </form>
    <%--Выбор свободного номера для бронирования--%>
    <div>
        <c:if test="${requestScope.foundRooms.size() == 0}">
            <div>Sorry, there are no available rooms on this date. Try other dates.</div>
        </c:if>
        <c:forEach items="${requestScope.foundRooms}" var="room">
            <div>${room}</div>
            <div>
                <c:if test="${not empty room.picturePath}">
                    <img src="${room.picturePath}" width="150" height="150">
                </c:if>
            </div>
            <div>
                <form action="reservationController" method="post">
                    <input type="hidden" name="command" value="reservation_confirm_page">
                    <input type="hidden" name="roomId" value="${room.id}">
                    <input type="hidden" name="roomTypeId" value="${room.roomType.id}">
                    <input type="hidden" name="inDate" value="${requestScope.inDate}">
                    <input type="hidden" name="outDate" value="${requestScope.outDate}">
                    <input type="submit" value="Reserve room">
                </form>
            </div>
        </c:forEach>
    </div>
</div>

</body>
</html>
