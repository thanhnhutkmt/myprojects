<%@ page pageEncoding="utf-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<base src="/TrainingCenter/" />
<meta charset="utf-8">
<title>Quản lý lớp học</title>
</head>
<body>
	<h1>LỚP HỌC</h1>
	<form:form action="classes.controller" modelAttribute="clazz">
		<div>
			<div>Class id</div>
			<form:input path="classid" />
		</div>
		<div>
			<div>Course id</div>
			<form:input path="courseid" />
		</div>
		<div>
			<div>Starting day</div>			
			<form:input path="displayValue" value="${clazz.displayValue}" />		 			
		</div>
		<div>
			<div>Time table</div>
			<form:input path="timeTable" />
		</div>
		<div>
			<div>Room</div>
			<form:input path="room"/>
		</div>
		<div>		
			<button formaction="insertclasses.controller">Insert</button>
			<button formaction="updateclasses.controller">Update</button>
			<button formaction="deleteclasses.controller">Delete</button>
			<button formaction="classes.controller">Refresh</button>
		</div>
	</form:form>
	<h2>${message}</h2>
	<table border="1" > 
		<tr>
			<th>Class id</th>
			<th>Course id</th>
			<th>Starting day</th>
			<th>Time table</th>
			<th>Room</th>
			<th></th>
		</tr>
		<c:forEach var="li" items="${listclass}">
			<tr>
				<td>${li.classid}</td>
				<td>${li.courseid}</td>
				<td>${li.displayValue}</td>				
				<td>${li.timeTable}</td>											
				<td>${li.room}</td>				
				<td>
					<a href="editclass.controller?id=${li.classid}">Edit</a>
				</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>