<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Rooms administration</title>
</head>
<body>
<span>Rooms administration</span>
<a href="mainController?command=main_page">To main page</a>
<a href="adminController?command=admin_cabinet_page">To admin page</a><br>

<div>
    ${param.get("message")}
</div>
<%--Create new room--%>
<div>
    <div>
        <span>Create new room:</span>
        <form action="adminController" method="post" enctype="multipart/form-data">
            <input type="hidden" name="command" value="create_room" required>
            <span>Input room name:</span><br>
            <input type="text" name="roomNumber" value="Input room number" required><br>
            <span>Input persons:</span><br>
            <input type="number" name="personsNumber" value="Input number of persons" required><br>
            <span>Input cost per day in USD:</span><br>
            <input type="number" name="costPerDay" value="Input cost per day in USD" required><br>
            <span>Select room type:</span><br>
            <select name="roomType">
                <c:forEach items="${sessionScope.roomTypes}" var="roomType">
                    <option>${roomType.typeName}</option>
                </c:forEach>
            </select>
            <br>
            <span>Upload room picture:</span><br>
            <input type="file" name="picture" size="60"><br>
            <input type="submit" value="Create new room">
        </form>
    </div>
<%--    Edit exist rooms--%>
    <div>
        Edit rooms:<br>
        <c:forEach var="room" items="${sessionScope.rooms}">
            <div>
                <div>
                    <form action="adminController" method="post" enctype="multipart/form-data">
                        <input type="hidden" name="command" value="edit_room">
                        <input type="hidden" name="roomId" value="${room.id}">
                        <span>Input room name:</span><br>
                        <input type="text" name="roomNumber" value="${room.roomNumber}" required><br>
                        <span>Input persons:</span><br>
                        <input type="number" name="personsNumber" value="${room.persons}" required><br>
                        <span>Input cost per day in USD:</span><br>
                        <input type="number" name="costPerDay" value="${room.dayCost}" required><br>
                        <span>Select room type:</span><br>
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
                            <span>Has picture.</span><br>
                            ${room.picturePath}<br>
                            <input type="hidden" name="picturePath" value="${room.picturePath}">
                        </c:if>
                        <span>Upload room picture:</span><br>
                        <input type="file" name="picture" size="60"><br>
                        <input type="submit" value="Edit room">
                    </form>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
</body>
</html>
