<?php
	if(isset($_POST['oAuth'])) {
		$usernameResult = file_get_contents("https://api.twitch.tv/kraken?oauth_token=" . $_POST['oAuth']);
		$json_decoded_usernameResult = json_decode($usernameResult, true);
		$username = $json_decoded_usernameResult['token']['user_name'];
		
		$conn = new mysqli('localhost', 'bot', 'Br!sket', 'pcmrbot');
		
		if (mysqli_connect_errno()) {
			trigger_error('Database connection failed: ' . mysqli_connect_error, E_USER_ERROR);
			echo 'There was an error storing your validation in our database';
		}		
		$v1 = "'" . $conn->real_escape_string($username) . "'";
		$v2 = "'" . $conn->real_escape_string($_POST['oAuth']) . "'";
		$query = $conn->query("SELECT * FROM userOAuth where userID=$v1");
		if ($query === false) {
			trigger_error('Bad SQL: ' . $sql . ' Error: ' . $conn->error, E_USER_ERROR);
			echo 'There was an error storing your validation in our database';
		} else if (mysqli_num_rows($query) !== 0) {
			$sql = "UPDATE userOAuth SET userID=$v1,oAuth=$v2 WHERE userID=$v1";
			
			if ($conn->query($sql) === false) {
				trigger_error('Bad SQL: ' . $sql . ' Error: ' . $conn->error, E_USER_ERROR);
				echo 'There was an error storing your validation in our database';
			} else {
				echo 'success';
			}
		} else {
			$sql = "INSERT INTO userOAuth (userID, oAuth) VALUES ($v1,$v2)";
			
			if ($conn->query($sql) === false) {
				trigger_error('Bad SQL: ' . $sql . ' Error: ' . $conn->error, E_USER_ERROR);
				echo 'There was an error storing your validation in our database';
			} else {
				echo 'success';
			}
		}
	}
?>