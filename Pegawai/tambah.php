<?php  

	if ($_SERVER['REQUEST_METHOD'] == 'POST') {
		
		$name = $_POST['name'];
		$desg = $_POST['desg'];
		$salary = $_POST['salary'];

		$sql = "INSERT INTO tb_pegawai (nama,posisi,gaji) VALUES ('$name','$desg','$salary')";

		require_once('koneksi.php');

		if (mysqli_query($con,$sql)) {
			echo "Berhasil ditambah";
		}else{
			echo mysqli_error();
		}

		mysqli_close($con);
	}
?>