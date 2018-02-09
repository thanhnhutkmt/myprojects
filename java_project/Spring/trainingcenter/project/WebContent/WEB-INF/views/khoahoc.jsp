<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<base src="/TrainingCenter/" />
<meta charset="utf-8">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
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
	<%@ include file="includes/mainheader.jsp"%>
	<div class="contact-section" id="contact-section">
		<div class="auto-container">
			<div class="row clearfix">
				<!--Content Side-->
				<div class="col-md-8 col-sm-7 col-xs-12 column pull-right">
					<div class="sec-title">
						<h2>Lớp học hiện có</h2>
						<div class="line"></div>
					</div>
					<div class="form-box">						
						<div class="row clearfix">
							<table border="2" style="width:100%"> 
								<tr>
								<th><strong>Thời gian học</strong></th>
								<th><strong>Phòng học</strong></th>
								<th><strong>Ngày khai giảng</strong></th>
								<th><strong>Học phí</strong></th>
								<th></th>
								<th></th>						
								</tr>							
								<tbody id="list"></tbody>								
							</table>			
							<div id="howtoreg"></div>			
						</div>											
					</div>
				</div>

				<!--Left Side-->
				<div class="col-md-4 col-sm-5 col-xs-12 column pull-left">
					<div class="sec-title">
						<h2>Thông tin khóa học</h2>
						<div class="line"></div>
					</div>
					<div class="info-box">
						<form:select id="mySelect" onchange="myFunc()" path="course">
							<form:option value="NONE">---Chọn khóa học---</form:option>
							<form:options itemValue="description" items="${listcourse}" itemLabel="title"></form:options>
						</form:select>
						<div class="form-box">							
							<div class="row clearfix">							
								<ul id="coursedescription"></ul>												
							</div>							
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<%@ include file="includes/mainfooter.jsp"%>
	<script>
		function myFunc() {	
			var description = $("#mySelect").val();
			$("#coursedescription").html("");
		    $("#coursedescription").append(description);
// 		    alert(description);		    
			$.ajax({
				url:"getclassschedule.controller", //controller  ?id=${mySelect}
				data:{description:description},	   //input ->courseid selected item
				success:function(response) { 	   //select data to display								
					$("#list").html("");										
					for(var i=0;i<response.length;i++) {
						$("#list").append(
						"<tr>"+
							"<td>"+ response[i].timeTable + "</td>"+
							"<td>" + response[i].room + "</td>"+
							"<td>" + response[i].openDate + "</td>"+
							"<td><strong>VNĐ " + Number(response[i].fee).toLocaleString('en') + "</strong></td>" +
							"<td><button name=\"btndangky\" onclick=\"dangky(" + i + ")\">Đăng ký</button></td>" +
						    "</tr>");
					}
					$("#list").append(
					"<td></td><td></td>" + 
					"<td align=\"center\"><button id=\"btnyt\" name=\"btnyeuthich\" onclick=\"yeuthich()\">Yêu Thích</button></td>" +
					"<td></td><td></td>");
					$("#list").append("</table>");
				},
				dataType:"json",
				type:"post"
			});
 		}
		function dangky(classnumber) {
			var description = $("#mySelect").val();			
			$.ajax({
				url:"registerclasses.controller", 						//controller  ?id=${mySelect}
				data:{courseid:classnumber, description:description},	//input ->courseid selected item
				success:function(response) { 	   						//select data to display									                
					alert(response.ketqua);
					$("#howtoreg").html("");
					$("#howtoreg").append(response.ketqua);						
				},
			    error: function(xhr, status, error) {
// 			    	alert(xhr.responseText);
// 			        console.log(arguments);
// 			        alert(" Can't do because: " + error);
// 					alert(xhr.responseText.ketqua);
					$("#howtoreg").html("");
					$("#howtoreg").append(xhr.responseText);	
			    },
				dataType:"json",
				type:"post"
			});		
		}	
		function yeuthich() {
			var description = $("#mySelect").val();			
			$.ajax({
				url:"registerfavcourse.controller",						//controller  ?id=${mySelect}
				data:{description:description},	//input ->courseid selected item
				success:function(response) { 	   						//select data to display									                
					alert(response.ketqua);
					$("#btnyt").css({"background-color":"#f9004f"});
					$("#btnyt").prop("disabled",true);
				},
				dataType:"json",
				type:"post"
			});		
		}			
	</script>
	
</body>
</html>
