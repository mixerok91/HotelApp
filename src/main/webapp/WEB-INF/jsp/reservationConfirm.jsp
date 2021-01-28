<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Reservation confirm</title>
</head>
<body>
    Reservation confirm
    <h3>${sessionScope.get("selectedReservation")}</h3>
    <form action="reservationController" name="post">
        <input type="hidden" name="command" value="reservation_confirm">
        <input type="submit" value="Confirm reservation">
    </form>
    <a href="reservationController?command=reservation_page">To room select</a><br>
    <a href="mainController?command=main_page">To main page</a>
</body>
</html>
