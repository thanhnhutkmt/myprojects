<%@ page pageEncoding="utf-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<base src="/TrainingCenter/" />
<meta charset="utf-8">
<title>Quản lý thi cử</title>
</head>
<body>
	<h1>KỲ THI</h1>	
	<form:form action="classes.controller" modelAttribute="exam">
		<div>
			<div>Exam id</div>
			<form:input path="examID" />
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
			<div>Examination day</div>			
			<form:input path="displayValue" value="${exam.displayValue}" />		 			
		</div>
		<div>
			<div>Mark</div>
			<form:input path="mark" />
		</div>
		<div>
			<div>Result type</div>
			<form:input path="resultType" />
		</div>
		<div>
			<div>Exam room</div>
			<form:input path="examRoom" />
		</div>				
		<div>
			<div>Result</div>
			<form:input path="resultDisplay"/>
		</div>
		<div>		
			<button formaction="insertexams.controller">Insert</button>
			<button formaction="updateexams.controller">Update</button>
			<button formaction="deleteexams.controller">Delete</button>
			<button formaction="exams.controller">Refresh</button>
		</div>
	</form:form>
	<h2>${message}</h2>
	<table border="1" > 
		<tr>
			<th>Exam id</th>
			<th>Class id</th>
			<th>Learner id</th>
			<th>Examination day</th>
			<th>Mark</th>
			<th>Result type</th>
			<th>Exam room</th>
			<th>Result</th>
			<th></th>
		</tr>
		<c:forEach var="li" items="${listexam}">
			<tr>
				<td>${li.examID}</td>
				<td>${li.classID}</td>
				<td>${li.learnerID}</td>				
				<td>${li.displayValue}</td>											
				<td>${li.mark}</td>
				<td>${li.resultType}</td>
				<td>${li.examRoom}</td>
				<td>${li.resultDisplay}</td>				
				<td>
					<a href="editexam.controller?id=${li.examID}">Edit</a>
				</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>