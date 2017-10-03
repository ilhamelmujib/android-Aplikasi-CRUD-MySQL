package com.example.pepey.crudmysql;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText enama,epos,egaji;
    private Button bsimpan,blihat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        enama   = (EditText) findViewById(R.id.editTextName);
        epos    = (EditText) findViewById(R.id.editTextDesg);
        egaji   = (EditText) findViewById(R.id.editTextSalary);

        bsimpan = (Button) findViewById(R.id.buttonAdd);
        blihat  = (Button) findViewById(R.id.buttonView);

        bsimpan.setOnClickListener(this);
        blihat.setOnClickListener(this);

    }


    private void reset(){

    }
    private void AddEmployee(){
        final String name = enama.getText().toString().trim();
        final String desg = epos.getText().toString().trim();
        final String sal  = egaji.getText().toString().trim();
        class AddEmployee extends AsyncTask<Void,Void,String >{

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(
                        MainActivity.this,
                        "Menambahkan...",
                        "Tunggu Sebentar...",
                        false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                reset();
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String,String> params = new HashMap<>();
                params.put(konfigurasi.KEY_EMP_NAMA,name);
                params.put(konfigurasi.KEY_EMP_POSISI,desg);
                params.put(konfigurasi.KEY_EMP_GAJI,sal);
                RequestHandler requestHandler = new RequestHandler();
                return requestHandler.sendPostRequest(konfigurasi.URL_ADD,params);
            }
        }

        new AddEmployee().execute();
    }

    @Override
    public void onClick(View view) {

        if (view == bsimpan){
            AddEmployee();
        }
        if (view == blihat){
            startActivity(new Intent(MainActivity.this,TampilSemuaPgw.class));
        }

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
