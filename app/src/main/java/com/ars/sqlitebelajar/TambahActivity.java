package com.ars.sqlitebelajar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TambahActivity extends AppCompatActivity {
    private EditText etJudul, etPenulis, etTahun;
    private Button btnSimpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);

        etJudul = findViewById(R.id.et_judul_tambah);
        etPenulis = findViewById(R.id.et_penulis_tambah);
        etTahun = findViewById(R.id.et_tahun_tambah);
        btnSimpan = findViewById(R.id.btn_simpan);

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // di tampung dulu
                String getJudul = etJudul.getText().toString(); //trim itu menghilangkan spasi
                String getPenulis = etPenulis.getText().toString();
                String getTahun = etTahun.getText().toString();

                if (getJudul.trim().equals("")) {
                    etJudul.setError("Judul Tidak Boleh Kosong");
                }else if(getPenulis.trim().equals("")){
                    etPenulis.setError("Penulis Tidak Boleh Kosong");

                }else if(getTahun.trim().equals("")){
                    etTahun.setError("Tahun Tidak Boleh Kosong");
                }else {
                    SQLHelper myDB = new SQLHelper(TambahActivity.this);
                    long eksekusi = myDB.TambahBuku(getJudul, getPenulis, Integer.valueOf(getTahun));
                    if (eksekusi == -1) {
                        Toast.makeText(TambahActivity.this, "Gagal Menambah Data Buku", Toast.LENGTH_SHORT).show();
                        etJudul.requestFocus();
                    }else {
                        Toast.makeText(TambahActivity.this, "Buku Berhasil Ditambah", Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(TambahActivity.this, MainActivity.class)); ini tidak d pakai karna stlah menambah data, ada finish, dan finish itu
//                        hanya activity nya saja yg d finish, slanjutnya bkal kmbali otomatis k main activity
                        finish();
                    }
                }
            }
        });
    }
}