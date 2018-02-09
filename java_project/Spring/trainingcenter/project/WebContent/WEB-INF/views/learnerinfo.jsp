<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
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
					<div class="form-box">
						<form:form modelAttribute="learner"  method="post">
							<table class="mytable">
								<tr>
									<c:if test="${learner.image ne null and learner.image ne ''}">
										<img src="images/${learner.image}" />
									</c:if>
									<c:if test="${learner.image eq null or learner.image eq ''}">
										<c:if test="${learner.sex eq true}">
											<img src="images/male_default.jpg" />
										</c:if>
										<c:if test="${learner.sex eq false}">
											<img src="images/female_default.jpg" />
										</c:if>
									</c:if>
								</tr>
								<tr>
									<td>Hình đại diện</td>
									<td><form:input path="image" /></td>
									<td>Mã số học viên</td>
									<td><form:input path="learnerId" /></td>
								</tr>
								<tr>
									<td>Họ</td>
									<td><form:input path="lastName" /></td>
									<td>Tên lót</td>
									<td><form:input path="middleName" /></td>
									<td>Tên</td>
									<td><form:input path="firstName" /></td>
								</tr>
								<tr>
									<td>Ngày sinh</td>
									<td><form:input path="birthDateString"
											value="${learner.birthDateString}" /></td>
									<td>Giới tính</td>
									<td><form:input path="sexDisplay" /></td>
									<td>Địa chỉ</td>
									<td><form:input path="address" /></td>
								</tr>
								<tr>
									<td>Email 1</td>
									<td><form:input path="email1" /></td>
									<td>Email 2</td>
									<td><form:input path="email2" /></td>
									<td>Email 3</td>
									<td><form:input path="email3" /></td>
								</tr>
								<tr>
									<td>Di động</td>
									<td><form:input path="mobile" /></td>
									<td>Điện thoại nhà</td>
									<td><form:input path="homePhone" /></td>
									<td>Điện thoại nơi làm</td>
									<td><form:input path="workPhone" /></td>
								</tr>
								<tr>
									<td>CMND</td>
									<td><form:input path="iDcard" /></td>
									<td>Hộ chiếu</td>
									<td><form:input path="iDpassport" /></td>
									<td>Ngày tạo</td>
									<td><form:input path="createdDateString"
											value="${learner.createdDateString}" /></td>
								</tr>
								<tr>
									<td>Tiểu sử trích ngang</td>
									<td colspan="5"><form:textarea path="notes" rows="3"
											cols="76" /></td>
								</tr>
								<tr>
									<td colspan="6" align="center">
										<button formaction="learnerinfoupdate.controller" class="normal-btn theme-btn">Update</button>
										<button formaction="learnerinfo.controller" class="normal-btn theme-btn">Refresh</button>
									</td>
								</tr>
							</table>
						</form:form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="includes/mainfooter.jsp"%>
</body>
</html>