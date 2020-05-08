package com.example.inventoria.ui.sales;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventoria.R;
import com.example.inventoria.model.User;


import java.util.List;


public class SalesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public final int TYPE_LIST = 0 ;
    public final int TYPE_LOAD = 1 ;

    private List<User> saless;
    OnLoadMoreListener loadMoreListener;
    boolean isLoading = false;
    boolean isMoreDataAvailable = true;

    public SalesAdapter(List<User> saless) {
        this.saless = saless;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        if (i == TYPE_LIST) {
            return new ListHolder(inflater.inflate(R.layout.list_sales, viewGroup, false));
        } else {
            return new LoadHolder(inflater.inflate(R.layout.list_loading, viewGroup, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        User user = saless.get(i);

        if (i >= getItemCount()-1 && isMoreDataAvailable && !isLoading && loadMoreListener !=
                null) {
            isLoading = true;
            loadMoreListener.onLoadMore();
        }

        if (getItemViewType(i) == TYPE_LIST){
            ListHolder listHolder = (ListHolder) viewHolder;
            listHolder.nama.setText(user.getNama());
            listHolder.no_telp.setText(user.getNo_telp());
            listHolder.alamat.setText(user.getAlamat());
        }
    }

    @Override
    public int getItemViewType(int position) {
        return (position == saless.size()) ? TYPE_LOAD : TYPE_LIST;
    }

    @Override
    public int getItemCount() {
        return saless.size();
    }

    public User getSales(int position) {
        return saless.get(position);
    }

    static class ListHolder extends RecyclerView.ViewHolder {
        TextView nama, no_telp, alamat;

        public ListHolder(@NonNull View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.nama);
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
