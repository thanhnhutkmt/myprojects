<%@ page pageEncoding="utf-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="s" %>
<!DOCTYPE html>
<html>
<head>
	<base href="/SpringMVC4/">
	<meta charset="utf-8">
	<title>Insert title here</title>
	<script src="js/jquery-1.10.2.min.js"></script>
	<script src="js/jquery.validate.min.js"></script>
	
	<script>
		$(function(){
			$("#stu").validate({
				rules:{
					name:{minlength:5},
					age:{range:[16,65]},
					email:{email:true, required:true}
				},
				messages:{
					name:{minlength:'<s:message code="Length.stu.name"/>'},
					age:{range:'<s:message code="Range.stu.age"/>'},
					email:{
						email:'<s:message code="Email.stu.email"/>', 
						required:'<s:message code="NotEmpty.stu.email"/>'
					}
				}
			});
		});
	</script>
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