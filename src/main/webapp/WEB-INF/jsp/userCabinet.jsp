<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>User Cabinet</title>
</head>
<body>
<div>
    User Cabinet.
<%--Показать все брони--%>
    <div>
        <form action="reservationController" method="get">
            <input type="hidden" name="command" value="show_all_users_reservations">
            <input type="submit" value="Show all reservations">
        </form>
    </div>
<%--Показать актуальные брони--%>
    <div>
        <form action="reservationController" method="get">
            <input type="hidden" name="command" value="user_cabinet_page">
            <input type="submit" value="Show actual reservations">
        </form>
    </div>
<%--Изменить данные пользователя--%>
    <div>
        <form action="reservationController" method="get">
            <input type="hidden" name="command" value="edit_user_data_page">
            <input type="submit" value="Edit data">
        </form>
    </div>
<%--Отображает брони--%>
    <div>
        <c:if test="${sessionScope.userReservations.size() > 0}">
            <div>User's reservations:</div>
            <c:forEach items="${sessionScope.userReservations}" var="reservation">
                <div>${reservation}</div>
                <br>
                <div>
                    <c:if test="${reservation.bookStatus.name() eq 'RESERVED'}">
                     <form action="reservationController" method="post">
                            <input type="hidden" name="command" value="reservation_payment">
                            <input type="hidden" name="reservationId" value="${reservation.id}">
                            <input type="submit" value="Pay for reservation">
                        </form>
                        <form action="reservationController" method="post">
                            <input type="hidden" name="command" value="reservation_cancel">
                            <input type="hidden" name="reservationId" value="${reservation.id}">
                            <input type="submit" value="Cancel for reservation">
                        </form> <br>
                    </c:if>
                </div>
            </c:forEach>
        </c:if>
    </div>
<a href="mainController?command=main_page">To main page</a>
</div>
</body>
</html>
