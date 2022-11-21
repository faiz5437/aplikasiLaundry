package com.ars.sqlitebelajar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
//    FloatingActionButton fab;
    private RecyclerView cvBuku;
    SQLHelper myDB;
    AdapterBuku adapterBuku;
    ArrayList<String> arrID, arrJudul, arrPenulis, arrTahun;
    public static int posisiData = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





    }

    public void bukaActivityTambah(View view) {
        startActivity(new Intent(MainActivity.this, TambahActivity.class));
    }
    private void ambilDataDariTableKeArrayList(){
        Cursor cursor = myDB.bacaSemuaData();

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "Tidak Ada Data", Toast.LENGTH_SHORT).show();
            
        }else {
            while (cursor.moveToNext()){
                arrID.add(cursor.getString(0));
                arrJudul.add(cursor.getString(1));//array ke 1 karna array ke 0 itu id
                arrPenulis.add(cursor.getString(2));
                arrTahun.add(cursor.getString(3));
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume(); // ini adalah android lifecycle
        myDB = new SQLHelper(MainActivity.this);
        arrID = new ArrayList<>();
        arrJudul = new ArrayList<>();
        arrPenulis = new ArrayList<>();
        arrTahun = new ArrayList<>();

        ambilDataDariTableKeArrayList();

        cvBuku = findViewById(R.id.rv_buku);
        adapterBuku = new AdapterBuku(MainActivity.this,arrID,arrJudul,arrPenulis,arrTahun);
        cvBuku.setAdapter(adapterBuku);
        cvBuku.setLayoutManager(new LinearLayoutManager(MainActivity.this));
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_tambah);
        cvBuku.smoothScrollToPosition(posisiData); // fungsi ini supaya langsung keliatan dan scroll k tmpat yg baru saja diubah
    }
}