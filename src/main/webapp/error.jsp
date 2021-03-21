<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Error</title>
</head>
<body>
<h1>ERROR</h1>
<c:if test="${requestScope.errorMessage != null }">
    <h3>${requestScope.errorMessage}</h3>
</c:if>
<a href="mainController?command=main_page">To main page</a>
</body>
</html>
