<%@ page pageEncoding="utf-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
</head>
<body>
	<h1>SINH VIÊN</h1>
	<form:form action="sinh-vien/index.php" modelAttribute="student">
		<div>
			<div>Id</div>
			<form:input path="id" />
		</div>
		<div>
			<div>Fullname</div>
			<form:input path="fullname" />
		</div>
		<div>
			<div>Marks</div>
			<form:input path="marks" />
		</div>
		<div>
			<div>Gender</div>
			<form:radiobutton path="gender" value="true" label="Male"/>
			<form:radiobutton path="gender" value="false" label="Female"/>
		</div>
		<div>
			<div>Class</div>
			<div>
			<form:select path="clazz">
				<form:option value="PT001">Ứng dụng phần mềm</form:option>
				<form:option value="PT002">Thiết kế web</form:option>
				<form:option value="PT003">Lập trình mobile</form:option>
			</form:select></div>
			<div><form:select path="clazz"
				items="${clazzs}" itemValue="id" itemLabel="name" /></div>
			<div><form:radiobuttons path="clazz"
				items="${clazzs}" itemValue="id" itemLabel="name" /></div>
		</div>
		<div>
			<form:button>Save</form:button>
		</div>
	</form:form>
</body>
</html>