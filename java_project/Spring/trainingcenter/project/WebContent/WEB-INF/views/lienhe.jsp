<%@ page pageEncoding="utf-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
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
	<%@include file="includes/mainheader.jsp" %>
	<div class="contact-section" id="contact-section">
        <div class="auto-container">
        	<div class="row clearfix">
                <!--Content Side-->
                <div class="col-md-8 col-sm-7 col-xs-12 column">
                	<div class="sec-title" >
		                <h3 align="center">LIÊN HỆ</h3>        
		                <h2 align="center">TRUNG TÂM THUNDER</h2>        
		                <h4 align="center"><div class="line"></div></h4>
                    </div>
                    <div class="form-box">
                    	<form:form id="feedbackform" modelAttribute="feedback">
                            <div class="row clearfix">
                                <div class="form-group col-md-12 col-sm-12 col-xs-12">
                                	<div class="field-label">Họ tên</div>
                                	<form:input path="fullName" />
                                </div>
                                <div class="form-group col-md-6 col-sm-12 col-xs-12">
                                	<div class="field-label">Email</div>
                                	<form:input path="email" />
                                </div>
                                <div class="form-group col-md-6 col-sm-12 col-xs-12">
                                	<div class="field-label">Phone</div>
                                	<form:input path="phone" />
                                </div>
                                <div class="form-group col-md-12 col-sm-12 col-xs-12">
                                	<div class="field-label">Tiêu đề</div>
                                	<form:input path="title" />
                                </div>
                                <div class="form-group col-md-12 col-sm-12 col-xs-12">
                                    <div class="field-label">Nội dung</div>
                                    <form:textarea path="content" rows="3" cols="47"/>
                                </div>                                
                            </div>
                            <div align="right"><button id="buttongui" class="normal-btn theme-btn" formaction="sendfeedback.controller">Gửi</button></div>
                        </form:form>
                        <div align="right"><button id="buttonxoa" class="normal-btn theme-btn" >Xóa</button></div>  
                    </div>                    
                </div>
    		</div>
        </div>
    </div>

	<%@ include	file="includes/mainfooter.jsp"%>
	<script>
		$("#buttonxoa").click(function() {
			$("#feedbackform")[0].reset();
		});
	</script>
</body>
</html>