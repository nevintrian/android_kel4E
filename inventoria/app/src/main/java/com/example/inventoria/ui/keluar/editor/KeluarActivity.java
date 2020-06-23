package com.example.inventoria.ui.keluar.editor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.inventoria.R;
import com.example.inventoria.network.response.BarangResponse;
import com.example.inventoria.network.response.SupplierResponse;
import com.example.inventoria.network.response.UserResponse;
import com.example.inventoria.tools.SessionManager;
import com.example.inventoria.ui.barang.editor.SpinnerSupplierAdapter;
import com.example.inventoria.ui.keluar.editor.KeluarPresenter;
import com.example.inventoria.ui.keluar.editor.KeluarView;
import com.example.inventoria.ui.masuk.editor.SpinnerBarangAdapter;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class KeluarActivity extends AppCompatActivity implements KeluarView {
    SpinnerUserAdapter adapter;
    SpinnerBarangAdapter adapter1;
    KeluarPresenter presenter;
    ProgressDialog progressDialog;
    SessionManager session;

    String id_keluar, id_user, nama, id_barang, nama_barang, qty, total_keluar, harga;

    @BindView(R.id.nama)
    Spinner s_nama;

    @BindView(R.id.nama_barang)
    Spinner s_nama1;

    @BindView(R.id.qty)
    EditText et_qty;

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
        presenter.getListUser();
        presenter.getListBarang();
        initDataIntent();
        s_nama.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nama = ((TextView) view.findViewById(R.id.nama)).getText().toString();
                id_user = ((TextView) view.findViewById(R.id.id_user)).getText().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        s_nama1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nama_barang = ((TextView) view.findViewById(R.id.nama_barang)).getText().toString();
                id_barang = ((TextView) view.findViewById(R.id.id_barang)).getText().toString();
                harga = ((TextView) view.findViewById(R.id.harga)).getText().toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    @OnTextChanged(R.id.qty) void qty() {
        // Cek jika value nya di kosongkan
        String s_qty;
        if (et_qty.getText().toString().isEmpty()) {
            s_qty = "1" ;
        } else {
            s_qty = et_qty.getText().toString();
        }

        try {
            int total = Integer.parseInt(s_qty) * Integer.parseInt(harga);
            et_total_keluar.setText(String.valueOf(total));
        } catch (NumberFormatException e) {

        }
    }



    @OnClick(R.id.simpan) void simpan(){
        presenter.saveKeluar(

                id_barang,
                id_user,
                et_qty.getText().toString(),
                et_total_keluar.getText().toString()
        );
    }

    @OnClick(R.id.update) void update() {
        presenter.updateKeluar(

                id_keluar,
                id_barang,
                id_user,
                et_qty.getText().toString(),
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
    @Override
    public void setListUser(UserResponse userResponse) {
        adapter = new SpinnerUserAdapter(this, R.layout.spinner_user,
                userResponse.getData());
        s_nama.setAdapter(adapter);

        setTextEditor();
    }

    @Override
    public void setListBarang(BarangResponse barangResponse) {
        adapter1 = new SpinnerBarangAdapter(this, R.layout.spinner_barang,
                barangResponse.getData());
        s_nama1.setAdapter(adapter1);

        setTextEditor();
    }

    private void initDataIntent() {
        Intent intent= getIntent();
        id_keluar = intent.getStringExtra("id_keluar");
        id_barang = intent.getStringExtra("id_barang");
        id_user = intent.getStringExtra("id_user");
        qty = intent.getStringExtra("qty_keluar");
        total_keluar = intent.getStringExtra("total_keluar");
    }

    private void setTextEditor() {
        if (id_keluar != null) {
            getSupportActionBar().setTitle("Update data");
            s_nama1.setSelection(adapter.getItemIndexById(id_barang));
            s_nama.setSelection(adapter.getItemIndexById(id_user));
            et_qty.setText(qty);
            et_total_keluar.setText(total_keluar);

            content_update.setVisibility(View.VISIBLE);
            content_simpan.setVisibility(View.GONE);
        } else {
            getSupportActionBar().setTitle("Simpan data");
        }
    }
}
