<%@ page pageEncoding="utf-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<base src="/TrainingCenter/" />
<meta charset="utf-8">
<title>Courses management</title>
</head>
<body>
	<h1>COURSES</h1>
	<h2>${message}</h2>
	<form:form action="test/index.php" modelAttribute="model">
		<div>
			<div>Id</div>
			<form:input path="courseid" />
		</div>
		<div>
			<div>Title</div>
			<form:input path="title" />
		</div>
		<div>
			<div>CreateDate</div>
			<form:input path="createDate" />
		</div>
		<div>
			<div>GroupOfCourse</div>
			<form:input path="groupOfCourse" />
		</div>
		<div>
			<div>Description</div>
			<form:textarea path="description" row="3"/>
		</div>
		<div>
			<div>Fee</div>
			<form:input path="fee" />
		</div>
		<div>		
			<button formaction="insert.php">Insert</button>
			<button formaction="update.php">Update</button>
			<button formaction="delete.php">Delete</button>
			<button formaction="index.php">Reset</button>
		</div>
	</form:form>
	
	<table border="1" style="width:100%"> 
		<tr>
			<th>Course id</th>
			<th>Title</th>
			<th>Created date</th>
			<th>Group of Course</th>			
			<th>Fee</th>			
			<th>Duration</th>
			<th>Description</th>
			<th></th>
		</tr>
		<c:forEach var="l" items="${listcourse}">
			<tr>
				<td>${l.courseid}</td>
				<td>${l.title}</td>
				<td>${l.createDate}</td>
				<td>${l.groupOfCourse}</td>
				<td>${l.fee}</td>
				<td>${l.duration}</td>
				<td>${l.description}</td>
				<td>
					<a href="edit.php?id=${l.id}">Edit</a>
				</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>