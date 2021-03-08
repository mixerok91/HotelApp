<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Room type administration</title>
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
    <span>Room type administration page</span>
    <div>
        ${param.get("message")}
    </div>
    <a href="mainController?command=main_page">To main page</a>
    <a href="mainController?command=admin_cabinet_page">To admin page</a><br>
    <div>
        <div>
            <form action="mainController" method="post">
                <input type="hidden" name="command" value="create_room_type">
                <input type="text" name="typeName" value="Insert room type name" required>
                <input type="text" name="russianDescription" value="Insert room type description in Russian" required>
                <input type="text" name="englishDescription" value="Insert room type description in English" required>
                <input type="submit" value="Create new room type">
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
                        <input type="submit" value="Edit room type">
                    </form>
                </div>
            </div>
        </c:forEach>
    </div>
</body>
</html>
