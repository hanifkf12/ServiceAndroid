package com.lauwba.cobaservice;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lauwba.cobaservice.Handler.RequestHandler;

import java.util.HashMap;

public class TambahData extends AppCompatActivity {

    private static final String URL_ADD="http://192.168.100.11/webservice/index.php/Barang/simpan";
    EditText editnama,editharga,editjumlah;
    Button simpanbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_data);
        editnama=findViewById(R.id.nama);
        editharga=findViewById(R.id.harga);
        editjumlah=findViewById(R.id.jumlah);
        simpanbtn=findViewById(R.id.save);

        simpanbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBarang();
            }
        });
    }

    private void addBarang(){
        final String nama=editnama.getText().toString().trim();
        final String harga=editharga.getText().toString().trim();
        final String jumlah=editjumlah.getText().toString().trim();

//        Double hrg=Double.parseDouble(editharga.toString());
//        int harr=Integer.parseInt(editharga.getText().toString());
        class AddBarang extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading=ProgressDialog.show(TambahData.this,"Simpan Data","Mohon Tunggu",false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(TambahData.this, s, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(TambahData.this,MainActivity.class));
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String,String> params=new HashMap<>();
                params.put("nama",nama);
                params.put("harga",harga);
                params.put("jumlah",jumlah);

                RequestHandler rh= new RequestHandler();

                String res=rh.sendPostRequest(URL_ADD,params);
                return res;
            }
        }
        AddBarang ab=new AddBarang();
        ab.execute();
    }
}
