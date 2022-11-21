package com.ars.sqlitebelajar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UbahActivity extends AppCompatActivity {
    private EditText etJudul, etPenulis, etTahun;
    private Button btnUbah;
    private String getID, getJudul, getPenulis, getTahun;
    public int getPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah);

        etJudul = findViewById(R.id.et_judul_ubah);
        etPenulis = findViewById(R.id.et_penulis_ubah);
        etTahun = findViewById(R.id.et_tahun_ubah);
        btnUbah = findViewById(R.id.btn_ubah);

        Intent terima = getIntent(); //nge get data yg d krim dari adapteractivity
        getID = terima.getStringExtra("varID");
        getJudul = terima.getStringExtra("varJudul");
        getPenulis = terima.getStringExtra("varPenulis");
        getTahun = terima.getStringExtra("varTahun");
        getPosition = terima.getIntExtra("varPosisi", -1);//default value -1 jdi kalau -1 dia ga bsa nerima posisi -1

        etJudul.setText(getJudul);
        etPenulis.setText(getPenulis);
        etTahun.setText(getTahun);

        btnUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtJudul = etJudul.getText().toString(); //trim itu menghilangkan spasi
                String txtPenulis = etPenulis.getText().toString();
                String txtTahun = etTahun.getText().toString();

                if (txtJudul.trim().equals("")) {
                    etJudul.setError("Judul Tidak Boleh Kosong");
                }else if(txtPenulis.trim().equals("")){
                    etPenulis.setError("Penulis Tidak Boleh Kosong");

                }else if(txtTahun.trim().equals("")){
                    etTahun.setError("Tahun Tidak Boleh Kosong");
                }else {
                    SQLHelper myDB = new SQLHelper(UbahActivity.this);
                    long eksekusi = myDB.ubahBuku(getID, txtJudul, txtPenulis, Integer.valueOf(txtTahun));
                    if (eksekusi == -1) {
                        Toast.makeText(UbahActivity.this, "Gagal Menambah Data Buku", Toast.LENGTH_SHORT).show();
                        etJudul.requestFocus();
                    }else {
                        Toast.makeText(UbahActivity.this, "Buku Berhasil Ditambah", Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(TambahActivity.this, MainActivity.class)); ini tidak d pakai karna stlah menambah data, ada finish, dan finish itu
//                        hanya activity nya saja yg d finish, slanjutnya bkal kmbali otomatis k main activity
                        MainActivity.posisiData = getPosition;
                        finish();
                    }
                }
            }
        });
    }
}