<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create manufacturer</title>
</head>
<body>
<h1>Enter data for adding new manufacturer</h1>

<h2 style="color:red">${message}</h2>

<form method="post" action="${pageContext.request.contextPath}/manufacturers/create">
    Enter name: <input type="text" name="name">
    Enter country: <input type="text" name="country">
    <button type="submit">Add manufacturer</button>
</form>
<a href="${pageContext.request.contextPath}/">Go to the main page</a>
</body>
</html>
