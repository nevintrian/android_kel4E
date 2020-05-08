package com.example.inventoria.ui.sales;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.inventoria.R;
import com.example.inventoria.model.User;
import com.example.inventoria.tools.RecyclerItemClickListener;
import com.example.inventoria.tools.SessionManager;
import com.example.inventoria.network.response.UserResponse;
import com.example.inventoria.tools.SimpleDividerItemDecoration;
import com.example.inventoria.ui.sales.editor.SalesActivity;
import com.example.inventoria.ui.user.UserAdapter;
import com.example.inventoria.ui.user.UserPresenter;
import com.example.inventoria.ui.user.UserView;
import com.example.inventoria.ui.user.editor.UserActivity;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class SalesFragment extends Fragment implements SalesView {

    SalesPresenter presenter;
    SessionManager session;
    com.example.inventoria.ui.sales.SalesAdapter adapter;
    List<User> saless;

    private static final int REQUEST_ADD = 1;
    private static final int REQUEST_UPDATE = 1;

    @BindView(R.id.recyclerUser)
    RecyclerView recyclerView;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipe;


    public SalesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View x = inflater.inflate(R.layout.fragment_sales, container, false);
        session = new SessionManager(getActivity());
        ButterKnife.bind(this, x);
        getActivity().setTitle("Data Sales");

        onSetRecyclerView();
        onClickRecylerView();

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getSaless();
            }
        });
        presenter.getSaless();

        return x;
    }

    @OnClick(R.id.userFab) void editor() {
        Intent intent = new Intent(getActivity(), SalesActivity.class);
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
    public void statusSuccess(UserResponse userResponse) {
        saless.removeAll(saless);
        saless.addAll(userResponse.getData());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void loadMore(UserResponse userResponse) {
        saless.remove(saless.size()-1);
        List<User> result = userResponse.getData();
        if (result.size() > 0) {
            saless.addAll(result);
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
            presenter.getSaless();
        } else if (requestCode == REQUEST_UPDATE && resultCode == RESULT_OK) {
            presenter.getSaless();
        }
    }

    void onSetRecyclerView() {
        saless = new ArrayList<>();
        presenter = new SalesPresenter(this);
        adapter = new com.example.inventoria.ui.sales.SalesAdapter(saless);
        adapter.setLoadMoreListener(new SalesAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        int index = saless.size();
                        saless.add(new User("load"));
                        adapter.notifyItemInserted(saless.size()-1);

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
                        User user = adapter.getSales(position);

                        Intent intent = new Intent(getActivity(), SalesActivity.class);

                        intent.putExtra("id_user", user.getId_user());
                        intent.putExtra("email", user.getEmail());
                        intent.putExtra("username", user.getUsername());
                        intent.putExtra("password", user.getPassword());
                        intent.putExtra("level", user.getLevel());
                        intent.putExtra("nama", user.getNama());
                        intent.putExtra("tgl_lahir", user.getTgl_lahir());
                        intent.putExtra("jenis_kelamin", user.getJenis_kelamin());
                        intent.putExtra("no_telp", user.getNo_telp());
                        intent.putExtra("alamat", user.getAlamat());

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
