<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create driver</title>
</head>
<body>
<h1>Enter data for adding new driver</h1>

<h2 style="color:red">${message}</h2>

<form method="post" action="${pageContext.request.contextPath}/drivers/create">
    Enter name: <input type="text" name="name" required>
    Enter licence number: <input type="text" name="licenceNumber" required>
    Enter login: <input type="text" name="login" required>
    Enter password: <input type="password" name="password" required>
    <button type="submit">Add driver</button>
</form>
<a href="${pageContext.request.contextPath}/login">Authentication</a>
<p></p>
<a href="${pageContext.request.contextPath}/">Go to the main page</a>
<a href="${pageContext.request.contextPath}/manufacturers">Show all drivers</a>
</body>
</html>
