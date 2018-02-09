<%@ page pageEncoding="utf-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
</head>
<body>
	<h1>PRODUCT</h1>
	<h2>${message}</h2>
	<form:form action="product/index.php" modelAttribute="model">
		<div>
			<div>Id</div>
			<form:input path="id" />
		</div>
		<div>
			<div>Name</div>
			<form:input path="name" />
		</div>
		<div>
			<div>Unit price</div>
			<form:input path="unitPrice" />
		</div>
		<div>
			<div>Quantity</div>
			<form:input path="quantity" />
		</div>
		<div>
			<div>Available</div>
			<form:radiobutton path="available" value="true"/>Male
			<form:radiobutton path="available" value="false"/>Female
		</div>	
		<div>
			<div>Image</div>
			<form:input path="image" />
		</div>
		<div>
			<div>Product date</div>
			<form:input path="productDate" />
		</div>					
		<div>
			<div>Category</div>
			<form:select path="category.id"
				items="${cates}" itemValue="id" itemLabel="name" />			
		</div>
		<div>
			<div>Description</div>
			<form:textarea path="description" row="3"/>
		</div>
		<div>		
			<button formaction="insert.php">Insert</button>
			<button formaction="update.php">Update</button>
			<button formaction="delete.php">Delete</button>
			<button formaction="index.php">Reset</button>
		</div>
	</form:form>
	
	<table border="1" style="width:100%"> 
		<tr>
			<th>Name</th>
			<th>Unit price</th>
			<th>Category</th>
			<th>Available</th>
			<th></th>
		</tr>
		<c:forEach var="p" items="${prods}">
			<tr>
				<td>${p.name}</td>
				<td>${p.unitPrice}</td>
				<td>${p.category.nameVN}</td>
				<td>${p.available}</td>
				<td>
					<a href="edit.php?id=${p.id}">Edit</a>
				</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>