package com.example.inventoria.ui.masuk;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventoria.R;
import com.example.inventoria.model.Masuk;


import java.util.List;


public class MasukAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public final int TYPE_LIST = 0 ;
    public final int TYPE_LOAD = 1 ;

    private List<Masuk> masuks;
    OnLoadMoreListener loadMoreListener;
    boolean isLoading = false;
    boolean isMoreDataAvailable = true;

    public MasukAdapter(List<Masuk> masuks) {
        this.masuks = masuks;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        if (i == TYPE_LIST) {
            return new ListHolder(inflater.inflate(R.layout.list_masuk, viewGroup, false));
        } else {
            return new LoadHolder(inflater.inflate(R.layout.list_loading, viewGroup, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Masuk masuk = masuks.get(i);

        if (i >= getItemCount()-1 && isMoreDataAvailable && !isLoading && loadMoreListener !=
                null) {
            isLoading = true;
            loadMoreListener.onLoadMore();
        }

        if (getItemViewType(i) == TYPE_LIST){
            ListHolder listHolder = (ListHolder) viewHolder;
            listHolder.id_masuk.setText(masuk.getId_masuk());
            listHolder.nama_barang.setText(masuk.getNama_barang());
            listHolder.nama_supplier.setText(masuk.getNama_supplier());
            listHolder.tgl_masuk.setText(masuk.getTgl_masuk());
            listHolder.qty_masuk.setText(masuk.getQty_masuk());
            listHolder.total_masuk.setText(masuk.getTotal_masuk());
        }
    }

    @Override
    public int getItemViewType(int position) {
        return (position == masuks.size()) ? TYPE_LOAD : TYPE_LIST;
    }

    @Override
    public int getItemCount() {
        return masuks.size();
    }

    public Masuk getMasuk(int position) {
        return masuks.get(position);
    }

    static class ListHolder extends RecyclerView.ViewHolder {
        TextView id_masuk, nama_barang, nama_supplier, tgl_masuk, qty_masuk, total_masuk;

        public ListHolder(@NonNull View itemView) {
            super(itemView);
            id_masuk = itemView.findViewById(R.id.id_masuk);
            nama_barang = itemView.findViewById(R.id.nama_barang);
            nama_supplier = itemView.findViewById(R.id.nama_supplier);
            tgl_masuk = itemView.findViewById(R.id.tgl_masuk);
            qty_masuk = itemView.findViewById(R.id.qty_masuk);
            total_masuk = itemView.findViewById(R.id.total_masuk);
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
