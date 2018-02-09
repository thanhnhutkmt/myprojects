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
	<form:form action="classes.controller" modelAttribute="favcourse">
		<div>
			<div>Favourite id</div>
			<form:input path="favid" />
		</div>
		<div>
			<div>Course id</div>
			<form:input path="courseid" />
		</div>
		<div>
			<div>Learner id</div>			
			<form:input path="learnerid" />		 			
		</div>
		<div>		
			<button formaction="insertfavcourse.controller">Insert</button>
			<button formaction="updateFavcourses.controller">Update</button>
			<button formaction="deleteFavcourses.controller">Delete</button>
			<button formaction="favcourses.controller">Refresh</button>
		</div>
	</form:form>
	<h2>${message}</h2>
	<table border="1" > 
		<tr>
			<th>Favourite id</th>
			<th>Course id</th>
			<th>Learner id</th>
			<th></th>
		</tr>
		<c:forEach var="li" items="${listfavcourse}">
			<tr>
				<td>${li.favid}</td>
				<td>${li.courseid}</td>
				<td>${li.learnerid}</td>							
				<td>
					<a href="editFavcourses.controller?id=${li.favid}">Edit</a>
				</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>