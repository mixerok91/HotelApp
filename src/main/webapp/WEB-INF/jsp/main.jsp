<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Main page</title>
</head>
<body>
<div>
<%--Если пустая страница, перезагрузить--%>
    <c:if test="${sessionScope.roomTypes == null}">
        <c:redirect url="/mainController?command=main_page"/>
    </c:if>

    <h1>Welcome to "Prancing Pony" Inn</h1>
    <div>Hello '${sessionScope.user.firstName}'</div>

<%--Ссылка на логинацию/регистрацию или логаут--%>
    <c:if test="${sessionScope.user == null}">
        <a href="userController?command=registration_page">To registration page</a><br>
        <a href="userController?command=login_page">To login page</a><br>
    </c:if>
    <c:if test="${sessionScope.user != null}">
        <a href="userController?command=logout">LogOut</a><br>
<%--В кабинет узера--%>
        <a href="reservationController?command=user_cabinet_page">To user cabinet</a><br>
        <%--В кабинет админа--%>
        <c:if test="${sessionScope.user.role.name().equals('ADMIN')}">
            <a href="adminController?command=admin_cabinet_page">To admin page</a><br>
        </c:if>
    </c:if>



<%--К бронированию номера--%>
    <a href="reservationController?command=reservation_page">To room reservation</a>
<%--Отображение типов номеров--%>
    <br>
    <div><h3>Description of room types:</h3></div>
    <div>
        <c:forEach var="roomType" items="${sessionScope.roomTypes}">
            <div>
                ${roomType}
            </div><br>
        </c:forEach>
    </div>

</div>
</body>
</html>
