function loadtwitch(username){

		document.getElementById('load').style.display = 'inline';
		document.getElementById('reloader').style.display = 'none';
		
		switch (username) {
			case "angablade":
				document.getElementById('men').innerHTML='<ul id="navtabs" class="navtabs"><li class="selected"><button>Angablade</button></li><li><button onclick="javascript:loadtwitch(\'donald10101\');">Donald10101</button></li><li><button onclick="javascript:loadtwitch(\'j3wsofhazard\');">J3wsOfHazard</button></li><li><button onclick="javascript:loadtwitch(\'officialpcmasterrace\');">Official PC Master Race</button></li></ul>';
				
				document.getElementById('reloader').innerHTML='<br /><iframe id="twitch-player" src="http://www.twitch.tv/angablade/embed" frameborder="0" scrolling="no" height="378" width="620">We\'re sorry. Your browser doesn\'t support Iframes. Please update your browser to view the embedded content.</iframe>&nbsp;<iframe id="twitch-chat" frameborder="0" scrolling="yes" id="chat_embed" src="http://www.twitch.tv/angablade/chat" height="378" width="300">We\'re sorry. Your browser doesn\'t support Iframes. Please update your browser to view the embedded content.</iframe>';
				break;

			case "donald10101":
				document.getElementById('men').innerHTML='<ul id="navtabs" class="navtabs"><li><button onclick="javascript:loadtwitch(\'angablade\');">Angablade</button></li><li class="selected"><button>Donald10101</button></li><li><button onclick="javascript:loadtwitch(\'j3wsofhazard\');">J3wsOfHazard</button></li><li><button onclick="javascript:loadtwitch(\'officialpcmasterrace\');">Official PC Master Race</button></li></ul>';
			
				document.getElementById('reloader').innerHTML='<br /><iframe id="twitch-player" src="http://www.twitch.tv/donald10101/embed" frameborder="0" scrolling="no" height="378" width="620">We\'re sorry. Your browser doesn\'t support Iframes. Please update your browser to view the embedded content.</iframe>&nbsp;<iframe id="twitch-chat" frameborder="0" scrolling="yes" id="chat_embed" src="http://www.twitch.tv/donald10101/chat" height="378" width="300">We\'re sorry. Your browser doesn\'t support Iframes. Please update your browser to view the embedded content.</iframe>';
				break;
							
			case "j3wsofhazard":
				document.getElementById('men').innerHTML='<ul id="navtabs" class="navtabs"><li><button onclick="javascript:loadtwitch(\'angablade\');">Angablade</button></li><li><button onclick="javascript:loadtwitch(\'donald10101\');">Donald10101</button></li><li class="selected"><button>J3wsOfHazard</button></li><li><button onclick="javascript:loadtwitch(\'officialpcmasterrace\');">Official PC Master Race</button></li></ul>';
				
				document.getElementById('reloader').innerHTML='<br /><iframe id="twitch-player" src="http://www.twitch.tv/j3wsofhazard/embed" frameborder="0" scrolling="no" height="378" width="620">We\'re sorry. Your browser doesn\'t support Iframes. Please update your browser to view the embedded content.</iframe>&nbsp;<iframe id="twitch-chat" frameborder="0" scrolling="yes" id="chat_embed" src="http://www.twitch.tv/j3wsofhazard/chat" height="378" width="300">We\'re sorry. Your browser doesn\'t support Iframes. Please update your browser to view the embedded content.</iframe>';
				break;

			case "officialpcmasterrace":
				document.getElementById('men').innerHTML='<ul id="navtabs" class="navtabs"><li><button onclick="javascript:loadtwitch(\'angablade\');">Angablade</button></li><li><button onclick="javascript:loadtwitch(\'donald10101\');">Donald10101</button></li><li><button onclick="javascript:loadtwitch(\'j3wsofhazard\');">J3wsOfHazard</button></li><li class="selected"><button>Official PC Master Race</button></li></ul>';
				
				document.getElementById('reloader').innerHTML='<br /><iframe id="twitch-player" src="http://www.twitch.tv/officialpcmasterrace/embed" frameborder="0" scrolling="no" height="378" width="620">We\'re sorry. Your browser doesn\'t support Iframes. Please update your browser to view the embedded content.</iframe>&nbsp;<iframe id="twitch-chat" frameborder="0" scrolling="yes" id="chat_embed" src="http://www.twitch.tv/officialpcmasterrace/chat" height="378" width="300">We\'re sorry. Your browser doesn\'t support Iframes. Please update your browser to view the embedded content.</iframe>';
				break;
				
			default:
				document.getElementById('men').innerHTML='<ul id="navtabs" class="navtabs"><li><button onclick="javascript:loadtwitch(\'angablade\');">Angablade</button></li><li><button onclick="javascript:loadtwitch(\'donald10101\');">Donald10101</button></li><li><button onclick="javascript:loadtwitch(\'j3wsofhazard\');">J3wsOfHazard</button></li><li class="selected"><button>Official PC Master Race</button></li></ul>';
				
				document.getElementById('reloader').innerHTML='<br /><iframe id="twitch-player" src="http://www.twitch.tv/officialpcmasterrace/embed" frameborder="0" scrolling="no" height="378" width="620">We\'re sorry. Your browser doesn\'t support Iframes. Please update your browser to view the embedded content.</iframe>&nbsp;<iframe id="twitch-chat" frameborder="0" scrolling="yes" id="chat_embed" src="http://www.twitch.tv/officialpcmasterrace/chat" height="378" width="300">We\'re sorry. Your browser doesn\'t support Iframes. Please update your browser to view the embedded content.</iframe>';
				break;
				
		}

		setTimeout(function(){
			document.getElementById('load').style.display = 'none';
			document.getElementById('reloader').style.display = 'inline';
		},4000);
}