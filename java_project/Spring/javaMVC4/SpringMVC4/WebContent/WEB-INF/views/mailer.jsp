<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
	<base href="/SpringMVC4/" >
	<meta charset="utf-8">
	<title>Insert title here</title>
</head>
<body>
	${thongbao}
	<form:form action="mailer/send.php" method="post"  
		modelAttribute="mail" enctype="multipart/form-data">
		<div>
			<div>FROM</div>
			<form:input path="from" />
		</div>
		<div>
			<div>To</div>
			<form:input path="to" />
		</div>
		<div>
			<div>Subject</div>
			<form:input path="subject" />
		</div>
		<div>
			<div>Body</div>
			<form:textarea path="body" rows="3"/>
		</div>
		<div>
			<div>ATTACHMENT</div>
			<input type="file" name="file" >
		</div>		
		<button>Send</button>
	</form:form>
</body>
</html>