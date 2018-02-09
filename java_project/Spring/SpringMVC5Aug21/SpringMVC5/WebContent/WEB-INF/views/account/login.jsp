<%@ page pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
	<base href="/SpringMVC5/" />
	<meta charset="utf-8">
	<title>Login</title>
</head>
<body>
	<h3>LOGIN</h3>
	${message}
	<form action="account/login.php" method="post">
		<div>Username</div>
		<input name="id">
		<div>Password</div>
		<input name="password">
		<div><button>Login</button></div>
	</form>
</body>
</html>