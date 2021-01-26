<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<h1>Login page</h1>

<h2 style="color:red">${message}</h2>

<form method="post" action="${pageContext.request.contextPath}/login">
    Enter login: <input type="text" name="login" required>
    Enter password: <input type="password" name="password" required>
    <button type="submit">Login</button>
</form>
<a href="${pageContext.request.contextPath}/drivers/create">Registration</a>
</body>
</html>
