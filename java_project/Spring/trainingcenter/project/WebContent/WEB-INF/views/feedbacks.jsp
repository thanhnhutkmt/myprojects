<%@ page pageEncoding="utf-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<base src="/TrainingCenter/" />
<meta charset="utf-8">
<title>Quản lý phản hồi</title>
</head>
<body>
	<h1>PHẢN HỒI</h1>
	<form:form action="feedbacks.controller" modelAttribute="feedback">
		<div>
			<div>Feed back ID</div>
			<form:input path="feedbackID" />
		</div>
		<div>
			<div>Full name</div>
			<form:input path="fullName" />
		</div>
		<div>
			<div>Phone</div>
			<form:input path="phone" />
		</div>
		<div>
			<div>Email</div>
			<form:input path="email" />
		</div>
		<div>
			<div>Title</div>
			<form:input path="title" />
		</div>
		<div>
			<div>Content</div>
			<form:textarea path="content" />
		</div>
		<div>
			<div>Submitted date</div>			
			<form:input path="submittedDateDisplay" value="${feedback.submittedDateDisplay}" />		 			
		</div>
		<div>		
			<button formaction="insertfeedback.controller">Insert</button>
			<button formaction="updatefeedback.controller">Update</button>
			<button formaction="deletefeedback.controller">Delete</button>
			<button formaction="feedbacks.controller">Refresh</button>
		</div>
	</form:form>
	<h2>${message}</h2>
	<table border="1" > 
		<tr>
			<th>Feed back ID</th>
			<th>Full name</th>
			<th>Phone</th>			
			<th>Email</th>
			<th>Title</th>
			<th>Content</th>
			<th>Submitted date</th>
			<th></th>
		</tr>
		<c:forEach var="li" items="${listfeedback}">
			<tr>
				<td>${li.feedbackID}</td>
				<td>${li.fullName}</td>
				<td>${li.phone}</td>											
				<td>${li.email}</td>
				<td>${li.title}</td>											
				<td>${li.content}</td>
				<td>${li.submittedDateDisplay}</td>											
				<td>
					<a href="editfeedback.controller?id=${li.feedbackID}">Edit</a>
				</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>