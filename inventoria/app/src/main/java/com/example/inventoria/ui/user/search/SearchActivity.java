package com.example.inventoria.ui.user.search;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventoria.R;
import com.example.inventoria.model.User;
import com.example.inventoria.network.response.UserResponse;
import com.example.inventoria.tools.RecyclerItemClickListener;
import com.example.inventoria.tools.SessionManager;
import com.example.inventoria.tools.SimpleDividerItemDecoration;
import com.example.inventoria.ui.user.UserAdapter;
import com.example.inventoria.ui.user.editor.UserActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.OnTextChanged;

public class SearchActivity extends AppCompatActivity implements SearchView {

    SearchPresenter presenter;
    SessionManager session;
    UserAdapter adapter;

    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    @BindView(R.id.searchText)
    EditText et_searchText;
    @BindView(R.id.clear)
    ImageButton clear;
    @BindView(R.id.progress)
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        session = new SessionManager(this);
        presenter = new SearchPresenter(this);
        onClickRecylerView();

    }


    @OnClick(R.id.clear) void clear(View view) {
        if (view.getId() == R.id.clear) {
            et_searchText.setText("");
        }
    }

    @OnClick(R.id.search) void search() {
        presenter.getSearch(
                et_searchText.getText().toString()
        );
    }

    @OnTextChanged(value = R.id.searchText, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void searchTextChanged(Editable s) {
        String text = s.toString();
        if (text.length() == 0) {
            clear.setVisibility(View.INVISIBLE);
        } else {
            clear.setVisibility(View.VISIBLE);
        }
    }

    @OnEditorAction(R.id.searchText) boolean onSearch() {
        search();
        return true;
    }


    @Override
    public void showProgress() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progress.setVisibility(View.INVISIBLE);
    }

    @Override


    public void statusSuccess(UserResponse userResponse) {
        adapter = new UserAdapter(userResponse.getData(), SearchActivity.this);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void statusError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }


    void onClickRecylerView() {
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(SearchActivity.this,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        User user = adapter.getUser(position);

                        Intent intent = new Intent(SearchActivity.this, UserActivity.class);

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

                        startActivity(intent);
                    }
                }));
    }
}
