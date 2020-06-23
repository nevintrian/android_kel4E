package com.example.inventoria.ui.masuk.editor;


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
import com.example.inventoria.model.Supplier;

import java.util.List;

public class SpinnerSupplierAdapter extends ArrayAdapter<String> {

    private final LayoutInflater mInflater;
    private final Context mContext;
    private final List<Supplier> suppliers;
    private final int mResource;

    public SpinnerSupplierAdapter(@NonNull Context context, @LayoutRes int resource,
                                  @NonNull List objects) {
        super(context, resource, 0, objects);

        mContext = context;
        mInflater = LayoutInflater.from(context);
        mResource = resource;
        suppliers = objects;
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


    public int getItemIndexById(String id_supplier) {
        for (Supplier supplier : this.suppliers) {
            if(supplier.getId_supplier().toString().equals(id_supplier.toString())){
                return this.suppliers.indexOf(supplier);
            }
        }
        return 0;
    }

    private View createItemView(int position, View convertView, ViewGroup parent){
        final View view = mInflater.inflate(mResource, parent, false);

        TextView id_supplier = (TextView) view.findViewById(R.id.id_supplier);
        TextView nama_supplier = (TextView) view.findViewById(R.id.nama_supplier);


        Supplier supplier = suppliers.get(position);

        id_supplier.setText(supplier.getId_supplier());
        nama_supplier.setText(supplier.getNama_supplier());


        return view;
    }
}
