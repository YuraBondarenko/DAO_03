<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add driver to car</title>
</head>
<body>
<h1>Enter data for adding driver to car</h1>

<form method="post" action="${pageContext.request.contextPath}/cars/drivers/add">
    Enter driver id: <input type="number" name="driverId" required>
    Enter car id: <input type="number" name="carId" required>
    <button type="submit">Add driver to car</button>
</form>
<a href="${pageContext.request.contextPath}/">Go to the main page</a>
<a href="${pageContext.request.contextPath}/cars">Show all cars</a>
<a href="${pageContext.request.contextPath}/drivers">Show all drivers</a>
</body>
</html>
