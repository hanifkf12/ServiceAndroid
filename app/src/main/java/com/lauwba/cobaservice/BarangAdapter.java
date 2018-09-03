package com.lauwba.cobaservice;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class BarangAdapter  extends ArrayAdapter<Barang>{
    public BarangAdapter(@NonNull Context context, ArrayList<Barang> list) {

        super(context,0, list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view=convertView;
        if(view==null){
            view= LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }

        Barang barang=getItem(position);

        TextView namabarang=(TextView)view.findViewById(R.id.nama);
        TextView hargabarang=(TextView)view.findViewById(R.id.harga);
        TextView jumlahbarang=(TextView)view.findViewById(R.id.jumlah);

        namabarang.setText(barang.getNama());
        hargabarang.setText(barang.getHarga());
        jumlahbarang.setText(barang.getJumlah());

        return view;
    }
}
