package com.example.inventoria.ui.user.editor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.inventoria.R;
import com.example.inventoria.tools.SessionManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserActivity extends AppCompatActivity implements UserView {

    UserPresenter presenter;
    ProgressDialog progressDialog;
    SessionManager session;

    String id_user, nama, no_telp, alamat;

    @BindView(R.id.nama)
    EditText et_nama;

    @BindView(R.id.no_telp)
    EditText et_no_telp;

    @BindView(R.id.alamat)
    EditText et_alamat;

    @BindView(R.id.content_simpan)
    LinearLayout content_simpan;

    @BindView(R.id.content_update)
    LinearLayout content_update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        ButterKnife.bind(this);

        session = new SessionManager(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading ...");
        presenter = new UserPresenter(this);

        initDataIntent();
        setTextEditor();
    }

    @OnClick(R.id.simpan) void simpan(){
        presenter.saveUser(

                et_nama.getText().toString(),
                et_no_telp.getText().toString(),
                et_alamat.getText().toString()
        );
    }

    @OnClick(R.id.update) void update() {
        presenter.updateUser(

                id_user,
                et_nama.getText().toString(),
                et_no_telp.getText().toString(),
                et_alamat.getText().toString()
        );
    }


    @OnClick(R.id.hapus) void hapus() {
        presenter.deleteUser(

                id_user
        );
    }

    @Override
    public void showProgress() {
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void statusSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        finish();
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

    private void initDataIntent() {
        Intent intent= getIntent();
        id_user = intent.getStringExtra("id_user");
        nama = intent.getStringExtra("nama");
        no_telp = intent.getStringExtra("no_telp");
        alamat = intent.getStringExtra("alamat");
    }

    private void setTextEditor() {
        if (id_user != null) {
            getSupportActionBar().setTitle("Update data");
            et_nama.setText(nama);
            et_alamat.setText(alamat);
            et_no_telp.setText(no_telp);
            content_update.setVisibility(View.VISIBLE);
            content_simpan.setVisibility(View.GONE);
        } else {
            getSupportActionBar().setTitle("Simpan data");
        }
    }
}
