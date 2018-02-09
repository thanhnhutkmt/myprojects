<%@ page pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
	<base href="/SpringMVC5/">
	<meta charset="utf-8">
	<title>Insert title here</title>
	<script src="js/jquery-1.10.2.min.js"></script>
	<script>
	$(function(){
		$("#hello").click(function(){
			$.ajax({
				url:"ajax/hello.php",
				success:function(response){
					alert(response);
				}
			});
		});
		
		$("#json1").click(function(){
			$.ajax({
				url:"ajax/json1.php",
				dataType:"json",
				success:function(response){
					alert(response.id);
				}
			});
		});
		
		$("#json2").click(function(){
			$.ajax({
				url:"ajax/json2.php",
				dataType:"json",
				success:function(response){
					alert(response.id);
				}
			});
		});
		
		$("#json3").click(function(){
			$.ajax({
				url:"ajax/json3.php",
				dataType:"json",
				success:function(response){
					alert(response.id);
				}
			});
		});
		
		$("#json4").click(function(){
			$.ajax({
				url:"ajax/json4.php",
				dataType:"json",
				success:function(response){
					for(var i=0;i<response.length;i++){
						alert(response[i]);
					}
				}
			});
		});
		
		$("#json5").click(function(){
			$.ajax({
				url:"ajax/json5.php",
				dataType:"json",
				success:function(response){
					for(var i=0;i<response.length;i++){
						alert(response[i]);
					}
				}
			});
		});
		
		$("#json6").click(function(){
			$.ajax({
				url:"ajax/json6.php",
				dataType:"json",
				success:function(response){
					for(var i=0;i<response.length;i++){
						alert(response[i].nameVN);
					}
				}
			});
		});
	});
	</script>
</head>
<body>
	<h2>AJAX DEMO</h2>
	<button id="hello">Hello</button>
	
	<button id="json1">Json1</button>
	<button id="json2">Json2</button>
	<button id="json3">Json3</button>
	<button id="json4">Json4</button>
	<button id="json5">Json5</button>
	<button id="json6">Json6</button>
</body>
</html>