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
	
	<form action="login.php" method="post">
		<div>
			<div>Username</div>
			<input name="id">
		</div>
		<div>
			<div>Password</div>
			<input name="password">
		</div>
		<button name="cach1">Cách 1</button>
		<button name="cach2">Cách 2</button>
		<button name="cach3">Cách 3</button>
	</form>
</body>
</html>