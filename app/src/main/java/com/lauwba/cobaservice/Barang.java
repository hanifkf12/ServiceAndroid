package com.lauwba.cobaservice;

public class Barang {

    String nama,harga,jumlah;

    public Barang(String nama, String harga, String jumlah) {
        this.nama = nama;
        this.harga = harga;
        this.jumlah = jumlah;
    }

    public String getNama() {
        return nama;
    }

    public String getHarga() {
        return harga;
    }

    public String getJumlah() {
        return jumlah;
    }
}
