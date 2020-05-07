package com.example.inventoria.ui.supplier;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventoria.R;
import com.example.inventoria.model.Supplier;


import java.util.List;


public class SupplierAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public final int TYPE_LIST = 0 ;
    public final int TYPE_LOAD = 1 ;

    private List<Supplier> suppliers;
    OnLoadMoreListener loadMoreListener;
    boolean isLoading = false;
    boolean isMoreDataAvailable = true;

    public SupplierAdapter(List<Supplier> suppliers) {
        this.suppliers = suppliers;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        if (i == TYPE_LIST) {
            return new ListHolder(inflater.inflate(R.layout.list_supplier, viewGroup, false));
        } else {
            return new LoadHolder(inflater.inflate(R.layout.list_loading, viewGroup, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Supplier supplier = suppliers.get(i);

        if (i >= getItemCount()-1 && isMoreDataAvailable && !isLoading && loadMoreListener !=
                null) {
            isLoading = true;
            loadMoreListener.onLoadMore();
        }

        if (getItemViewType(i) == TYPE_LIST){
            ListHolder listHolder = (ListHolder) viewHolder;
            listHolder.nama_supplier.setText(supplier.getNama_supplier());
            listHolder.no_telp.setText(supplier.getNo_telp());
            listHolder.alamat.setText(supplier.getAlamat());
        }
    }

    @Override
    public int getItemViewType(int position) {
        return (position == suppliers.size()) ? TYPE_LOAD : TYPE_LIST;
    }

    @Override
    public int getItemCount() {
        return suppliers.size();
    }

    public Supplier getSupplier(int position) {
        return suppliers.get(position);
    }

    static class ListHolder extends RecyclerView.ViewHolder {
        TextView nama_supplier, no_telp, alamat;

        public ListHolder(@NonNull View itemView) {
            super(itemView);
            nama_supplier = itemView.findViewById(R.id.nama_supplier);
            no_telp = itemView.findViewById(R.id.no_telp);
            alamat = itemView.findViewById(R.id.alamat);
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
