<?php

	$db_host = "localhost";
	$db_user = "root";
	$db_passwd = "mimo";
	$db_name = "MIMO";
	$conn = mysqli_connect($db_host, $db_user, $db_passwd, $db_name);

	if (mysqli_connect_errno($conn))
	{
		echo "데이터 베이스 연결 실패" . mysql_connect_error();
	}
	else
	{
		echo "데이터 베이스 연결 성공";
	}

	echo "<br>";

	$sql = "INSERT INTO MEMBER (ID, PASSWORD) VALUES ('$_POST[name]', '$_POST[password]')";

	if (mysqli_query($conn, $sql))
	{
		echo "등록 완료";
	}
	else
	{
		echo "Error: " . $sql. "<br>" . mysqli_error($conn);
	}

	mysqli_close($conn);

?>
