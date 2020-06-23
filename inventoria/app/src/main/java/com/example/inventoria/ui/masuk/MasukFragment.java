package com.example.inventoria.ui.masuk;


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

import com.example.inventoria.R;
import com.example.inventoria.model.Masuk;
import com.example.inventoria.tools.RecyclerItemClickListener;
import com.example.inventoria.tools.SessionManager;
import com.example.inventoria.network.response.MasukResponse;
import com.example.inventoria.tools.SimpleDividerItemDecoration;
import com.example.inventoria.tools.Url;
import com.example.inventoria.ui.masuk.MasukAdapter;
import com.example.inventoria.ui.masuk.MasukPresenter;
import com.example.inventoria.ui.masuk.MasukView;
import com.example.inventoria.ui.masuk.editor.MasukActivity;
import com.example.inventoria.ui.masuk.search.SearchActivity;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class MasukFragment extends Fragment implements MasukView {

    MasukPresenter presenter;
    SessionManager session;
    com.example.inventoria.ui.masuk.MasukAdapter adapter;
    List<Masuk> masuks;

    private static final int REQUEST_ADD = 1;
    private static final int REQUEST_UPDATE = 1;

    @BindView(R.id.recyclerMasuk)
    RecyclerView recyclerView;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipe;


    public MasukFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View x = inflater.inflate(R.layout.fragment_masuk, container, false);
        session = new SessionManager(getActivity());
        ButterKnife.bind(this, x);
        getActivity().setTitle("Transaksi Masuk");

        onSetRecyclerView();
        onClickRecylerView();
        setHasOptionsMenu(true);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getMasuks();
            }
        });
        presenter.getMasuks();

        return x;
    }

    @OnClick(R.id.masukFab) void editor() {
        Intent intent = new Intent(getActivity(), MasukActivity.class);
        startActivityForResult(intent, REQUEST_ADD);
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
    public void statusSuccess(MasukResponse masukResponse) {
        masuks.removeAll(masuks);
        masuks.addAll(masukResponse.getData());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void loadMore(MasukResponse masukResponse) {
        masuks.remove(masuks.size()-1);
        List<Masuk> result = masukResponse.getData();
        if (result.size() > 0) {
            masuks.addAll(result);
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
            presenter.getMasuks();
        } else if (requestCode == REQUEST_UPDATE && resultCode == RESULT_OK) {
            presenter.getMasuks();
        }
    }

    void onSetRecyclerView() {
        masuks = new ArrayList<>();
        presenter = new MasukPresenter(this);
        adapter = new com.example.inventoria.ui.masuk.MasukAdapter(masuks);
        adapter.setLoadMoreListener(new MasukAdapter.OnLoadMoreListener() {
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
                        Masuk masuk = adapter.getMasuk(position);

                        Intent intent = new Intent(getActivity(), MasukActivity.class);

                        intent.putExtra("id_masuk", masuk.getId_masuk());
                        intent.putExtra("id_barang", masuk.getId_barang());
                        intent.putExtra("id_supplier", masuk.getId_supplier());
                        intent.putExtra("qty_masuk", masuk.getQty_masuk());
                        intent.putExtra("total_masuk", masuk.getTotal_masuk());

                        startActivityForResult(intent, REQUEST_UPDATE);
                    }
                }));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search, menu);
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.search) {
            startActivity(new Intent(getActivity(), SearchActivity.class));
        } else if (item.getItemId() == R.id.cetak) {

            Intent intent = new Intent();
            intent.setDataAndType(Uri.parse(Url.URL + "masuk/cetak_pdf"), "application/pdf");
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
