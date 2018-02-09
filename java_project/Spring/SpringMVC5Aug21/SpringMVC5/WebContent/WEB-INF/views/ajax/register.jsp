<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
	<base href="/SpringMVC5/" />
	<meta charset="utf-8">
	<title>Register</title>
</head>
<body>
	<h3>Register</h3>
	${message}
	<form:form action="account/register.php" modelAttribute="user">
		<div>Username</div>
		<form:input path="id" />
		<div>Password</div>
		<form:input path="password" />
		<div>Fullname</div>
		<form:input path="fullname" />
		<div>Email Address</div>
		<form:input path="email" />
		<div>Photo</div>
		<form:input path="photo" />
		<div>Activated?</div>
		<form:radiobutton path="activated" value="true" label="Yes"/>
		<form:radiobutton path="activated" value="false" label="No"/>
		
		<div><button>Register</button></div>
	</form:form>
</body>
</html>