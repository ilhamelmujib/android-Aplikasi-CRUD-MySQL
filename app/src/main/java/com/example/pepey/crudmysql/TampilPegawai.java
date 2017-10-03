package com.example.pepey.crudmysql;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class TampilPegawai extends AppCompatActivity implements View.OnClickListener{

    private EditText eid,enama,epos,egaj;
    private Button bedit,bhapus;

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_pegawai);

        eid = (EditText) findViewById(R.id.editTextId);
        enama = (EditText) findViewById(R.id.editTextName);
        epos = (EditText) findViewById(R.id.editTextDesg);
        egaj = (EditText) findViewById(R.id.editTextSalary);

        bedit = (Button) findViewById(R.id.buttonUpdate);
        bhapus = (Button) findViewById(R.id.buttonDelete);

        bedit.setOnClickListener(this);
        bhapus.setOnClickListener(this);

        id = getIntent().getStringExtra(konfigurasi.EMP_ID);
        eid.setText(id);
        getEmployee();
    }

    private void getEmployee(){
        class GetEmployee extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(
                        TampilPegawai.this,"Mengambil Data",
                        "Tunggu Sebentar...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showEmployee(s);
            }

            @Override
            protected String doInBackground(Void... voids) {
                RequestHandler rh = new RequestHandler();
                return rh.sendGetRequestParam(konfigurasi.URL_GET_EMP,id);
            }
        }
        new GetEmployee().execute();
    }

    private void showEmployee(String json){
        try {

            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);
            enama.setText(c.getString(konfigurasi.TAG_NAMA));
            epos.setText(c.getString(konfigurasi.TAG_POSISI));
            egaj.setText(c.getString(konfigurasi.TAG_GAJI));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void updateEmployee(){
        final String nama = enama.getText().toString().trim();
        final String posisi = epos.getText().toString().trim();
        final String gaji = egaj.getText().toString().trim();

        class UpdateEmployee extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(
                        TampilPegawai.this,"Mengubah",
                        "Tunggu Sebentar...",false,false
                );

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(TampilPegawai.this, s, Toast.LENGTH_SHORT).show();
            }

            @Override
            protected String doInBackground(Void... voids) {

                HashMap<String,String> map = new HashMap<>();
                map.put(konfigurasi.KEY_EMP_ID,id);
                map.put(konfigurasi.KEY_EMP_NAMA,nama);
                map.put(konfigurasi.KEY_EMP_POSISI,posisi);
                map.put(konfigurasi.KEY_EMP_GAJI,gaji);
                RequestHandler rh = new RequestHandler();
                return rh.sendPostRequest(konfigurasi.URL_UPDATE_EMP,map);

            }
        }

        new UpdateEmployee().execute();
    }

    private void deleteEmployee(){
        class DeleteEmpolyee extends AsyncTask<Void,Void,String>{

            ProgressDialog progressDialog;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(
                        TampilPegawai.this,"Menghapus Data",
                        "Tunggu Sebentar",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressDialog.dismiss();
                Toast.makeText(TampilPegawai.this, s, Toast.LENGTH_SHORT).show();
            }

            @Override
            protected String doInBackground(Void... voids) {
                RequestHandler rh = new RequestHandler();
                return rh.sendGetRequestParam(konfigurasi.URL_DELETE_EMP,id);
            }
        }

        new DeleteEmpolyee().execute();
    }

    private void confirmDelete(){
        new AlertDialog.Builder(TampilPegawai.this)
                .setMessage("Hapus data pegawai ini ?")
                .setTitle("KONFIRMASI")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteEmployee();
                        startActivity(new Intent(TampilPegawai.this,TampilSemuaPgw.class));
                    }
                })
                .setNegativeButton("Tidak",null)
                .show();
    }

    @Override
    public void onClick(View view) {
        if (view == bedit){
            updateEmployee();
        }else if (view == bhapus){
            confirmDelete();
        }
    }
}
