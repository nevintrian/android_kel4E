package com.example.inventoria.ui.supplier;


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
import com.example.inventoria.model.Supplier;
import com.example.inventoria.tools.RecyclerItemClickListener;
import com.example.inventoria.tools.SessionManager;
import com.example.inventoria.network.response.SupplierResponse;
import com.example.inventoria.tools.SimpleDividerItemDecoration;
import com.example.inventoria.tools.Url;
import com.example.inventoria.ui.supplier.search.SearchActivity;
import com.example.inventoria.ui.supplier.editor.SupplierActivity;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class SupplierFragment extends Fragment implements SupplierView{

    SupplierPresenter presenter;
    SessionManager session;
    SupplierAdapter adapter;
    List<Supplier> suppliers;

    private static final int REQUEST_ADD = 1;
    private static final int REQUEST_UPDATE = 1;

    @BindView(R.id.recyclerSupplier)
    RecyclerView recyclerView;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipe;


    public SupplierFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View x = inflater.inflate(R.layout.fragment_supplier, container, false);
        session = new SessionManager(getActivity());
        ButterKnife.bind(this, x);
        getActivity().setTitle("Data Supplier");
        setHasOptionsMenu(true);
        onSetRecyclerView();
        onClickRecylerView();

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getSuppliers();
            }
        });
        presenter.getSuppliers();

        return x;
    }

    @OnClick(R.id.supplierFab) void editor() {
        Intent intent = new Intent(getActivity(), SupplierActivity.class);
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
    public void statusSuccess(SupplierResponse supplierResponse) {
        suppliers.removeAll(suppliers);
        suppliers.addAll(supplierResponse.getData());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void loadMore(SupplierResponse supplierResponse) {
        suppliers.remove(suppliers.size()-1);
        List<Supplier> result = supplierResponse.getData();
        if (result.size() > 0) {
            suppliers.addAll(result);
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
            presenter.getSuppliers();
        } else if (requestCode == REQUEST_UPDATE && resultCode == RESULT_OK) {
            presenter.getSuppliers();
        }
    }

    void onSetRecyclerView() {
        suppliers = new ArrayList<>();
        presenter = new SupplierPresenter(this);
        adapter = new SupplierAdapter(suppliers);
        adapter.setLoadMoreListener(new SupplierAdapter.OnLoadMoreListener() {
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
                        Supplier supplier = adapter.getSupplier(position);

                        Intent intent = new Intent(getActivity(), SupplierActivity.class);

                        intent.putExtra("id_supplier", supplier.getId_supplier());
                        intent.putExtra("nama_supplier", supplier.getNama_supplier());
                        intent.putExtra("no_telp", supplier.getNo_telp());
                        intent.putExtra("alamat", supplier.getAlamat());

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
            intent.setDataAndType(Uri.parse(Url.URL + "supplier/cetak_pdf"), "application/pdf");
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
