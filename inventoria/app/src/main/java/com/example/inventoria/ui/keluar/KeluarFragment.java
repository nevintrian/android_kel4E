package com.example.inventoria.ui.keluar;


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
import com.example.inventoria.ui.keluar.KeluarAdapter;
import com.example.inventoria.ui.keluar.KeluarPresenter;
import com.example.inventoria.ui.keluar.KeluarView;
import com.example.inventoria.ui.keluar.editor.KeluarActivity;
import com.example.inventoria.ui.keluar.search.SearchActivity;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class KeluarFragment extends Fragment implements KeluarView {

    KeluarPresenter presenter;
    SessionManager session;
    KeluarAdapter adapter;
    List<Keluar> keluars;

    private static final int REQUEST_ADD = 1;
    private static final int REQUEST_UPDATE = 1;

    @BindView(R.id.recyclerKeluar)
    RecyclerView recyclerView;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipe;


    public KeluarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View x = inflater.inflate(R.layout.fragment_keluar, container, false);
        session = new SessionManager(getActivity());
        ButterKnife.bind(this, x);
        getActivity().setTitle("Transaksi Keluar");
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

    @OnClick(R.id.keluarFab) void editor() {
        Intent intent = new Intent(getActivity(), KeluarActivity.class);
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
        presenter = new KeluarPresenter(this);
        adapter = new KeluarAdapter(keluars);
        adapter.setLoadMoreListener(new KeluarAdapter.OnLoadMoreListener() {
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

                        Intent intent = new Intent(getActivity(), KeluarActivity.class);

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
            intent.setDataAndType(Uri.parse(Url.URL + "keluar/cetak_pdf"), "application/pdf");
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

}
