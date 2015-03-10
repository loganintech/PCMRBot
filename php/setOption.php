<?php
	ini_set("log_errors", 1);
	ini_set("error_log", "error.log");
	if(isset($_POST['user']) && isset($_POST['option']) && isset($_POST['value'])) {
		$conn = new mysqli('localhost', 'bot', 'Br!sket', 'pcmrbot');
		
		if (mysqli_connect_errno()) {
			trigger_error('Database connection failed: ' . mysqli_connect_error, E_USER_ERROR);
			echo 'There was an error storing your validation in our database';
		}
		$tblName = $_POST['user'] . 'Options';
		$v1 = "'" . $conn->real_escape_string($_POST['option']) . "'";
		$v2 = "'" . $conn->real_escape_string($_POST['value']) . "'";
		$sql = "SELECT * FROM $tblName";
		$query = $conn->query($sql);
		
		if ($query === false) {
			error_log($_POST['user']);
			error_log($tblName);
			trigger_error('Bad SQL: ' . $sql . ' Error: ' . $conn->error, E_USER_ERROR);
			echo 'There was a problem changing the option. Please make sure the bot has joined you channel before!';
		} else {
			$sql = "UPDATE $tblName SET optionID=$v1,value=$v2 WHERE optionID=$v1";
			
			if ($conn->query($sql) === false) {
				trigger_error('Bad SQL: ' . $sql . ' Error: ' . $conn->error, E_USER_ERROR);
				echo 'There was an error storing your validation in our database';
			} else {
				echo 'success';
			}
		}
	}
?>