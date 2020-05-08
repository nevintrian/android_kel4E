package com.example.inventoria.ui.sales.editor;

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
import com.example.inventoria.ui.sales.editor.SalesPresenter;
import com.example.inventoria.ui.sales.editor.SalesView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SalesActivity extends AppCompatActivity implements SalesView {

    SalesPresenter presenter;
    ProgressDialog progressDialog;
    SessionManager session;

    String id_user, email, username, password, level, nama, tgl_lahir, jenis_kelamin, no_telp, alamat;

    @BindView(R.id.email)
    EditText et_email;

    @BindView(R.id.username)
    EditText et_username;

    @BindView(R.id.password)
    EditText et_password;

    @BindView(R.id.level)
    EditText et_level;

    @BindView(R.id.nama)
    EditText et_nama;

    @BindView(R.id.tgl_lahir)
    EditText et_tgl_lahir;

    @BindView(R.id.jenis_kelamin)
    EditText et_jenis_kelamin;

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
        setContentView(R.layout.activity_sales);

        ButterKnife.bind(this);

        session = new SessionManager(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading ...");
        presenter = new SalesPresenter(this);

        initDataIntent();
        setTextEditor();
    }

    @OnClick(R.id.simpan) void simpan(){
        presenter.saveSales(

                et_username.getText().toString(),
                et_email.getText().toString(),
                et_password.getText().toString(),
                et_level.getText().toString(),
                et_nama.getText().toString(),
                et_tgl_lahir.getText().toString(),
                et_jenis_kelamin.getText().toString(),
                et_no_telp.getText().toString(),
                et_alamat.getText().toString()
        );
    }

    @OnClick(R.id.update) void update() {
        presenter.updateSales(

                id_user,
                et_username.getText().toString(),
                et_email.getText().toString(),
                et_password.getText().toString(),
                et_level.getText().toString(),
                et_nama.getText().toString(),
                et_tgl_lahir.getText().toString(),
                et_jenis_kelamin.getText().toString(),
                et_no_telp.getText().toString(),
                et_alamat.getText().toString()
        );
    }


    @OnClick(R.id.hapus) void hapus() {
        presenter.deleteSales(

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
        email = intent.getStringExtra("email");
        username = intent.getStringExtra("username");
        password = intent.getStringExtra("password");
        level = intent.getStringExtra("level");
        nama = intent.getStringExtra("nama");
        tgl_lahir = intent.getStringExtra("tgl_lahir");
        jenis_kelamin = intent.getStringExtra("jenis_kelamin");
        no_telp = intent.getStringExtra("no_telp");
        alamat = intent.getStringExtra("alamat");
    }

    private void setTextEditor() {
        if (id_user != null) {
            getSupportActionBar().setTitle("Update data");
            et_email.setText(email);
            et_username.setText(username);
            et_password.setText(password);
            et_level.setText(level);
            et_nama.setText(nama);
            et_tgl_lahir.setText(tgl_lahir);
            et_jenis_kelamin.setText(jenis_kelamin);
            et_alamat.setText(alamat);
            et_no_telp.setText(no_telp);
            content_update.setVisibility(View.VISIBLE);
            content_simpan.setVisibility(View.GONE);
        } else {
            getSupportActionBar().setTitle("Simpan data");
        }
    }
}
