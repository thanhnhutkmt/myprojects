<%@ page pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
	<base href="/SpringMVC4/" />
	<meta charset="utf-8">
	<title>Insert title here</title>
</head>
<body>
	<h1>Kết quả</h1>
	<img src="images/${filename}" >
	<ul>
		<li>Filename: ${filename}</li>
		<li>Filetype: ${filetype}</li>
		<li>Filesize: ${filesize}</li>
	</ul>
</body>
</html>