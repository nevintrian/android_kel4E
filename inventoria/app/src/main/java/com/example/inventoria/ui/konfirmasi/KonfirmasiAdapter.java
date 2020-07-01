package com.example.inventoria.ui.konfirmasi;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventoria.R;
import com.example.inventoria.model.Keluar;


import java.util.List;


public class KonfirmasiAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public final int TYPE_LIST = 0 ;
    public final int TYPE_LOAD = 1 ;

    private List<Keluar> keluars;
    OnLoadMoreListener loadMoreListener;
    boolean isLoading = false;
    boolean isMoreDataAvailable = true;

    public KonfirmasiAdapter(List<Keluar> keluars) {
        this.keluars = keluars;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        if (i == TYPE_LIST) {
            return new ListHolder(inflater.inflate(R.layout.list_konfirmasi, viewGroup, false));
        } else {
            return new LoadHolder(inflater.inflate(R.layout.list_loading, viewGroup, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Keluar keluar = keluars.get(i);

        if (i >= getItemCount()-1 && isMoreDataAvailable && !isLoading && loadMoreListener !=
                null) {
            isLoading = true;
            loadMoreListener.onLoadMore();
        }

        if (getItemViewType(i) == TYPE_LIST){
            ListHolder listHolder = (ListHolder) viewHolder;
            listHolder.id_keluar.setText(keluar.getId_keluar());
            listHolder.nama_barang.setText(keluar.getNama_barang());
            listHolder.nama.setText(keluar.getNama());
            listHolder.tgl_keluar.setText(keluar.getTgl_keluar());
            listHolder.qty_keluar.setText(keluar.getQty_keluar());
            listHolder.total_keluar.setText(keluar.getTotal_keluar());
        }
    }

    @Override
    public int getItemViewType(int position) {
        return (position == keluars.size()) ? TYPE_LOAD : TYPE_LIST;
    }

    @Override
    public int getItemCount() {
        return keluars.size();
    }

    public Keluar getKeluar(int position) {
        return keluars.get(position);
    }

    static class ListHolder extends RecyclerView.ViewHolder {
        TextView id_keluar, nama_barang, nama, tgl_keluar, qty_keluar, total_keluar;

        public ListHolder(@NonNull View itemView) {
            super(itemView);
            id_keluar = itemView.findViewById(R.id.id_keluar);
            nama_barang = itemView.findViewById(R.id.nama_barang);
            nama = itemView.findViewById(R.id.nama);
            tgl_keluar = itemView.findViewById(R.id.tgl_keluar);
            qty_keluar = itemView.findViewById(R.id.qty_keluar);
            total_keluar = itemView.findViewById(R.id.total_keluar);
        }
    }

    static class LoadHolder extends RecyclerView.ViewHolder {
        public LoadHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public void setMoreDataAvailable(boolean moreDataAvailable) {
        isMoreDataAvailable = moreDataAvailable;
    }

    public void notifyDataChanged() {
        notifyDataSetChanged();
        isLoading =  false;
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public void setLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }

}
