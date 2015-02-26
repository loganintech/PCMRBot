function loadtwitch(username){

	document.getElementById('twitch').innerHTML='<p id="vs" class="vs"><img src="./media/loading.gif"  height="15" width="15" />Loading.....</p>';
		
	document.getElementById('twitch').setAttribute("style", "background-color: black;");
		
	setTimeout(function(){
		switch (username) {
		case "angablade":
			document.getElementById('twitch').innerHTML='<ul id="navtabs" class="navtabs"><li class="selected"><button>Angablade</button></li><li><button onclick="javascript:loadtwitch(\'donald10101\');">Donald10101</button></li><li><button onclick="javascript:loadtwitch(\'j3wsofhazard\');">J3wsOfHazard</button></li><li><button onclick="javascript:loadtwitch(\'officialpcmasterrace\');">Official PC Master Race</button></li></ul><br /><br /><iframe id="twitch-player" src="http://www.twitch.tv/angablade/embed" frameborder="0" scrolling="no" height="378" width="620">We\'re sorry. Your browser doesn\'t support Iframes. Please update your browser to view the embedded content.</iframe>&nbsp;<iframe id="twitch-chat" frameborder="0" scrolling="yes" id="chat_embed" src="http://www.twitch.tv/angablade/chat" height="378" width="300">We\'re sorry. Your browser doesn\'t support Iframes. Please update your browser to view the embedded content.</iframe>';
			break;

		case "donald10101":
			document.getElementById('twitch').innerHTML='<ul id="navtabs" class="navtabs"><li><button onclick="javascript:loadtwitch(\'angablade\');">Angablade</button></li><li class="selected"><button>Donald10101</button></li><li><button onclick="javascript:loadtwitch(\'j3wsofhazard\');">J3wsOfHazard</button></li><li><button onclick="javascript:loadtwitch(\'officialpcmasterrace\');">Official PC Master Race</button></li></ul><br /><br /><iframe id="twitch-player" src="http://www.twitch.tv/donald10101/embed" frameborder="0" scrolling="no" height="378" width="620">We\'re sorry. Your browser doesn\'t support Iframes. Please update your browser to view the embedded content.</iframe>&nbsp;<iframe id="twitch-chat" frameborder="0" scrolling="yes" id="chat_embed" src="http://www.twitch.tv/donald10101/chat" height="378" width="300">We\'re sorry. Your browser doesn\'t support Iframes. Please update your browser to view the embedded content.</iframe>';
			break;
						
		case "j3wsofhazard":
			document.getElementById('twitch').innerHTML='<ul id="navtabs" class="navtabs"><li><button onclick="javascript:loadtwitch(\'angablade\');">Angablade</button></li><li><button onclick="javascript:loadtwitch(\'donald10101\');">Donald10101</button></li><li class="selected"><button>J3wsOfHazard</button></li><li><button onclick="javascript:loadtwitch(\'officialpcmasterrace\');">Official PC Master Race</button></li></ul><br /><br /><iframe id="twitch-player" src="http://www.twitch.tv/j3wsofhazard/embed" frameborder="0" scrolling="no" height="378" width="620">We\'re sorry. Your browser doesn\'t support Iframes. Please update your browser to view the embedded content.</iframe>&nbsp;<iframe id="twitch-chat" frameborder="0" scrolling="yes" id="chat_embed" src="http://www.twitch.tv/j3wsofhazard/chat" height="378" width="300">We\'re sorry. Your browser doesn\'t support Iframes. Please update your browser to view the embedded content.</iframe>';
			break;

		case "officialpcmasterrace":
			document.getElementById('twitch').innerHTML='<ul id="navtabs" class="navtabs"><li><button onclick="javascript:loadtwitch(\'angablade\');">Angablade</button></li><li><button onclick="javascript:loadtwitch(\'donald10101\');">Donald10101</button></li><li><button onclick="javascript:loadtwitch(\'j3wsofhazard\');">J3wsOfHazard</button></li><li class="selected"><button>Official PC Master Race</button></li></ul><br /><br /><iframe id="twitch-player" src="http://www.twitch.tv/officialpcmasterrace/embed" frameborder="0" scrolling="no" height="378" width="620">We\'re sorry. Your browser doesn\'t support Iframes. Please update your browser to view the embedded content.</iframe>&nbsp;<iframe id="twitch-chat" frameborder="0" scrolling="yes" id="chat_embed" src="http://www.twitch.tv/officialpcmasterrace/chat" height="378" width="300">We\'re sorry. Your browser doesn\'t support Iframes. Please update your browser to view the embedded content.</iframe>';
			break;
			
		default:
			document.getElementById('twitch').innerHTML='<ul id="navtabs" class="navtabs"><li><button onclick="javascript:loadtwitch(\'angablade\');">Angablade</button></li><li><button onclick="javascript:loadtwitch(\'donald10101\');">Donald10101</button></li><li><button onclick="javascript:loadtwitch(\'j3wsofhazard\');">J3wsOfHazard</button></li><li class="selected"><button>Official PC Master Race</button></li></ul><br /><br /><iframe id="twitch-player" src="http://www.twitch.tv/officialpcmasterrace/embed" frameborder="0" scrolling="no" height="378" width="620">We\'re sorry. Your browser doesn\'t support Iframes. Please update your browser to view the embedded content.</iframe>&nbsp;<iframe id="twitch-chat" frameborder="0" scrolling="yes" id="chat_embed" src="http://www.twitch.tv/officialpcmasterrace/chat" height="378" width="300">We\'re sorry. Your browser doesn\'t support Iframes. Please update your browser to view the embedded content.</iframe>';
			break;
		}
	},3000);
	
	setTimeout(function(){
		document.getElementById('twitch').setAttribute("style", "background-color: white;");
	},3000);
	
}
//<ul id="navtabs" class="navtabs"><li><button onclick="javascript:loadtwitch('angablade');">Angablade</button></li><li><button onclick="javascript:loadtwitch('donald10101');">Donald10101</button></li><li><button onclick="javascript:loadtwitch('j3wsofhazard');">J3wsOfHazard</button></li><li class="selected"><button onclick="javascript:loadtwitch('officialpcmasterrace');">Official PC Master Race</button></li></ul>