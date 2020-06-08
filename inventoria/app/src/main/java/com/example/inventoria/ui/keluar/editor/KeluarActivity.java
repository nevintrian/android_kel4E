package com.example.inventoria.ui.keluar.editor;

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
import com.example.inventoria.ui.keluar.editor.KeluarPresenter;
import com.example.inventoria.ui.keluar.editor.KeluarView;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class KeluarActivity extends AppCompatActivity implements KeluarView {

    KeluarPresenter presenter;
    ProgressDialog progressDialog;
    SessionManager session;

    String id_keluar, id_user, tgl_keluar, total_keluar;

    @BindView(R.id.id_keluar)
    EditText et_id_keluar;

    @BindView(R.id.id_user)
    EditText et_id_user;

    @BindView(R.id.tgl_keluar)
    EditText et_tgl_keluar;

    @BindView(R.id.total_keluar)
    EditText et_total_keluar;

    @BindView(R.id.content_simpan)
    LinearLayout content_simpan;

    @BindView(R.id.content_update)
    LinearLayout content_update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keluar);

        ButterKnife.bind(this);

        session = new SessionManager(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading ...");
        presenter = new KeluarPresenter(this);

        initDataIntent();
        setTextEditor();
    }

    @OnClick(R.id.simpan) void simpan(){
        presenter.saveKeluar(


                et_id_user.getText().toString(),
                et_tgl_keluar.getText().toString(),
                et_total_keluar.getText().toString()
        );
    }

    @OnClick(R.id.update) void update() {
        presenter.updateKeluar(

                id_keluar,
                et_id_user.getText().toString(),
                et_tgl_keluar.getText().toString(),
                et_total_keluar.getText().toString()
        );
    }


    @OnClick(R.id.hapus) void hapus() {
        presenter.deleteKeluar(

                id_keluar
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
        id_keluar = intent.getStringExtra("id_keluar");
        id_user = intent.getStringExtra("id_user");
        tgl_keluar = intent.getStringExtra("tgl_keluar");
        total_keluar = intent.getStringExtra("total_keluar");
    }

    private void setTextEditor() {
        if (id_keluar != null) {
            getSupportActionBar().setTitle("Update data");
            et_id_keluar.setText(id_keluar);
            et_id_user.setText(id_user);
            et_tgl_keluar.setText(tgl_keluar);
            et_total_keluar.setText(total_keluar);

            content_update.setVisibility(View.VISIBLE);
            content_simpan.setVisibility(View.GONE);
        } else {
            getSupportActionBar().setTitle("Simpan data");
        }
    }
}
