<?php
	if(!isset($_GET['user']) {
		header('Location: ../login');
		die();
	}
?>
<!doctype html>
<!--[if lt IE 7]> <html class="ie6 oldie"> <![endif]-->
<!--[if IE 7]>    <html class="ie7 oldie"> <![endif]-->
<!--[if IE 8]>    <html class="ie8 oldie"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="">
<!--<![endif]-->
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>PCMRBot | Home</title>
<meta name="google-site-verification" content="OZkY9PcW-6dY0cmpkWWbexQB8HrqCee9J5myGH-prz0" />
<link href="http://fonts.googleapis.com/css?family=Corben:bold|Nobile" rel="stylesheet" type="text/CSS">
<link href="css/boilerplate.css" rel="stylesheet" type="text/css">
<link href="css/layout.css" rel="stylesheet" type="text/css">
<link href="css/common.css" rel="stylesheet" type="text/css">
<link href="css/bootstrap.css" rel="stylesheet" type="text/css">
<link href="css/bootstrap-responsive.css" rel="stylesheet" type="text/css">
<!-- 
To learn more about the conditional comments around the html tags at the top of the file:
paulirish.com/2008/conditional-stylesheets-vs-css-hacks-answer-neither/

Do the following if you're using your customized build of modernizr (http://www.modernizr.com/):
* insert the link to your js here
* remove the link below to the html5shiv
* add the "no-js" class to the html tags at the top
* you can also remove the link to respond.min.js if you included the MQ Polyfill in your modernizr build 
-->
<!--[if lt IE 9]>
<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->
<script src="js/respond.min.js"></script>
</head>
<body>
	<div class="gridContainer clearfix">
	  <div id="content" class="fluid  container-narrow">
        <div class="masthead">
            <ul class="nav nav-pills pull-right">
              <li class="active"><a href="./">Home</a></li>
              <li><a href="./commands">Commands</a></li>
              <li><a href="./authorize">Authorize</a></li>
            </ul>
        </div>
		<br />
		<br />
		<br />
        <hr>
      </div>
</div>
</body>
</html>
