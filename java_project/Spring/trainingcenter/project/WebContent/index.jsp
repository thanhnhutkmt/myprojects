<%@ page pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
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
.warnnote {
	color: #000001
}
.inner {
	color: #ffffff;
	font-weight: 400
}
.inner:hover {
	color: #28ffe2
}
</style>


</head>
<body>
<%@ include file="WEB-INF/views/includes/mainheader.jsp" %>
<Section class="main-slider p-0 m-0">
		<div class="container" style="width: 100%;">
			<div id="myCarousel" class="carousel slide" data-ride="carousel">
				<!-- Indicators -->
				<ol class="carousel-indicators">
					<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
					<li data-target="#myCarousel" data-slide-to="1"></li>
					<li data-target="#myCarousel" data-slide-to="2"></li>
				</ol>

				<!-- Wrapper for slides -->
				<div class="carousel-inner" role="listbox">

					<div class="item active">
						<img src="images/slider1.jpg" alt="Los Angeles"
							style="width: 100%;">
						<div class="carousel-caption">
							<h3>Web app</h3>
							Make deluxe websites
						</div>
					</div>

					<div class="item">
						<img src="images/slider2.jpg" alt="Chicago" style="width: 100%;">
						<div class="carousel-caption">
							<h3>Mobile app</h3>
							<p>App for your phones, tablets</p>
						</div>
					</div>

					<div class="item">
						<img src="images/slider3.jpg" alt="New York" style="width: 100%;">
						<div class="carousel-caption">
							<h3>Electronics</h3>
							<p>Interesting circuits</p>
						</div>
					</div>

				</div>

				<!-- Left and right controls -->
				<a class="left carousel-control" href="#myCarousel"
					data-slide="prev"> <span
					class="glyphicon glyphicon-chevron-left"></span> <span
					class="sr-only">Previous</span>
				</a> 
				<a class="right carousel-control" href="#myCarousel"
					data-slide="next"> <span
					class="glyphicon glyphicon-chevron-right"></span> 
					<span class="sr-only">Next</span>
				</a>
			</div>
		</div>
	</Section>

	<!--Featured Three Column-->

	<section class="pt-20 pb-20">
		<div class="auto-container">
			<div class="row clearfix">
				<!--Column-->
				<div class="col-md-4 col-sm-6 col-xs-12">
					<article class="inner-box">
						<a href=""><img src="images/column1.png"
							alt="Thiết kế Mobile"></a>
						<div class="content">
							<h3>THIẾT KẾ MOBILE APP</h3>
							<div class="text">
								<p>
									Ứng dụng di động là xu hướng thế giới, giúp doanh nghiệp
									tiếp cận được với người sử dụng smart phone. Thunder chuyên 
									phát triển ứng dụng di động trên iOS/Android/windowPhone
									cho doanh nghiệp và cá nhân.
								</p>
							</div>
							<div class="link">
								<a href="" class="read-more normal-btn theme-btn"> <span
									class="fa fa-phone"></span> 094 869 7739
								</a>
							</div>
						</div>
					</article>
				</div>
				<!--Column-->
				<div class="col-md-4 col-sm-6 col-xs-12">
					<article class="inner-box">
						<a href=""><div class="inner-box-hover"><img src="images/column2.png" alt="Thiết kế Web"></div></a>
						<div class="content">
							<h3>THIẾT KẾ WEBSITE</h3>
							<div class="text">
								<p>Thunder giảng dạy thiết kế website bằng nhiều công nghệ
								mới, đang được sử dụng trong các công ty và doanh nghiệp
								hiện tại. 
								
								</p>
							</div>
							<div class="link">
								<a href="" class="read-more normal-btn theme-btn"> <span
									class="fa fa-phone"></span> 094 869 7739
								</a>
							</div>
						</div>
					</article>
				</div>
				<!--Column-->
				<div class="col-md-4 col-sm-6 col-xs-12">
				<article class="inner-box">                        
					<a href=""><img src="images/column3.png" alt="Thunder Training"></a>                            
					<div class="content">
						<h3>ĐÀO TẠO IOT</h3>
						<div class="text">
							<p>
							  Thunder có thể đào tạo chuyên sâu về IoT, điện tử, hệ thống
							  nhúng mà ít có trung tâm nào có thể
							  
							  
							</p>
						</div>
						<div class="link"><a href="" class="read-more normal-btn theme-btn">
						  <span class="fa fa-phone"></span> 094 869 7739 </a></div>
					</div>
				</article>
			</div>
		</div>	
		</div>
	</section>
	    <!--Why Us Section-->
    <section class="parallax-section pt-30 pb-30" style="background-image:url(images/Training.jpg);">
        <div class="auto-container" align="left">
			<h2 class="warnnote">BẠN NÊN CHỌN HỌC TẠI THUNDER VÌ:</h2>
			<div class="wblock">
			<div class="wbnumber">1</div>
				<div class="warnnote"><h4>Phương pháp giảng dạy lập trình hiệu quả</h4>
				<p class="inner">Lập trình là rất thú vị và tuyệt vời <br>
				   để mọi người khám phá. Khơi gợi và truyền<br>
				   lửa cho học viên là những gì chúng tôi sẽ làm. <br>
				</p></div>
			</div>
			<div class="wblock">
			<div class="wbnumber">2</div>
				<div class="warnnote"><h4>Giảng viên là những người có kinh nghiệm thực tế.</h4>
				<p class="inner">Giảng viên tại Thunder đều có <br>
				   kinh nghiệm thực tế và đam mê giảng dạy.				  
				</p></div>
			</div>
			<div class="wblock">
			<div class="wbnumber">3</div>
				<div class="warnnote"><h4>Chương trình học được cập nhật liên tục</h4>
				<p class="inner">Với sự thay đổi như vũ bão của công nghệ, <br>
				   chương trình học của chúng tôi luôn <br>
				   theo kịp xu hướng, công nghệ		
				</p></div>
			</div>
		</div>
	</section>

	<%@ include file="WEB-INF/views/includes/mainfooter.jsp" %>
</body>
</html>
