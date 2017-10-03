package com.example.pepey.crudmysql;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class TampilSemuaPgw extends AppCompatActivity implements ListView.OnItemClickListener{

    private  ListView listView;
    private String JSON_STRING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_semua_pgw);

        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(this);
        getJSON();
    }

    private void showEmployee(){

        JSONObject jsonObject;
        ArrayList<HashMap<String,String>> list = new ArrayList<>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY);

            for (int i = 0; i < result.length();i++){

                JSONObject object = result.getJSONObject(i);
                String id = object.getString(konfigurasi.TAG_ID);
                String name = object.getString(konfigurasi.TAG_NAMA);

                HashMap<String,String> employees = new HashMap<>();
                employees.put(konfigurasi.TAG_ID,id);
                employees.put(konfigurasi.TAG_NAMA,name);
                list.add(employees);
            }
        } catch (JSONException e) {
            new AlertDialog.Builder(TampilSemuaPgw.this)
                    .setMessage(e.getMessage())
                    .show();
        }catch (NullPointerException e){
            new AlertDialog.Builder(TampilSemuaPgw.this)
                    .setMessage(e.getMessage())
                    .show();
        }

        ListAdapter adapter = new SimpleAdapter(
                TampilSemuaPgw.this,list,R.layout.list_item,
                new String[] {konfigurasi.TAG_ID,konfigurasi.TAG_NAMA},
                new int[] {R.id.id,R.id.name});
        listView.setAdapter(adapter);
    }

    private void getJSON(){

        class GetJSON extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(
                        TampilSemuaPgw.this,
                        "Mengambil Data",
                        "Mohon tunggu...",
                        false,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showEmployee();
            }

            @Override
            protected String doInBackground(Void... voids) {
                RequestHandler rh = new RequestHandler();
                String s =rh.sendGetRequest(konfigurasi.URL_GET_ALL,TampilSemuaPgw.this);
                return s;
            }
        }

        GetJSON js = new GetJSON();
        js.execute();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(this,TampilPegawai.class);
        HashMap<String,String> map = (HashMap)adapterView.getItemAtPosition(i);
        intent.putExtra(konfigurasi.EMP_ID,map.get(konfigurasi.TAG_ID).toString());
        startActivity(intent);
    }

}
