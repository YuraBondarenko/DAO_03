<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Taxi Service</title>
</head>
<body>
<h1>Welcome to taxi service</h1>
<a href="${pageContext.request.contextPath}/cars">Show all cars into db</a>
<p></p>
<a href="${pageContext.request.contextPath}/cars/create">Add new car into db</a>
<p></p>
<a href="${pageContext.request.contextPath}/drivers">Show all drivers into db</a>
<p></p>
<a href="${pageContext.request.contextPath}/drivers/create">Add new driver into db</a>
<p></p>
<a href="${pageContext.request.contextPath}/manufacturers">Show all manufacturers into db</a>
<p></p>
<a href="${pageContext.request.contextPath}/manufacturers/create">Add new manufacturer into db</a>
<p></p>
<a href="${pageContext.request.contextPath}/cars/drivers/add">Add driver to car</a>
</body>
</html>
