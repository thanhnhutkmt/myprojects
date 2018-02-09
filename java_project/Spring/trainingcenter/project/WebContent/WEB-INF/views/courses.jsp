<%@ page pageEncoding="utf-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<base src="/TrainingCenter/" />
<meta charset="utf-8">
<title>Quản lý khóa học</title>
</head>
<body>
	<h1>KHÓA HỌC</h1>	
	<form:form action="index.controller" modelAttribute="course">
		<div>
			<div>Course id</div>
			<form:input path="courseid" />
		</div>
		<div>
			<div>Title</div>
			<form:input path="title" />
		</div>
		<div>
<%-- 			<c:set var="cd" value="${course.createdDate}" />   --%>
<%-- 			<%	 
 				java.util.Date d = new java.util.Date((long)pageContext.getAttribute("cd"));
 				pageContext.setAttribute("d", d);
	 			%>			 --%>
<%-- 			<fmt:formatDate var="cds" value="${d}" pattern="HH:mm:ss EEE MMM dd yyyy" /> --%>
 			<div>Created date</div>			 
<%-- 			<form:input path="createdDate" value="${cds}" /> --%>
			<form:input path="displayValue" value="${course.displayValue}" />		 			
		</div>
		<div>
			<div>Group of course</div>
			<form:input path="groupOfCourse" />
		</div>
		<div>
			<div>Description</div>
			<form:textarea path="description" row="3"/>
		</div>
		<div>
			<fmt:formatNumber var="money" type="number" maxFractionDigits="3" value="${course.fee}" />
			<div>Fee <form:input path="fee" value="${money}"/> VNĐ</div>
		</div>
		<div>
			<div>Duration</div>
			<form:input path="duration"/>
		</div>
		<div>		
			<button formaction="insertcourse.controller">Insert</button>
			<button formaction="updatecourse.controller">Update</button>
			<button formaction="deletecourse.controller">Delete</button>
			<button formaction="courses.controller">Refresh</button>
		</div>
	</form:form>
	<h2>${message}</h2>
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
		<c:forEach var="li" items="${listcourse}">
			<tr>
				<td>${li.courseid}</td>
				<td>${li.title}</td>
				<c:set var="cd1" value="${li.createdDate}" />  
				<%pageContext.setAttribute("d1", new java.util.Date((long)pageContext.getAttribute("cd1")));%>			
				<fmt:formatDate var="cds1" value="${d1}" pattern="HH:mm EEE MMM dd yyyy" />
				<td>${cds1}</td>
				<td>${li.groupOfCourse}</td>
				<td><strong>VNĐ <fmt:formatNumber type="number" maxFractionDigits="3" value="${li.fee}" /></strong></td>							
				<td>${li.duration}</td>
				<td>${li.description}</td>
				<td>
					<a href="editcourse.controller?id=${li.courseid}">Edit</a>
				</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>