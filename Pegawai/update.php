<?php  

	if ($_SERVER['REQUEST_METHOD'] == 'POST') {
		
		$id = $_POST['id'];
		$name = $_POST['name'];
		$desg = $_POST['desg'];
		$salary = $_POST['salary'];

		$sql = "UPDATE tb_pegawai SET nama = '$name',posisi = '$desg', gaji = '$salary' WHERE id = '$id';";

		require_once('koneksi.php');

		if (mysqli_query($con,$sql)) {
			echo "Berhasil";
		}else{
			echo mysqli_error();
		}

		mysqli_close($con);
	}
?>