<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>Insert title here</title>
</head>
<body>
	<h2>PRODUCT LIST</h2>
	<ul>
		<c:forEach var="p" items="${list}">
			<li>${p.name}</li>
		</c:forEach>
	</ul>
</body>
</html>