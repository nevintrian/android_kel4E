package com.example.inventoria.ui.sales;

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
import com.example.inventoria.model.User;
import com.example.inventoria.tools.Url;


import java.util.List;

public class SalesAdapter extends RecyclerView.Adapter<SalesAdapter.ViewHolder> {

    List<User> users;
    Context mContext;

    public SalesAdapter(List<User> users, Context context) {
        mContext = context;
        this.users = users;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_sales,
                viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        User user = users.get(i);
        viewHolder.email.setText(user.getEmail());
        viewHolder.username.setText(user.getUsername());
        viewHolder.level.setText(user.getLevel());
        viewHolder.nama.setText(user.getNama());
        viewHolder.tgl_lahir.setText(user.getTgl_lahir());
        viewHolder.jenis_kelamin.setText(user.getJenis_kelamin());
        viewHolder.alamat.setText(user.getAlamat());
        viewHolder.no_telp.setText(user.getNo_telp());

        String URL = Url.URL + "image/user/";

        Glide.with(mContext).load(URL + user.getFoto())
                .thumbnail(0.5f)
                .transition(new DrawableTransitionOptions().crossFade())
                .into(viewHolder.foto);

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public User getSales(int position) {
        return users.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView email, username, level, nama, tgl_lahir, jenis_kelamin, alamat, no_telp;
        ImageView foto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            email = itemView.findViewById(R.id.email);
            username = itemView.findViewById(R.id.username);

            level = itemView.findViewById(R.id.level);
            nama = itemView.findViewById(R.id.nama);
            tgl_lahir = itemView.findViewById(R.id.tgl_lahir);
            jenis_kelamin = itemView.findViewById(R.id.jenis_kelamin);
            alamat = itemView.findViewById(R.id.alamat);
            no_telp = itemView.findViewById(R.id.no_telp);
            foto = itemView.findViewById(R.id.foto);
        }
    }


}
