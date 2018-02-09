<!DOCTYPE html>
<html lang="en">
<head>
<title>Purchase Requests</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="myRequestList" scope="request" type="java.util.List" />
</head>
<body>
	<div class="container-fluid">
		<div class="page-header">
			<h1>Purchase Requests</h1>
		</div>
		<div class="row">
			<ul class="nav nav-tabs">
				<li role="presentation" class="active"><a href="#">Saved</a></li>
				<li role="presentation"><a href="#">Submitted</a></li>
				<li role="presentation"><a href="#">Approved</a></li>
			</ul>
		</div>
		<div class="row">
			<table class="table">
				<thead>
					<tr>
						<th>PO Number</th>
						<th>Date</th>
						<th>Justification</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${myRequestList}" var="request">
						<tr>
							<td>${request.poNumber}</td>
							<td>${request.date}</td>
							<td>${request.justification}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>