<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<base src="/TrainingCenter/" />
<meta charset="utf-8">
<title>Trung tâm Thunder - Lưu Thành Nhựt</title>
<meta http-equiv="cache-control" content="public" />
<meta name="geo.placename" content="Thành Phố Hồ Chí Minh" />
<!-- Stylesheets -->
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/revolution-slider.css" rel="stylesheet">
<link href="css/style.css" rel="stylesheet">
<!-- Responsive -->
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<link href="css/bootstrap-margin-padding.css" rel="stylesheet">
<link
	href="http://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css"
	rel="stylesheet">
<link href="css/responsive.css" rel="stylesheet">
<link href="css/huong-style.css" rel="stylesheet">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<style type="text/css">
.fancybox-margin {
	margin-right: 17px;
}

.contact-info {
	align: right;
	background-color: #252525
}
</style>


</head>
<body>
	<%@include file="includes/mainheader.jsp"%>
	<div class="contact-section" id="contact-section">
		<div class="auto-container">
			<div class="row clearfix">
				<!--Content Side-->
				<div class="col-md-12 col-sm-12 col-xs-12 column">
					<div class="sec-title">
						<table border="1" style="width:100%"> 
							<tr>								
								<th>Tên khóa học</th>								
								<th>Nhóm khóa học</th>			
								<th>Học phí</th>			
								<th>Thời gian</th>
								<th>Mô tả</th>		
								<th></th>						
							</tr>
							<c:forEach var="li" items="${listfavcourse}">
								<tr>
									<td>${li.title}</td>
									<td>${li.groupOfCourse}</td>
									<td><strong>VNĐ <fmt:formatNumber type="number" maxFractionDigits="3" value="${li.fee}" /></strong></td>							
									<td>${li.duration}</td>
									<td>${li.description}</td>
									<td><button  onclick="remove('${li.description}')" class="normal-btn theme-btn">Bỏ yêu thích</button></td>									
								</tr>
							</c:forEach>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="includes/mainfooter.jsp"%>
	
	<script>
		function remove(des) {
			var description = des;
			$.ajax({
				url:"removefavcourse.controller",
				data:{description:description},
				success:function(response) { 	
					location.reload();
				},
			
				type:"post"
			});	
		};
	</script>
</body>
</html>