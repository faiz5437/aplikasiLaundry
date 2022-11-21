package com.ars.sqlitebelajar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class SQLHelper extends SQLiteOpenHelper {
    private Context ctx; //untuk mewakili acitivty d klas non klas activity
    private static final String DATABASE_NAME = "db_buku"; //static dan final supay tidak bsa d pkai d klas lain
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "tbl_buku";
    private static final String FIELD_ID = "id"; //otomatis auto increment
    private static final String FIELD_JUDUL = "judul";
    private static final String FIELD_PENULIS = "penulis";
    private static final String FIELD_TAHUN = "tahun";


    public SQLHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION); //ini konstruktor
        this.ctx = context; // ini konstruktor
    }

    @Override
    public void onCreate(SQLiteDatabase db) { // ketika di buka sudah d buat table ini
        String sql = "CREATE TABLE "+ TABLE_NAME +" ("+
               FIELD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                FIELD_JUDUL + " TEXT, " +
                FIELD_PENULIS+" TEXT, "+
                FIELD_TAHUN+" INTEGER);";
    // CREATE TABLE tbl_buku (
        // id INTEGER PRIMARY KEY AUTOINCREMENT,
        // judul TEXT,
        // penulis TEXT,
        //tahun INTEGER)

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME); //takutnya belum update jd d buat drop table
        onCreate(db);
    }
    //ini mereturn eksekusi toasnya d TambahActivity
    public long TambahBuku(String judul, String penulis, int tahun){
        SQLiteDatabase db = this.getWritableDatabase();//databseenya dsa d write bsa ngabus dan ubah
        ContentValues cv = new ContentValues();

        cv.put(FIELD_JUDUL, judul);
        cv.put(FIELD_PENULIS, penulis);
        cv.put(FIELD_TAHUN, tahun);

        long eksekusiInsert = db.insert(TABLE_NAME, null, cv);//db insert itu keluarannya long
        return eksekusiInsert;
    }

    public long ubahBuku (String id, String judul, String penulis, int tahun){
        SQLiteDatabase dbUbah = this.getWritableDatabase();
        ContentValues cvUbah = new ContentValues();

        cvUbah.put(FIELD_JUDUL, judul);
        cvUbah.put(FIELD_PENULIS, penulis);
        cvUbah.put(FIELD_TAHUN, tahun);

        long eksekusiUbah = dbUbah.update(TABLE_NAME, cvUbah, "id = ?", new String[]{id});
        return eksekusiUbah;

    }
    public long hapusBuku (String id){
        SQLiteDatabase dbHapus = this.getWritableDatabase();


        long eksekusiHapus = dbHapus.delete(TABLE_NAME,  "id = ?", new String[]{id});
        return eksekusiHapus;

    }
//    ini menghasilkan toast nya d krim k tambah acivity
//    public void TambahBuku(String judul, String penulis, int tahun){
//        SQLiteDatabase db = this.getWritableDatabase();//databseenya dsa d write bsa ngabus dan ubah
//        ContentValues cv = new ContentValues();
//
//        cv.put(FIELD_JUDUL, judul);
//        cv.put(FIELD_PENULIS, penulis);
//        cv.put(FIELD_TAHUN, tahun);
//
//        long eksekusiInsert = db.insert(TABLE_NAME, null, cv);//db insert itu keluarannya long
//        if (eksekusiInsert == -1) {
//            Toast.makeText(ctx, "Gagal Menambah Data Buku", Toast.LENGTH_SHORT).show();
//        }else {
//            Toast.makeText(ctx, "Buku Berhasil Ditambah", Toast.LENGTH_SHORT).show();
//        }
//    }
    public Cursor bacaSemuaData(){
        String query = "SELECT * FROM "+ TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query,null);

        }
        return cursor;
    }
}
