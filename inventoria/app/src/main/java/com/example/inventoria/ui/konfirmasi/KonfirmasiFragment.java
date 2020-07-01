package com.example.inventoria.ui.konfirmasi;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.inventoria.MainActivity;
import com.example.inventoria.R;
import com.example.inventoria.model.Keluar;
import com.example.inventoria.tools.RecyclerItemClickListener;
import com.example.inventoria.tools.SessionManager;
import com.example.inventoria.network.response.KeluarResponse;
import com.example.inventoria.tools.SimpleDividerItemDecoration;
import com.example.inventoria.tools.Url;
import com.example.inventoria.ui.konfirmasi.KonfirmasiAdapter;

import com.example.inventoria.ui.konfirmasi.editor.KonfirmasiActivity;
import com.example.inventoria.ui.konfirmasi.search.SearchActivity;
import com.example.inventoria.ui.konfirmasi.editor.KonfirmasiActivity;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class KonfirmasiFragment extends Fragment implements KonfirmasiView {

    KonfirmasiPresenter presenter;
    SessionManager session;
    KonfirmasiAdapter adapter;
    List<Keluar> keluars;

    private static final int REQUEST_ADD = 1;
    private static final int REQUEST_UPDATE = 1;

    @BindView(R.id.recyclerKeluar)
    RecyclerView recyclerView;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipe;


    public KonfirmasiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View x = inflater.inflate(R.layout.fragment_konfirmasi, container, false);
        session = new SessionManager(getActivity());
        ButterKnife.bind(this, x);
        getActivity().setTitle("Konfirmasi Transaksi");
        setHasOptionsMenu(true);
        onSetRecyclerView();
        onClickRecylerView();

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getKeluars();
            }
        });
        presenter.getKeluars();

        return x;
    }


    @Override
    public void showProgress() {
        swipe.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        swipe.setRefreshing(false);
    }

    @Override
    public void statusSuccess(KeluarResponse keluarResponse) {
        keluars.removeAll(keluars);
        keluars.addAll(keluarResponse.getData());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void loadMore(KeluarResponse keluarResponse) {
        keluars.remove(keluars.size()-1);
        List<Keluar> result = keluarResponse.getData();
        if (result.size() > 0) {
            keluars.addAll(result);
        } else {
            adapter.setMoreDataAvailable(false);
        }
        adapter.notifyDataChanged();
    }

    @Override
    public void statusError(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ADD && resultCode == RESULT_OK) {
            presenter.getKeluars();
        } else if (requestCode == REQUEST_UPDATE && resultCode == RESULT_OK) {
            presenter.getKeluars();
        }
    }

    void onSetRecyclerView() {
        keluars = new ArrayList<>();
        presenter = new KonfirmasiPresenter(this);
        adapter = new KonfirmasiAdapter(keluars);
        adapter.setLoadMoreListener(new KonfirmasiAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {



                    }
                });
            }
        });
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    void onClickRecylerView() {
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(),
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Keluar keluar = adapter.getKeluar(position);

                        Intent intent = new Intent(getActivity(), KonfirmasiActivity.class);

                        intent.putExtra("id_keluar", keluar.getId_keluar());
                        intent.putExtra("id_barang", keluar.getId_barang());
                        intent.putExtra("id_user", keluar.getId_user());
                        intent.putExtra("qty_keluar", keluar.getQty_keluar());
                        intent.putExtra("total_keluar", keluar.getTotal_keluar());

                        startActivityForResult(intent, REQUEST_UPDATE);
                    }
                }));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }









}
