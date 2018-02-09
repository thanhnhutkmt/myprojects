<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<base href="/SpringMVC2/">
	<meta charset="utf-8">
	<title>Customer Page</title>
</head>
<body>
	<h1>CUSTOMER</h1>
	<label>${message}</label>
	<form action="customer/index.php" method="post">
		<div>
			<label>Id</label>
			<input name="id" value="${user.id}">
		</div>
		<div>
			<label>Fullname</label>
			<input name="fullname" value="${user.fullname}">
		</div>
		<div>
			<label>Password</label>
			<input type="password" name="password" value="${user.password}">
		</div>
		<div>
			<label>Email</label>
			<input name="email" value="${user.email}">
		</div>
		<div>
			<label>Photo</label>
			<input name="photo" value="${user.photo}">
		</div>
		<div>
			<label>Activated?</label>
			<input type="radio" name="activated" value="true" ${user.activated?'checked':''}>Male</input>
			<input type="radio" name="activated" value="false" ${user.activated?'':'checked'}>Female</input>
		</div>
		<div>
			<button formaction="customer/insert.php">Insert</button>
			<button formaction="customer/update.php">Update</button>
			<button formaction="customer/delete.php">Delete</button>
			<button formaction="customer/index.php">Reset</button>
		</div>
	</form>
	<ul>
		<c:forEach var="v" items="${list}">
			<li>${v.fullname}
			<a href="customer/edit/${v.id}.php">Edit</a>
			</li>
		</c:forEach>
	</ul>
</body>
</html>
