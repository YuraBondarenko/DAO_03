<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create car</title>
</head>
<body>
<h1>Enter data for adding new car</h1>

<form method="post" action="${pageContext.request.contextPath}/cars/create">
    Enter model: <input type="text" name="model" required>
    Enter manufacturer id: <input type="number" name="manufacturerId" required>

    <button type="submit">Add car</button>
</form>
<a href="${pageContext.request.contextPath}/">Go to the main page</a>
</body>
</html>
