<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="mytag" uri="selfMadeTag" %>
<html>
<head>
    <fmt:setLocale value="${sessionScope.localization}"/>
    <fmt:setBundle basename="local" var="loc"/>
    <fmt:message bundle="${loc}" key="local.reservationpage.button.eng" var="eng_button"/>
    <fmt:message bundle="${loc}" key="local.reservationpage.button.rus" var="rus_button"/>
    <fmt:message bundle="${loc}" key="local.reservationpage.string.reservation_page" var="reservation_page"/>
    <fmt:message bundle="${loc}" key="local.reservationpage.ref.to_main_page" var="to_main_page"/>
    <fmt:message bundle="${loc}" key="local.reservationpage.string.in_date" var="in_date"/>
    <fmt:message bundle="${loc}" key="local.reservationpage.string.out_date" var="out_date"/>
    <fmt:message bundle="${loc}" key="local.reservationpage.string.room_type" var="room_type"/>
    <fmt:message bundle="${loc}" key="local.reservationpage.button.search" var="search_rooms"/>
    <fmt:message bundle="${loc}" key="local.reservationpage.string.no_suitable_rooms" var="no_suitable_rooms"/>
    <fmt:message bundle="${loc}" key="local.reservationpage.string.available_rooms" var="available_rooms"/>
    <fmt:message bundle="${loc}" key="local.reservationpage.button.reserve_room" var="reserve_room"/>
    <fmt:message bundle="${loc}" key="local.reservationpage.string.room_number" var="room_number"/>
    <fmt:message bundle="${loc}" key="local.reservationpage.string.persons" var="persons"/>
    <fmt:message bundle="${loc}" key="local.reservationpage.string.cost_per_day" var="cost_per_day"/>
    <fmt:message bundle="${loc}" key="local.reservationpage.string.room_type_name" var="room_type_name"/>
    <fmt:message bundle="${loc}" key="local.reservationpage.string.room_description" var="room_description"/>
    <title>Room reservation page</title>
    <style>
        .errorText {
            color: red;
            font-size: small;
        }
    </style>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body class="body">
<div class="main_content">
    <header>
        <div class="lang_buttons">
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
        </div>
        <div class="welcome_to_page">
            <h2>${reservation_page}</h2>
        </div>
        <div class="references">
            <a href="mainController?command=main_page">${to_main_page}</a>
        </div>
    </header>
    <div class="horizontal_center">
        <form action="mainController" method="get" class="center_form">
            <input type="hidden" name="command" value="find_suitable_rooms">
            <div class="errorText">${requestScope.dateError}</div>
            <div>
                ${in_date}
            </div>
            <div>
                <input type="date" name="inDate" required value="${requestScope.inDate}">
            </div>
            <div>
                ${out_date}
            </div>
            <div>
                <input type="date" name="outDate" required value="${requestScope.outDate}">
            </div>
            <div>
                ${room_type}
            </div>
            <div>
                <select name="roomType">
                    <option>Any type</option>
                    <c:forEach items="${sessionScope.roomTypes}" var="roomType">
                        <option>${roomType.typeName}</option>
                    </c:forEach>
                </select>
            </div>
            <div>
                <input type="submit" value="${search_rooms}">
            </div>
        </form>
        <div>
            <div class="center_form" style="font-size: 14px">
                <c:if test="${requestScope.foundRooms.size() == 0}">
                    ${no_suitable_rooms}
                </c:if>
                <c:if test="${requestScope.foundRooms.size() > 0}">
                    ${available_rooms}
                </c:if>
            </div>
            <ul>
                <c:forEach items="${requestScope.foundRooms}" var="room">
                    <li>
                        <div>${room_number}<br>
                                ${room.roomNumber}</div>
                        <div>${persons}<br>
                                ${room.persons}</div>
                        <div>${cost_per_day}<br>
                                ${room.dayCost}</div>
                        <div>${room_type_name}<br>
                                ${room.roomType.typeName}</div>
                        <div>${room_description}<br>
                            <c:choose>
                                <c:when test="${sessionScope.localization.equals('ru')}">
                                    ${room.roomType.descriptionRus}
                                </c:when>
                                <c:otherwise>
                                    ${room.roomType.descriptionEng}
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div>
                            <div>
                                <c:if test="${not empty room.picturePath}">
                                    <img src="${room.picturePath}" width="150" height="150">
                                </c:if>
                            </div>
                            <div><br>
                                <form action="mainController" method="post">
                                    <input type="hidden" name="command" value="reservation_confirm_page">
                                    <input type="hidden" name="roomId" value="${room.id}">
                                    <input type="hidden" name="roomTypeId" value="${room.roomType.id}">
                                    <input type="hidden" name="inDate" value="${requestScope.inDate}">
                                    <input type="hidden" name="outDate" value="${requestScope.outDate}">
                                    <input type="submit" value="${reserve_room}">
                                </form>
                            </div>
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>
</div>
<mytag:madeByStepanov/>
</body>
</html>
