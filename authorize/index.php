<?php
if(isset($_GET['code'])){
$url = 'https://api.twitch.tv/kraken/oauth2/token';
$myvars = 'client_id=60tool1u9l1unspczyetcutxq6ay951&client_secret=21h91l0mv4v4frwz35hlxr6jmicl8he&grant_type=authorization_code&redirect_uri=http://pcmrbot.no-ip.info/authorize&code=$_GET["code"]';

$ch = curl_init( $url );
curl_setopt( $ch, CURLOPT_POST, 1);
curl_setopt( $ch, CURLOPT_POSTFIELDS, $myvars);
curl_setopt( $ch, CURLOPT_FOLLOWLOCATION, 1);
curl_setopt( $ch, CURLOPT_HEADER, 0);
curl_setopt( $ch, CURLOPT_RETURNTRANSFER, 1);

$response = curl_exec( $ch );
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
<title>PCMRBot | Authorize</title>
<meta name="google-site-verification" content="OZkY9PcW-6dY0cmpkWWbexQB8HrqCee9J5myGH-prz0" />
<link href="http://fonts.googleapis.com/css?family=Corben:bold|Nobile" rel="stylesheet" type="text/CSS">
<link href="../css/boilerplate.css" rel="stylesheet" type="text/css">
<link href="../css/layout.css" rel="stylesheet" type="text/css">
<link href="../css/common.css" rel="stylesheet" type="text/css">
<link href="../css/bootstrap.css" rel="stylesheet" type="text/css">
<link href="../css/bootstrap-responsive.css" rel="stylesheet" type="text/css">
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
<script src="./../js/respond.min.js"></script>
</head>
<body>
	<div class="gridContainer clearfix">
		<div id="content" class="fluid  container-narrow">
			<div id="content" class="fluid  container-narrow">
				<div class="masthead">
					<ul class="nav nav-pills pull-right">
						<li><a href="../">Home</a></li>
						<li><a href="../commands">Commands</a></li>
						<li class="active"><a href="../authorize">Authorize</a></li>
					</ul>
				</div>
				<hr>
				<div id="main" class="jumbotron">
					<h2>PCMRBot Authorization!</h2>
					<p class="lead">Click the button below to authorize PCMRBot to change your game, status, run a commercial, and view your subscribers!</p>
					Twitch Username: <input type="text" id="user">
					<a href="https://api.twitch.tv/kraken/oauth2/authorize?response_type=token&client_id=60tool1u9l1unspczyetcutxq6ay951&redirect_uri=http://pcmrbot.no-ip.info/authorize&scope=channel_editor channel_commercial channel_subscriptions"><input type="submit" id="user-submit" value="Authorize!"></a>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
