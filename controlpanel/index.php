<?php
	if (!isset($_GET['user']) || $_GET['user'] == "") {
		header('Location: http://pcmrbot.no-ip.info/login');
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
<title>PCMRBot | Control Panel</title>
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
<script src="../js/respond.min.js"></script>
<script src="./../js/jquery.min.js"></script>
<script src="../js/tabchat.js"></script>
<script>
	$(function () {
		$('#user').hide();
		$('#link').hide();
		$('#welcomeDisable').hide();
		$('#optionValue').show();
	
		$('#textSubmit').click(function() {
			$.post("../php/setOption.php",
				{user: $('#user').val(), option: 'welcomeMessage', value: $('#welcomeMessage').val()},
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
		});
	
		$('#welcomeDisable').click(function() {
			$.post("../php/setOption.php",
				{user: $('#user').val(), option: 'welcomeMessage', value: 'none'},
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
		});
	
		$('#optionSubmit').click(function() {
			if ($('#options').val() != 'no') {
				if ($('#options').val() == 'link') {
					if ($('#link').val() != 'no') {
						$.post("../php/setOption.php",
							{user: $('#user').val(), option: 'link', value: $('#link').val()},
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
					}
				} else {
					if (/^-?\d+$/.test($('#optionValue').val())) {
						$.post("../php/setOption.php",
							{user: $('#user').val(), option: $('#options').val(), value: $('#optionValue').val()},
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
					}
				}
			}
		});
	
		$('#options').change(function() {
			if ($('#options').val() == 'link') {
				$('#link').show();
				$('#optionValue').hide();
			} else {
				$('#link').hide();
				$('#optionValue').show();
			}
		});
		
		$('#textOptions').change(function() {
			if ($('#textOptions').val() == 'wm') {
				$('#welcomeDisable').show();
			} else {
				$('#welcomeDisable').hide();
			}
		});
	});

	function getUser() {
		var params = {};
		
		if (location.search) {
			var parts = location.search.substring(1).split('&');

			for (var i = 0; i < parts.length; i++) {
				var nv = parts[i].split('=');
				if (!nv[0]) continue;
				params[nv[0]] = nv[1] || true;
			}
			
			return params.user;
		}
	}
</script>
</head>
<body onLoad="loadsolotwitch(document.getElementById('user').innerHTML)">
	<div class="gridContainer clearfix">
		<div id="content" class="fluid  container-narrow">
			<div class="masthead">
				<ul class="nav nav-pills pull-right">
					<li><a href="../">Home</a></li>
					<li><a href="../commands">Commands</a></li>
					<li><a href="../login">Login</a></li>
					<li><a href="../authorize">Authorize</a></li>
				</ul>
			</div>
			<br />
			<br />
			<br />
			<hr>
			<p id="user"><?php echo $_GET['user']; ?></p>
			<div class="text-center">
				<input type="submit" value="Join" id="join" />
				<input type="submit" value="Leave" id="leave" />
			</div>
			<br />
			<div id="points" class="fluid ">
				<form class="form-inline">
					<div class="form-group" id="textType">
						<select id="options" name="textOptions">
							<option selected value="no">Choose an Option</option>
							<option value="title">Title</option>
							<option value="game">Game</option>
							<option value="wm">Welcome Message</option>
						</select>
					</div>
					<div class="form-group">
						<input type="text" id="textValue" name="textValue" placeholder="Value" />
					</div>
					<input type="submit" value="Submit" id="textSubmit" />
					<input type="submit" value="Disable" id="welcomeDisable" />
				</form>
			</div>
			<div id="reliability" class="fluid ">
				<form class="form-inline">
					<div class="form-group" id="optionType">
						<select id="options" name="options">
							<option selected value="no">Choose an Option</option>
							<option value="numCaps">Caps Limit</option>
							<option value="numSymbols">Symbols Limit</option>
							<option value="numEmotes">Emote Limit</option>
							<option value="paragraphLength">Paragraph Limit</option>
							<option value="link">Links</option>
						</select>
					</div>
					<div class="form-group" id="link">
						<select name="link">
							<option selected value="no">Choose One</option>
							<option value="0">Enable</option>
							<option value="-1">Disable</option>
						</select>
					</div>
					<div class="form-group" id="optionValue" >
						<input type="text" name="optionValue" placeholder="Value" />
					</div>
					<input type="submit" value="Submit" id="optionSubmit" />
				</form>
			</div>
			<br />
			<br />
			<br />
			<br />
			<hr>
			<div id="men" class="men" name="men"></div>
		
			<div id="twitch" class="twitch" name="twitch">
				<div id="load" name="load" name="load" class="load"><p id="vs" class="vs"><img src="../media/loading.gif"  height="15" width="15" />Loading.....</p></div>
			
				<div id="reloader" class="reloader" name="reloader"></div>
			</div>
		</div>
	</div>
</body>
</html>
