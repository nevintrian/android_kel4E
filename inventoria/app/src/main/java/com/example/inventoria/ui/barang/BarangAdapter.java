package com.example.inventoria.ui.barang;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.inventoria.R;
import com.example.inventoria.model.Barang;
import com.example.inventoria.tools.Url;


import java.util.List;

public class BarangAdapter extends RecyclerView.Adapter<BarangAdapter.ViewHolder> {

    List<Barang> barangs;
    Context mContext;

    public BarangAdapter(List<Barang> barangs, Context context) {
        mContext = context;
        this.barangs = barangs;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_barang,
                viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Barang barang = barangs.get(i);
        viewHolder.nama_barang.setText(barang.getNama_barang());
        viewHolder.jenis.setText(barang.getJenis());
        viewHolder.merk.setText(barang.getMerk());
        viewHolder.harga.setText(barang.getHarga());
        viewHolder.kemasan.setText(barang.getKemasan());
        viewHolder.stok.setText(barang.getStok());
        viewHolder.terjual.setText(barang.getTerjual());

        String URL = Url.URL + "image/barang/";

        Glide.with(mContext).load(URL + barang.getFoto_barang())
                .thumbnail(0.5f)
                .transition(new DrawableTransitionOptions().crossFade())
                .into(viewHolder.foto_barang);

    }

    @Override
    public int getItemCount() {
        return barangs.size();
    }

    public Barang getBarang(int position) {
        return barangs.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nama_barang, jenis, merk, harga, stok, kemasan, terjual;
        ImageView foto_barang;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nama_barang = itemView.findViewById(R.id.nama_barang);
            jenis = itemView.findViewById(R.id.jenis);
            merk = itemView.findViewById(R.id.merk);
            harga = itemView.findViewById(R.id.harga);
            kemasan = itemView.findViewById(R.id.kemasan);
            stok = itemView.findViewById(R.id.stok);
            terjual = itemView.findViewById(R.id.terjual);
            foto_barang = itemView.findViewById(R.id.foto_barang);
        }
    }
}
