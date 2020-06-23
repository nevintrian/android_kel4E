package com.example.inventoria.ui.masuk.editor;

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
import com.example.inventoria.tools.SessionManager;



import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class MasukActivity extends AppCompatActivity implements MasukView {
    SpinnerSupplierAdapter adapter;
    SpinnerBarangAdapter adapter1;
    MasukPresenter presenter;
    ProgressDialog progressDialog;
    SessionManager session;

    String id_masuk, nama_supplier, id_supplier, qty, total_masuk, nama_barang, harga, id_barang;


    @BindView(R.id.nama_supplier)
    Spinner s_nama;

    @BindView(R.id.nama_barang)
    Spinner s_nama1;

    @BindView(R.id.qty)
    EditText et_qty;

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
        presenter.getListSupplier();
        presenter.getListBarang();
        initDataIntent();
        s_nama.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nama_supplier = ((TextView) view.findViewById(R.id.nama_supplier)).getText().toString();
                id_supplier = ((TextView) view.findViewById(R.id.id_supplier)).getText().toString();

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
            et_total_masuk.setText(String.valueOf(total));
        } catch (NumberFormatException e) {

        }
    }


    @OnClick(R.id.simpan) void simpan(){
        presenter.saveMasuk(

                id_barang,
                id_supplier,
                et_qty.getText().toString(),
                et_total_masuk.getText().toString()
        );
    }

    @OnClick(R.id.update) void update() {
        presenter.updateMasuk(

                id_masuk,
                id_barang,
                id_supplier,
                et_qty.getText().toString(),
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
    @Override
    public void setListSupplier(SupplierResponse supplierResponse) {
        adapter = new SpinnerSupplierAdapter(this, R.layout.spinner_supplier,
                supplierResponse.getData());
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
        id_masuk = intent.getStringExtra("id_masuk");
        id_barang = intent.getStringExtra("id_barang");
        id_supplier = intent.getStringExtra("id_supplier");
        qty = intent.getStringExtra("qty_masuk");
        total_masuk = intent.getStringExtra("total_masuk");
    }

    private void setTextEditor() {
        if (id_masuk != null) {
            getSupportActionBar().setTitle("Update data");
            s_nama.setSelection(adapter.getItemIndexById(id_supplier));
            s_nama1.setSelection(adapter.getItemIndexById(id_barang));
            et_qty.setText(qty);
            et_total_masuk.setText(total_masuk);

            content_update.setVisibility(View.VISIBLE);
            content_simpan.setVisibility(View.GONE);
        } else {
            getSupportActionBar().setTitle("Simpan data");
        }
    }
}
