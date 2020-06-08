package com.example.inventoria.ui.masuk.editor;

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
import com.example.inventoria.ui.masuk.editor.MasukPresenter;
import com.example.inventoria.ui.masuk.editor.MasukView;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MasukActivity extends AppCompatActivity implements MasukView {

    MasukPresenter presenter;
    ProgressDialog progressDialog;
    SessionManager session;

    String id_masuk, id_supplier, tgl_masuk, total_masuk;

    @BindView(R.id.id_masuk)
    EditText et_id_masuk;

    @BindView(R.id.id_supplier)
    EditText et_id_supplier;

    @BindView(R.id.tgl_masuk)
    EditText et_tgl_masuk;

    @BindView(R.id.total_masuk)
    EditText et_total_masuk;

    @BindView(R.id.content_simpan)
    LinearLayout content_simpan;

    @BindView(R.id.content_update)
    LinearLayout content_update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masuk);

        ButterKnife.bind(this);

        session = new SessionManager(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading ...");
        presenter = new MasukPresenter(this);

        initDataIntent();
        setTextEditor();
    }

    @OnClick(R.id.simpan) void simpan(){
        presenter.saveMasuk(


                et_id_supplier.getText().toString(),
                et_tgl_masuk.getText().toString(),
                et_total_masuk.getText().toString()
        );
    }

    @OnClick(R.id.update) void update() {
        presenter.updateMasuk(

                id_masuk,
                et_id_supplier.getText().toString(),
                et_tgl_masuk.getText().toString(),
                et_total_masuk.getText().toString()
        );
    }


    @OnClick(R.id.hapus) void hapus() {
        presenter.deleteMasuk(

                id_masuk
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
        id_masuk = intent.getStringExtra("id_masuk");
        id_supplier = intent.getStringExtra("id_supplier");
        tgl_masuk = intent.getStringExtra("tgl_masuk");
        total_masuk = intent.getStringExtra("total_masuk");
    }

    private void setTextEditor() {
        if (id_masuk != null) {
            getSupportActionBar().setTitle("Update data");
            et_id_masuk.setText(id_masuk);
            et_id_supplier.setText(id_supplier);
            et_tgl_masuk.setText(tgl_masuk);
            et_total_masuk.setText(total_masuk);

            content_update.setVisibility(View.VISIBLE);
            content_simpan.setVisibility(View.GONE);
        } else {
            getSupportActionBar().setTitle("Simpan data");
        }
    }
}
