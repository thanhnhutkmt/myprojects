<%@ page pageEncoding="utf-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<base src="/TrainingCenter/" />
<meta charset="utf-8">
<title>Quản lý học viên</title>
</head>
<body>
	<h1>HỌC VIÊN</h1>	
	<form:form action="learners.controller" modelAttribute="learner">
		<table border="2">		
			<tr>					
				<c:if test="${learner.image ne null and learner.image ne ''}">
				 	<img src="images/${learner.learnerId}.jpg" />
				</c:if>
				<c:if test="${learner.image eq null or learner.image eq ''}">
					<c:if test="${learner.sex eq true}">
					 	<img src="images/male_default.jpg" />
					</c:if>
					<c:if test="${learner.sex eq false}">
						<img src="images/female_default.jpg" />
					</c:if>
				</c:if>							
			</tr>
			<tr>	
				<td>Avatar</td><td> <form:input path="image" /></td>
				<td>Learner ID</td> <td><form:input path="learnerId" /></td>
			</tr>					
			<tr>				
				<td>Last name</td> <td><form:input path="lastName" /></td>
				<td>Middle name</td> <td><form:input path="middleName" /></td>
				<td>First name</td> <td><form:input path="firstName" /></td>				 		
			</tr>
			<tr>
				<td>Birthday</td><td><form:input path="birthDateString" value="${learner.birthDateString}" /></td>				
				<td>Sex</td><td><form:input path="sexDisplay" /></td>
				<td>Address</td><td><form:input path="address" /></td>	
			</tr>
			<tr>		 									
				<td>Email 1</td><td><form:input path="email1" /></td>
				<td>Email 2</td><td><form:input path="email2" /></td>
				<td>Email 3</td><td><form:input path="email3" /></td>
			</tr>
			<tr>
				<td>Mobile</td>      <td><form:input path="mobile" /></td>
				<td>Home phone</td>  <td><form:input path="homePhone" /></td>
				<td>Work phone</td>  <td><form:input path="workPhone" /></td>
			</tr>
			<tr>
				<td>ID card</td><td><form:input path="iDcard" /></td>
				<td>ID passport</td><td><form:input path="iDpassport" /></td>
				<td>Created date</td><td><form:input path="createdDateString" value="${learner.createdDateString}" /></td>			
			</tr>			
			<tr>
				<td>Notes</td><td colspan="5"><form:textarea path="notes" rows="3" cols="76"/></td>
			</tr>
			<tr>
				<td colspan="6" align="center">			
					<button formaction="insertlearner.controller">Insert</button>
					<button formaction="updatelearner.controller">Update</button>
					<button formaction="deletelearner.controller">Delete</button>
					<button formaction="learners.controller">Refresh</button>
				</td>
			</tr>
		</table>
	</form:form>
	<h2>${message}</h2>
	<table border="1" style="width:100%"> 
		<tr>
			<th>Learner id</th>
			<th>Avatar</th>			
			<th>Last name</th>
			<th>Middle name</th>
			<th>First name</th>		
			<th>Birthday</th>	
			<th>Created date</th>					
			<th>Address</th>
			<th>Sex</th>
			<th>Email 1</th>
			<th>Email 2</th>
			<th>Email 3</th>
			<th>Mobile</th>
			<th>Home phone</th>
			<th>Work phone</th>
			<th>ID card</th>
			<th>ID passport</th>
			<th>Notes</th>
			<th></th>
		</tr>
		<c:forEach var="li" items="${listlearner}">
			<tr>				
				<td>${li.learnerId}</td>
				<td align="center">						
					<c:if test="${li.image ne null and li.image ne ''}">
					 	<img src="images/${li.learnerId}.jpg" />
					</c:if>
					<c:if test="${li.image eq null or li.image eq ''}">
						<c:if test="${li.sex eq true}">
						 	<img src="images/male_default.jpg" />
						</c:if>
						<c:if test="${li.sex eq false}">
							<img src="images/female_default.jpg" />
						</c:if>
					</c:if>	
				</td>
				<td>${li.lastName}</td>
				<td>${li.middleName}</td>
				<td>${li.firstName}</td>
				<td>${li.birthDateString}</td>
				<td>${li.createdDateString}</td>
				<td>${li.address}</td>
				<td>
					<c:choose>
						<c:when test="${li.sex eq true}">
							<c:set var="sexstring" value="Nam"></c:set>
						</c:when>
						<c:when test="${li.sex ne true}">
							<c:set var="sexstring" value="Nữ"></c:set>
						</c:when>
					</c:choose>
					${sexstring}
				</td>
				<td>${li.email1}</td>
				<td>${li.email2}</td>
				<td>${li.email3}</td>
				<td>${li.mobile}</td>
				<td>${li.homePhone}</td>
				<td>${li.workPhone}</td>
				<td>${li.iDcard}</td>
				<td>${li.iDpassport}</td>
				<td>${li.notes}</td>
				<td>
					<a href="editlearner.controller?id=${li.learnerId}">Edit</a>
				</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>