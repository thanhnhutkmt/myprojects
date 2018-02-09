<%@ page pageEncoding="utf-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
	<base href="/SpringMVC4/">
	<meta charset="utf-8">
	<title>Insert title here</title>
	<script src="js/jquery-1.10.2.min.js"></script>
	<script src="js/jquery.validate.min.js"></script>
	<script>
		$(function() {
			$("#stu").validate({
				rules:{
					name:{minlength:5},
					age:{range:[16,65]},
					email:{email:true, required:true}
				},
				messages:{
					name:{minlength:'Ít nhất 5 ký tự'},
					age:{range:'Phải từ 16 tới 65'},
					email:{
						email:'Không đúng dạng email', 
						required:'Không để trống'
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
		</div>
		<div>
			<div>Tuổi</div>
			<form:input path="age"/>			
		</div>
		<div>
			<div>Email</div>
			<form:input path="email"/>
		</div>
		<div>
			<button>Insert</button>
		</div>
	</form:form>
</body>
</html>