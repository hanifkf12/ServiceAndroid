package com.lauwba.cobaservice;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.lauwba.cobaservice.Handler.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity.class" ;
    private static final String URL_GET="http://192.168.100.11/webservice/index.php/Barang";

    private String JSON_STRING;
    ConnectivityManager connMgr;
    NetworkInfo networkInfo;
    ListView listView;
    BarangAdapter adapter;
    ArrayList<Barang> barangs;
    Button tambah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        barangs=new ArrayList<>();
        listView=(ListView)findViewById(R.id.list_view);
        tambah = (Button)findViewById(R.id.add);
        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),TambahData.class));
            }
        });
        getJSON();

//        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();
//        AndroidNetworking.initialize(getApplicationContext(),okHttpClient);
//
//        AndroidNetworking.get("http://192.168.100.11/webservice/index.php/Barang")
//                .setTag("test")
//                .setPriority(Priority.MEDIUM)
//                .build()
//                .getAsJSONObject(new JSONObjectRequestListener(){
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        // do anything with response
//                        try{
//                            JSONArray array = response.getJSONArray("isi_data");
//                            for (int i=0;i<array.length();i++){
//                                JSONObject data=array.getJSONObject(i);
//                                String nama=data.getString("nama");
//                                String harga=data.getString("harga");
//                                String jumlah=data.getString("jumlah");
//                                barangs.add(new Barang(nama,harga,jumlah));
//
//                            }
//                            adapter=new BarangAdapter(getApplicationContext(),barangs);
//                            listView.setAdapter(adapter);
//                        }catch (Exception e){
//                            Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//
//                    @Override
//                    public void onError(ANError error) {
//                        if (error.getErrorCode() != 0) {
//                            Log.e(TAG, "onError errorCode : " + error.getErrorCode());
//                            Log.e(TAG, "onError errorBody : " + error.getErrorBody());
//                            Log.e(TAG, "onError errorDetail : " + error.getErrorDetail());
//                        } else {
//                            Log.e(TAG, "onError errorDetail : " + error.getErrorDetail());
//                        }
//                    }
//                });



    }
    private void showBarang(){
        JSONObject jsonObject=null;
        try{
            jsonObject=new JSONObject(JSON_STRING);
            JSONArray result=jsonObject.getJSONArray("isi_data");
            for (int i=0;i<result.length();i++){
                JSONObject data=result.getJSONObject(i);
                String nama=data.getString("nama");
                String harga=data.getString("harga");
                String jumlah=data.getString("jumlah");
                barangs.add(new Barang(nama,harga,jumlah));


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapter = new BarangAdapter(getApplicationContext(),barangs);
        listView.setAdapter(adapter);
    }

    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading=ProgressDialog.show(MainActivity.this,"Mengambil Data",
                        "Tunggu Dulu......",false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING=s;
                showBarang();

            }

            @Override
            protected String doInBackground(Void... voids) {
                RequestHandler rh=new RequestHandler();
                String s=rh.sendGetRequest(URL_GET);
                return s;
            }
        }
        GetJSON gj=new GetJSON();
        gj.execute();
    }
}
