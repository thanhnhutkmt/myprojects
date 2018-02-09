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
		$("#ok").click(function(){
			$.ajax({
				url:"category/list.php",
				success:function(response){
					$("#list").html("");
					for(var i=0;i<response.length;i++){
						$("#list").append("<li>"+response[i].nameVN+"</li>");
					}
				},
				dataType:"json",
				type:"post"
			});
		});
		
		$("#insert").click(function(){
			var name = $("#name").val();
			var nameVN = $("#nameVN").val();
			$.ajax({
				url:"category/insert.php",
				data:{name:name, nameVN:nameVN},
				success:function(response){
					$("#ok").click();
				},
				type:"post"
			});
		});
	});
	</script>
</head>
<body>
	<h1>AJAX DEMO</h1>
	<div>
		<div>Name</div>
		<input id="name">
	</div>
	<div>
		<div>NameVN</div>
		<input id="nameVN">
	</div>
	<button id="insert">Insert</button>
	<hr>
	<button id="ok">OK</button>
	<ul id="list">
		
	</ul>
</body>
</html>