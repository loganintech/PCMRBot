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
<script src="./../js/jquery.min.js"></script>
<script src="https://ttv-api.s3.amazonaws.com/twitch.min.js"></script>
<script>
	$(function() {
		window.CLIENT_ID = '60tool1u9l1unspczyetcutxq6ay951';
		Twitch.init({clientId: CLIENT_ID}, function(error, status) {
			if (error) {
				alert(error);
			}
			
			if (status.authenticated) {
				// Already logged in
				$('.twitch-connect').hide();
				$('.connect-text').hide();
				$('.connected-text').show();
				
				var token = Twitch.getToken();
				
				$.post ("./insert.php",
					{oAuth: token},
					function(data) {
						if (data.toLowerCase() === 'success') {
							// Already logged in
							$('.twitch-connect').hide();
							$('.connect-text').hide();
							$('.connected-text').show();
						} else if(data) {
							alert(data);
						}
					}
				);
			} else {
				// Not logged in
				$('.twitch-connect').show();
				$('.connect-text').show();
				$('.connected-text').hide();
			}
		});
		
		$('.twitch-connect').click(function() {
			Twitch.login({
				redirect_uri: 'http://pcmrbot.no-ip.info/authorize',
				scope: ['channel_editor', 'channel_commercial', 'channel_subscriptions']
			});
		});
	});
</script>
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
					<p class="lead connected-text">Thank you for authorizing PCMRBot with twitch! You can now use it to its full extent!</p>
					<p class="lead connect-text">Click the button below to authorize PCMRBot to change your game, status, run a commercial, and view your subscribers!<br /><br />If you have done this before and you are seeing this page DO NOT click the button unless you were sent here by the bot!</p>
					<img src="http://ttv-api.s3.amazonaws.com/assets/connect_dark.png" class="twitch-connect" href="#" />
				</div>
			</div>
		</div>
	</div>
</body>
</html>
