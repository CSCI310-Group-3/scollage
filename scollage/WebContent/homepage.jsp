<!DOCTYPE html>
<html>
	<head>
	<meta charset="UTF-8">
	<title>Build A Collage</title>
	<link rel="stylesheet" type="text/css" href="homepageStyle.css">
	<script src="homepageScripts.js"></script>
	</head>
	<body>
		<div>
		<form name="homePage" id="input" method="POST" action="ImageServer">
			<input type="text" id="inputBox" name="topic" placeholder="Enter topic" onkeyup="editingStopped()"/>
			<input type="Submit" id = "submitButton" name="build" value="Build Collage" disabled/>
		</form>
		</div>
	</body>
</html>