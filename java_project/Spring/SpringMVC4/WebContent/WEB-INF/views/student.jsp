<%@ page pageEncoding="utf-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>Insert title here</title>
</head>
<body>
	${message}
	<form:form action="insert.php" modelAttribute="stu">
		<div>
			<div>Họ và tên</div>
			<form:input path="name"/>
			<form:errors path="name"/>
		</div>
		<div>
			<div>Tuổi</div>
			<form:input path="age"/>
			<form:errors path="age"/>
		</div>
		<div>
			<div>Email</div>
			<form:input path="email"/>
			<form:errors path="email"/>
		</div>
		<div>
			<button>Insert</button>
		</div>
	</form:form>
</body>
</html>