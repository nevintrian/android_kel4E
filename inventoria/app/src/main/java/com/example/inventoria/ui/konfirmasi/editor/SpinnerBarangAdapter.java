package com.example.inventoria.ui.konfirmasi.editor;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.inventoria.R;
import com.example.inventoria.model.Barang;

import java.util.List;

public class SpinnerBarangAdapter extends ArrayAdapter<String> {

    private final LayoutInflater mInflater;
    private final Context mContext;
    private final List<Barang> barangs;
    private final int mResource;

    public SpinnerBarangAdapter(@NonNull Context context, @LayoutRes int resource,
                                @NonNull List objects) {
        super(context, resource, 0, objects);

        mContext = context;
        mInflater = LayoutInflater.from(context);
        mResource = resource;
        barangs = objects;
    }


    @Override
    public View getDropDownView(int position, @Nullable View convertView,
                                @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    @Override
    public @NonNull
    View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }


    public int getItemIndexById(String barang_id) {
        for (Barang barang : this.barangs) {
            if(barang.getId_barang().toString().equals(barang_id.toString())){
                return this.barangs.indexOf(barang);
            }
        }
        return 0;
    }

    private View createItemView(int position, View convertView, ViewGroup parent){
        final View view = mInflater.inflate(mResource, parent, false);

        TextView id_barang = (TextView) view.findViewById(R.id.id_barang);
        TextView nama_barang = (TextView) view.findViewById(R.id.nama_barang);


        Barang barang = barangs.get(position);

        id_barang.setText(barang.getId_barang());
        nama_barang.setText(barang.getNama_barang());


        return view;
    }
}
