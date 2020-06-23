package com.example.inventoria.ui.home;


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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.inventoria.R;
import com.example.inventoria.model.Barang;
import com.example.inventoria.network.response.BarangResponse;
import com.example.inventoria.tools.RecyclerItemClickListener;
import com.example.inventoria.tools.SessionManager;
import com.example.inventoria.tools.SimpleDividerItemDecoration;
import com.example.inventoria.tools.Url;
import com.example.inventoria.ui.barang.BarangAdapter;
import com.example.inventoria.ui.barang.BarangPresenter;
import com.example.inventoria.ui.barang.BarangView;
import com.example.inventoria.ui.barang.editor.BarangActivity;

import com.example.inventoria.ui.home.editor.HomeActivity;
import com.example.inventoria.ui.home.search.SearchActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements HomeView {

    SessionManager session;
    HomePresenter presenter;
    com.example.inventoria.ui.home.HomeAdapter adapter;

    private static final int REQUEST_ADD = 1;
    private static final int REQUEST_UPDATE = 2;

    @BindView(R.id.recycler)
    RecyclerView recyclerView;



    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipe;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View x = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, x );
        getActivity().setTitle("Halaman Utama");
        setHasOptionsMenu(true);
        session = new SessionManager(getActivity());
        presenter = new HomePresenter(this);
        presenter.getBarang();

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2, RecyclerView.VERTICAL,false));

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getBarang();
            }
        });

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
    public void statusSuccess(BarangResponse barangResponse) {
        adapter = new HomeAdapter(barangResponse.getData(), getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(),
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Barang barang = adapter.getBarang(position);

                        Intent intent = new Intent(getActivity(), HomeActivity.class);

                        intent.putExtra("id_barang", barang.getId_barang());
                        intent.putExtra("id_supplier", barang.getId_supplier());
                        intent.putExtra("nama_barang", barang.getNama_barang());
                        intent.putExtra("kemasan", barang.getKemasan());
                        intent.putExtra("merk", barang.getMerk());
                        intent.putExtra("jenis", barang.getJenis());
                        intent.putExtra("stok", barang.getStok());
                        intent.putExtra("harga", barang.getHarga());
                        intent.putExtra("terjual", barang.getTerjual());
                        intent.putExtra("foto_barang", barang.getFoto_barang());

                        startActivityForResult(intent, REQUEST_UPDATE);
                    }
                }));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void statusError(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ADD && resultCode == RESULT_OK) {
            presenter.getBarang();
        } else if (requestCode == REQUEST_UPDATE && resultCode == RESULT_OK) {
            presenter.getBarang();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.search) {
            startActivity(new Intent(getActivity(), SearchActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
}
