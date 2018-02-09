<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset=UTF-8">
<title>Insert title here</title>
<style>
	.ads{
		position: fixed;
		right:10px;
		bottom:10px;
	}
	.box{
		width:200px;
		height:200px;
		border:1px solid red;	
		position: relative;	
	}
	.basket{
		position:absolute;
		right:0px;
		bottom:0px;
	}
	.heart{
		position:absolute;
		left:0px;
		bottom:0px;
	}	
</style>
</head>
<body>
	<img class="ads" src="images/1.gif">
	<div class="box">
		<img class="basket" src="images/Add to basket.png">
		<img class="heart" src="images/Heart.png">
	</div>
</body>
</html>