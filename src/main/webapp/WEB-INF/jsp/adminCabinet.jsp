<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Admin's cabinet</title>
</head>
<body>
<span>Admin's Cabinet.</span>
<a href="mainController?command=main_page">To main page</a>
<div>
    <form name="toRoomType" method="get" action="adminController">
        <input type="hidden" name="command" value="room_type_administration_page">
        <input type="submit" value="Administrating room types">
    </form>
</div>
<div>
    <form name="toRooms" method="get" action="adminController">
        <input type="hidden" name="command" value="room_administration_page">
        <input type="submit" value="Administrating rooms">
    </form>
</div>
<div>
    <form name="getUser" method="get" action="adminController">
        <input type="hidden" name="command" value="get_user">
        <input type="email" name="userEmail" required value="Type user email">
        <input type="submit" value="Get user's information">
    </form>
</div>
<div>
    <form name="getReservationsByPeriod" method="get" action="adminController">
        <input type="hidden" name="command" value="get_reservations_by_period">
        <input type="date" name="fromDate" required value="${requestScope.fromDate}">
        <input type="date" name="beforeDate" required value="${requestScope.beforeDate}">
        <input type="submit" value="Get reservations">
    </form>
</div>
</body>
</html>
