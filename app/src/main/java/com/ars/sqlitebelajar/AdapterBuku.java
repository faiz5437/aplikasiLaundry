package com.ars.sqlitebelajar;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterBuku extends RecyclerView.Adapter<AdapterBuku.ViewHolderBuku>{
    private Context ctx;
    private ArrayList arrID, arrJudul, arrPenulis, arrTahun;

    public AdapterBuku(Context ctx, ArrayList arID,ArrayList arJudul, ArrayList aarPenulis, ArrayList arTahun) {
        this.ctx = ctx;
        this.arrID = arID;
        this.arrJudul = arJudul;
        this.arrPenulis = aarPenulis;
        this.arrTahun = arTahun;
    }

//      oncreate view holder an onbind viwe holder dibuat saat clas AdapterBuku extends RecyclerView.Adapter<AdapterBuku.ViewHolderBuku> dan
//      dan viewHolderBuku dibuat setelah itu ada eror, klik create method


    @NonNull
    @Override
    public ViewHolderBuku onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflater adalah sebuah method untuk memompa card_item ke recyclerView
        LayoutInflater pompa = LayoutInflater.from(ctx);
        View view = pompa.inflate(R.layout.card_item, parent, false);
        //disini card view dan recycler view sudah bersatu
        return new ViewHolderBuku(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderBuku holder, @SuppressLint("RecyclerView") int position) {
        holder.tvID.setText(arrID.get(position).toString());
        holder.tvJudul.setText(arrJudul.get(position).toString()); //holder ini integer mkannya harus pakai to string
        holder.tvPenulis.setText(arrPenulis.get(position).toString());
        holder.tvTahun.setText(arrTahun.get(position).toString());
        holder.cvBuku.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder jendelaPesan = new AlertDialog.Builder(ctx);
                jendelaPesan.setMessage("Pilihan");
//                jendelaPesan.setTitle("Perhatian");
                jendelaPesan.setCancelable(true);

                jendelaPesan.setNegativeButton("Hapus", new DialogInterface.OnClickListener() { // negative button, button sblah kirinya kalau positf kanan
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SQLHelper myDB = new SQLHelper(ctx);
                        long eksekusiHapus = myDB.hapusBuku(arrID.get(position).toString());

                        if (eksekusiHapus == -1) {
                            Toast.makeText(ctx, "Gagal Menghapus Data Buku", Toast.LENGTH_SHORT).show();

                        }else {
                            Toast.makeText(ctx, "Buku Berhasil Dihapus", Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(TambahActivity.this, MainActivity.class)); ini tidak d pakai karna stlah menambah data, ada finish, dan finish itu
//                        hanya activity nya saja yg d finish, slanjutnya bkal kmbali otomatis k main activity
                            if (position == 0) {
                                MainActivity.posisiData = 0;
                            } else {
                                MainActivity.posisiData = position -1;
                            }
                            dialogInterface.dismiss();
                            ((MainActivity) ctx).onResume();
                        }

                    }
                });

                jendelaPesan.setPositiveButton("Ubah", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intentUbah = new Intent(ctx, UbahActivity.class);
                        intentUbah.putExtra("varID", arrID.get(position).toString());
                        intentUbah.putExtra("varJudul", arrJudul.get(position).toString());
                        intentUbah.putExtra("varPenulis", arrPenulis.get(position).toString());
                        intentUbah.putExtra("varTahun", arrTahun.get(position).toString());
                        intentUbah.putExtra("varPosisi", position);

                        ctx.startActivity(intentUbah);//start avtivity haru pake contex karna bkan class activity

                    }
                });

                jendelaPesan.show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrJudul.size();
    } //view holder yang ngehold data per view d layout


    public class ViewHolderBuku extends RecyclerView.ViewHolder{
        TextView tvID,tvJudul, tvPenulis, tvTahun;
        CardView cvBuku; //cv buku dari id car_item

        public ViewHolderBuku(@NonNull View itemView) {
            super(itemView);
            //view finder tida bsa langsung ke findview, tp harus lwat itemView.finView
            tvID = itemView.findViewById(R.id.tv_id);
            tvJudul = itemView.findViewById(R.id.tv_judul);
            tvPenulis = itemView.findViewById(R.id.tv_penulis);
            tvTahun = itemView.findViewById(R.id.tv_tahun);
            cvBuku = itemView.findViewById(R.id.cv_buku);

        }
    }
}
