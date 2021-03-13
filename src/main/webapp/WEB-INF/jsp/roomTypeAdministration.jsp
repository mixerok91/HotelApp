<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Room type administration</title>
    <fmt:setLocale value="${sessionScope.localization}"/>
    <fmt:setBundle basename="local" var="loc"/>
    <fmt:message bundle="${loc}" key="local.roomtypesadministrationpage.button.eng" var="eng_button"/>
    <fmt:message bundle="${loc}" key="local.roomtypesadministrationpage.button.rus" var="rus_button"/>
    <fmt:message bundle="${loc}" key="local.roomtypesadministrationpage.string.room_type_administration" var="room_types_administration"/>
    <fmt:message bundle="${loc}" key="local.roomtypesadministrationpage.ref.to_admin_page" var="to_admin_page"/>
    <fmt:message bundle="${loc}" key="local.roomtypesadministrationpage.ref.to_main_page" var="to_main_page"/>
    <fmt:message bundle="${loc}" key="local.roomtypesadministrationpage.string.enter_room_type_name" var="enter_room_type_name"/>
    <fmt:message bundle="${loc}" key="local.roomtypesadministrationpage.string.enter_room_type_description_rus" var="enter_rus_description"/>
    <fmt:message bundle="${loc}" key="local.roomtypesadministrationpage.string.enter_room_type_description_eng" var="enter_eng_description"/>
    <fmt:message bundle="${loc}" key="local.roomtypesadministrationpage.button.create_room_type" var="create_room_type"/>
    <fmt:message bundle="${loc}" key="local.roomtypesadministrationpage.button.edit_room_type" var="edit_room_type"/>
</head>
<body>
<c:if test="${sessionScope.user.role != 'ADMIN'}">
    <c:redirect url="/mainController?command=main_page"/>
</c:if>
<%--Смена локали--%>
<div>
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
<div>
    <div>${room_types_administration}</div>
    <div>
        <a href="mainController?command=main_page">${to_main_page}</a><br>
        <a href="mainController?command=admin_cabinet_page">${to_admin_page}</a>
    </div>
    <div>
        ${param.get("message")}
    </div>
    <div>
        <div>
            <form action="mainController" method="post">
                <input type="hidden" name="command" value="create_room_type">
                <input type="text" name="typeName" value="${enter_room_type_name}" required>
                <input type="text" name="russianDescription" value="${enter_rus_description}" required>
                <input type="text" name="englishDescription" value="${enter_eng_description}" required>
                <input type="submit" value="${create_room_type}">
            </form>
        </div>
        <c:forEach var="roomType" items="${sessionScope.roomTypes}">
            <div>
                <div>
                    <form action="mainController" method="post">
                        <input type="hidden" name="command" value="edit_room_type">
                        <input type="hidden" name="roomTypeId" value="${roomType.id}">
                        <input type="text" name="typeName" value="${roomType.typeName}" required>
                        <input type="text" name="russianDescription" value="${roomType.descriptionRus}" required>
                        <input type="text" name="englishDescription" value="${roomType.descriptionEng}" required>
                        <input type="submit" value="${edit_room_type}">
                    </form>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
</body>
</html>
