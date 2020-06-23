package com.example.inventoria.ui.keluar.editor;


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
import com.example.inventoria.model.User;

import java.util.List;

public class SpinnerUserAdapter extends ArrayAdapter<String> {

    private final LayoutInflater mInflater;
    private final Context mContext;
    private final List<User> users;
    private final int mResource;

    public SpinnerUserAdapter(@NonNull Context context, @LayoutRes int resource,
                              @NonNull List objects) {
        super(context, resource, 0, objects);

        mContext = context;
        mInflater = LayoutInflater.from(context);
        mResource = resource;
        users = objects;
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


    public int getItemIndexById(String user_id) {
        for (User user : this.users) {
            if(user.getId_user().toString().equals(user_id.toString())){
                return this.users.indexOf(user);
            }
        }
        return 0;
    }

    private View createItemView(int position, View convertView, ViewGroup parent){
        final View view = mInflater.inflate(mResource, parent, false);

        TextView id_user = (TextView) view.findViewById(R.id.id_user);
        TextView nama = (TextView) view.findViewById(R.id.nama);


        User user = users.get(position);

        id_user.setText(user.getId_user());
        nama.setText(user.getNama());


        return view;
    }
}
