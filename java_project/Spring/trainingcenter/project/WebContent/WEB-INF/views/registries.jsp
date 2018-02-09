<%@ page pageEncoding="utf-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<base src="/TrainingCenter/" />
<meta charset="utf-8">
<title>Quản lý đăng ký</title>
</head>
<body>
	<h1>SỔ ĐĂNG KÝ</h1>	
	<form:form action="registries.controller" modelAttribute="registry">
		<div>
			<div>Registry id</div>
			<form:input path="registryID" />
		</div>
		<div>
			<div>Class id</div>
			<form:input path="classID" />
		</div>
		<div>
			<div>Learner id</div>
			<form:input path="learnerID" />
		</div>
		<div>
			<div>Registered day</div>			
			<form:input path="displayValue" value="${clazz.displayValue}" />		 			
		</div>
		<div>
			<div>Deposit</div>
			<form:input path="deposit" />
		</div>
		<div>
			<div>Fee status</div>
			<form:input path="feeStatusDisplay" />
		</div>
		<div>		
			<button formaction="insertregistries.controller">Insert</button>
			<button formaction="updateregistries.controller">Update</button>
			<button formaction="deleteregistries.controller">Delete</button>
			<button formaction="registries.controller">Refresh</button>
		</div>
	</form:form>
	<h2>${message}</h2>
	<table border="1" > 
		<tr>
			<th>Registry id</th>
			<th>Class id</th>
			<th>Learner id</th>			
			<th>Registered day</th>
			<th>Deposit</th>
			<th>Fee status</th>
			<th></th>
		</tr>
		<c:forEach var="li" items="${listregistry}">
			<tr>
				<td>${li.registryID}</td>
				<td>${li.classID}</td>
				<td>${li.learnerID}</td>				
				<td>${li.displayValue}</td>											
				<td>${li.deposit}</td>
				<td>${li.feeStatusDisplay}</td>							
				<td>
					<a href="editregistry.controller?id=${li.registryID}">Edit</a>
				</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>