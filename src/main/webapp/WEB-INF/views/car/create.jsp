<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Enter data for adding new car</h1>

<h2 style="color:red">${message}</h2>

<form method="post" action="${pageContext.request.contextPath}/cars/create">
    Enter model: <input type="text" name="model">
    Enter manufacturer id: <input type="text" name="manufacturerId">

    <button type="submit">Add car</button>
</form>
<a href="${pageContext.request.contextPath}/">Go to the main page</a>
</body>
</html>
