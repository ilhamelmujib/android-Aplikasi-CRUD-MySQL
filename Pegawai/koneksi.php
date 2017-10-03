<?php  
	define("host", 'localhost');
	define("user", 'root');
	define("pass", "");
	define("db", 'db_android');

	$con = mysqli_connect(host,user,pass,db) or die("Tidak dapat terhubung.");
?>