<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Rooms administration</title>
    <fmt:setLocale value="${sessionScope.localization}"/>
    <fmt:setBundle basename="local" var="loc"/>
    <fmt:message bundle="${loc}" key="local.roomadministrationpage.button.eng" var="eng_button"/>
    <fmt:message bundle="${loc}" key="local.roomadministrationpage.button.rus" var="rus_button"/>
    <fmt:message bundle="${loc}" key="local.roomadministrationpage.string.rooms_administration"
                 var="rooms_administration"/>
    <fmt:message bundle="${loc}" key="local.roomadministrationpage.ref.to_admin_page" var="to_admin_page"/>
    <fmt:message bundle="${loc}" key="local.roomadministrationpage.ref.to_main_page" var="to_main_page"/>
    <fmt:message bundle="${loc}" key="local.roomadministrationpage.string.create_new_room" var="create_new_room"/>
    <fmt:message bundle="${loc}" key="local.roomadministrationpage.string.enter_room_name" var="enter_room_name"/>
    <fmt:message bundle="${loc}" key="local.roomadministrationpage.string.enter_quantity_of_persons" var="enter_quantity_persons"/>
    <fmt:message bundle="${loc}" key="local.roomadministrationpage.string.enter_price_per_day" var="enter_price_per_day"/>
    <fmt:message bundle="${loc}" key="local.roomadministrationpage.string.select_room_type" var="select_room_type"/>
    <fmt:message bundle="${loc}" key="local.roomadministrationpage.string.upload_room_picture" var="upload_room_picture"/>
    <fmt:message bundle="${loc}" key="local.roomadministrationpage.button.create_new_room" var="create_new_room_btn"/>
    <fmt:message bundle="${loc}" key="local.roomadministrationpage.string.edit_rooms" var="edit_rooms"/>
    <fmt:message bundle="${loc}" key="local.roomadministrationpage.string.has_picture" var="has_picture"/>
    <fmt:message bundle="${loc}" key="local.roomadministrationpage.button.edit_room" var="edit_room_btn"/>
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
    <div>${rooms_administration}</div>
    <div>
        <a href="mainController?command=main_page">${to_main_page}</a><br>
        <a href="mainController?command=admin_cabinet_page">${to_admin_page}</a>
    </div>

    <div>
        ${param.get("message")}
    </div>
    <%--Create new room--%>
    <div>
        <div>
            <span>${create_new_room}</span>
            <form action="mainController" method="post" enctype="multipart/form-data">
                <input type="hidden" name="command" value="create_room">
                <span>${enter_room_name}</span><br>
                <input type="text" name="roomNumber" value="Input room number" required><br>
                <span>${enter_quantity_persons}</span><br>
                <input type="number" name="personsNumber" value="Input number of persons" required><br>
                <span>${enter_price_per_day}</span><br>
                <input type="number" name="costPerDay" value="Input cost per day in USD" required><br>
                <span>${select_room_type}</span><br>
                <select name="roomType">
                    <c:forEach items="${sessionScope.roomTypes}" var="roomType">
                        <option>${roomType.typeName}</option>
                    </c:forEach>
                </select>
                <br>
                <span>${upload_room_picture}</span><br>
                <input type="file" name="picture" size="60"><br>
                <input type="submit" value="${create_new_room_btn}">
            </form>
        </div>
        <%--    Edit exist rooms--%>
        <div>
            ${edit_rooms}<br>
            <c:forEach var="room" items="${sessionScope.rooms}">
                <div>
                    <div>
                        <form action="mainController" method="post" enctype="multipart/form-data">
                            <input type="hidden" name="command" value="edit_room">
                            <input type="hidden" name="roomId" value="${room.id}">
                            <span>${enter_room_name}</span><br>
                            <input type="text" name="roomNumber" value="${room.roomNumber}" required><br>
                            <span>${enter_quantity_persons}</span><br>
                            <input type="number" name="personsNumber" value="${room.persons}" required><br>
                            <span>${enter_price_per_day}</span><br>
                            <input type="number" name="costPerDay" value="${room.dayCost}" required><br>
                            <span>${select_room_type}</span><br>
                            <select name="roomType">
                                <option>${room.roomType.typeName}</option>
                                <c:forEach items="${sessionScope.roomTypes}" var="roomType">
                                    <c:if test="${room.roomType.typeName != roomType.typeName}">
                                        <option>${roomType.typeName}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                            <br>
                            <c:if test="${not empty room.picturePath}">
                                <span>${has_picture}</span><br>
                                ${room.picturePath}<br>
                                <input type="hidden" name="picturePath" value="${room.picturePath}">
                            </c:if>
                            <span>${upload_room_picture}</span><br>
                            <input type="file" name="picture" size="60"><br>
                            <input type="submit" value="${edit_room_btn}">
                        </form>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</div>
</body>
</html>
