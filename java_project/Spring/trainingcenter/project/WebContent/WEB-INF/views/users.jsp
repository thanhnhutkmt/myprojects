<%@ page pageEncoding="utf-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<base src="/TrainingCenter/" />
<meta charset="utf-8">
<title>Quản lý người dùng</title>
</head>
<body>
	<h1>NGƯỜI DÙNG</h1>
	<form:form action="users.controller" modelAttribute="user">
		<div>
			<div>Username</div>
			<form:input path="userid" />
		</div>
		<div>
			<div>Password</div>
			<form:input path="pwd" />
		</div>
		<div>
			<div>Role</div>
			<form:input path="role" />
		</div>
		<div>
			<div>Email</div>
			<form:input path="email"/>
		</div>
		<div>
			<div>Created day</div>			
			<form:input path="createdDateDisplay" value="${user.createdDateDisplay}" />		 			
		</div>		
		<div>		
			<button formaction="insertusers.controller">Insert</button>
			<button formaction="updateusers.controller">Update</button>
			<button formaction="deleteusers.controller">Delete</button>
			<button formaction="users.controller">Refresh</button>
		</div>
	</form:form>
	<h2>${message}</h2>
	<table border="1" > 
		<tr>
			<th>Username</th>
			<th>Password</th>
			<th>Role</th>
			<th>Email</th>
			<th>Open day</th>	
			<th></th>
		</tr>
		<c:forEach var="li" items="${listuser}">
			<tr>
				<td>${li.userid}</td>
				<td>${li.pwd}</td>
				<td>${li.role}</td>				
				<td>${li.email}</td>											
				<td>${li.createdDateDisplay}</td>				
				<td>
					<a href="edituser.controller?id=${li.userid}">Edit</a>
				</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>