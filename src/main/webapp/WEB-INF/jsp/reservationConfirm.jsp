<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Reservation confirm</title>
</head>
<body>
    Reservation confirm
    <h4>${sessionScope.selectedReservation}</h4><br>
    <h2>Total amount: ${sessionScope.selectedBill.totalAmount}</h2>
    <form action="reservationController" name="post">
        <input type="hidden" name="command" value="reservation_confirm">
        <input type="submit" value="Confirm reservation">
    </form>
    <form action="reservationController" name="get">
        <input type="hidden" name="command" value="reservation_undo">
        <input type="submit" value="Cancel reservation">
    </form>
    <a href="mainController?command=main_page">To main page</a>
</body>
</html>
