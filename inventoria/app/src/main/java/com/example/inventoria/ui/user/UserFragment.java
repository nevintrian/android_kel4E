package com.example.inventoria.ui.user;


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
import com.example.inventoria.model.User;
import com.example.inventoria.network.response.UserResponse;
import com.example.inventoria.tools.RecyclerItemClickListener;
import com.example.inventoria.tools.SessionManager;
import com.example.inventoria.tools.SimpleDividerItemDecoration;
import com.example.inventoria.tools.Url;
import com.example.inventoria.ui.user.search.SearchActivity;
import com.example.inventoria.ui.user.editor.UserActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment implements UserView {

    SessionManager session;
    UserPresenter presenter;
    UserAdapter adapter;

    private static final int REQUEST_ADD = 1;
    private static final int REQUEST_UPDATE = 2;

    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipe;

    public UserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View x = inflater.inflate(R.layout.fragment_user, container, false);
        ButterKnife.bind(this, x );
        getActivity().setTitle("Data User");

        session = new SessionManager(getActivity());
        presenter = new UserPresenter(this);
        presenter.getUser();
        setHasOptionsMenu(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getUser();
            }
        });

        return x;
    }

    @OnClick(R.id.fab) void editor() {
        Intent intent = new Intent(getActivity(), UserActivity.class);
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
        adapter = new UserAdapter(userResponse.getData(), getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(),
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        User user = adapter.getUser(position);

                        Intent intent = new Intent(getActivity(), UserActivity.class);

                        intent.putExtra("id_user", user.getId_user());
                        intent.putExtra("email", user.getEmail());
                        intent.putExtra("username", user.getUsername());
                        intent.putExtra("password", user.getPassword());
                        intent.putExtra("level", user.getLevel());
                        intent.putExtra("nama", user.getNama());
                        intent.putExtra("tgl_lahir", user.getTgl_lahir());
                        intent.putExtra("jenis_kelamin", user.getJenis_kelamin());
                        intent.putExtra("alamat", user.getAlamat());
                        intent.putExtra("no_telp", user.getNo_telp());
                        intent.putExtra("foto", user.getFoto());

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
            presenter.getUser();
        } else if (requestCode == REQUEST_UPDATE && resultCode == RESULT_OK) {
            presenter.getUser();
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
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.search) {
            startActivity(new Intent(getActivity(), SearchActivity.class));
        } else if (item.getItemId() == R.id.cetak) {

            Intent intent = new Intent();
            intent.setDataAndType(Uri.parse(Url.URL + "user/cetak_pdf"), "application/pdf");
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

}
