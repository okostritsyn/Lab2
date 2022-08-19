<%--
  Created by IntelliJ IDEA.
  User: Oleg
  Date: 16.08.2022
  Time: 1:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

<html>
<head>
    <title>Sorry we have an error!</title>
</head>
<body>
<h1>Error Page</h1>
<p>Application has encountered an error. Please contact support on ...</p>
<div>
    <p><c:out value="${url}"/></p>
    <c:forEach var="ste" items="${exception.stackTrace}">
        <p><c:out value="${ste}"/></p>
    </c:forEach>
</div>
</body>
</html>