<?php  
	
	$id = $_GET['id'];

	require_once 'koneksi.php';
	
	$sql = "DELETE FROM tb_pegawai WHERE id = '$id'";

	if (mysqli_query($con,$sql)) {
		echo "Berhasil di hapus.";
	}else{
		echo mysqli_error();
	}

	mysqli_close($con);

?>