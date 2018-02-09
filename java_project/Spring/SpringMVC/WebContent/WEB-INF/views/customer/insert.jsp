<%@ page pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
</head>
<body>
	<h1>Login Spring MVC</h1>
	<h4>${message}</h4>
	<h4>${sessionScope.x}</h4>
	<h4>${applicationScope.x}</h4>
	
	<form action="insert.php">
		<button name="a">Insert A</button>
		<button name="b">Insert B</button>
		<button name="c">Insert C</button>
	</form>
</body>
</html>