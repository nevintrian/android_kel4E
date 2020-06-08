package com.example.inventoria.ui.profil;

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
import com.example.inventoria.tools.SessionManager;
import com.example.inventoria.tools.Url;

import java.util.List;

public class ProfilAdapter extends RecyclerView.Adapter<ProfilAdapter.ViewHolder> {
    SessionManager sessionManager;

    Context mContext;

    public ProfilAdapter(Context context) {
        mContext = context;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_profil,
                viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        sessionManager = new SessionManager(mContext.getApplicationContext());
        viewHolder.id_user.setText(sessionManager.getKeyId_user());
        viewHolder.email.setText(sessionManager.getKeyEmail());
        viewHolder.username.setText(sessionManager.getKeyUsername());
        viewHolder.password.setText(sessionManager.getKeyPassword());
        viewHolder.level.setText(sessionManager.getKeyLevel());
        viewHolder.nama.setText(sessionManager.getKeyNama());
        viewHolder.tgl_lahir.setText(sessionManager.getKeyTgl_lahir());
        viewHolder.jenis_kelamin.setText(sessionManager.getKeyJenis_kelamin());
        viewHolder.alamat.setText(sessionManager.getKeyAlamat());
        viewHolder.no_telp.setText(sessionManager.getKeyNo_telp());

        String URL = Url.URL + "image/user/";

        Glide.with(mContext).load(URL + sessionManager.getKeyFoto())
                .thumbnail(0.5f)
                .transition(new DrawableTransitionOptions().crossFade())
                .into(viewHolder.foto);

    }


    @Override
    public int getItemCount() {
        return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView id_user, email, username, password, level, nama, tgl_lahir, jenis_kelamin, alamat, no_telp;
        ImageView foto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id_user = itemView.findViewById(R.id.id_user);
            email = itemView.findViewById(R.id.email);
            username = itemView.findViewById(R.id.username);
            password = itemView.findViewById(R.id.password);
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
