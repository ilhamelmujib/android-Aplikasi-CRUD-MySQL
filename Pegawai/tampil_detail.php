<?php  
	
	$id = $_GET['id'];

	require_once 'koneksi.php';
	
	$sql = "SELECT * FROM tb_pegawai WHERE id = '$id'";

	$result = array();
	$r = mysqli_query($con,$sql);

	while ($row = mysqli_fetch_array($r)) {
		
		array_push($result, array(
				"id" => $row['id'],
				"name" => $row['nama'],
				"desg" => $row['posisi'],
				"salary" => $row['gaji'],
			));

	}
	
	echo json_encode(array('result' => $result));
	mysqli_close($con);

?>