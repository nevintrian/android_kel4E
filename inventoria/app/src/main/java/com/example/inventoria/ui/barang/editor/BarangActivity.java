package com.example.inventoria.ui.barang.editor;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.inventoria.BuildConfig;
import com.example.inventoria.R;
import com.example.inventoria.network.response.SupplierResponse;
import com.example.inventoria.tools.FileUtils;
import com.example.inventoria.tools.SessionManager;
import com.example.inventoria.tools.Url;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class BarangActivity extends AppCompatActivity implements BarangView {
    SpinnerSupplierAdapter adapter;
    SessionManager session;
    ProgressDialog progressDialog;
    BarangPresenter presenter;
    Uri uri;
    Boolean CheckEditText;
    String id_barang, id_supplier, nama_supplier, nama_barang, kemasan, merk, jenis, stok, harga, terjual, foto_barang;
    String currentPhotoPath;
    String selectImagePath;
    static final String folder = "AndroidInventory";
    static final int type_foto_code = 1;
    static final int REQUEST_GALLERY = 1;
    static final int REQUEST_CAMERA = 2;


    @BindView(R.id.nama_supplier)
    Spinner s_nama;
    @BindView(R.id.nama_barang)
    EditText et_nama_barang;
    @BindView(R.id.kemasan)
    EditText et_kemasan;
    @BindView(R.id.merk)
    EditText et_merk;
    @BindView(R.id.jenis)
    EditText et_jenis;
    @BindView(R.id.stok)
    EditText et_stok;
    @BindView(R.id.harga)
    EditText et_harga;
    @BindView(R.id.terjual)
    EditText et_terjual;
    @BindView(R.id.foto_barang)
    ImageView iv_foto_barang;
    @BindView(R.id.content_simpan)
    LinearLayout content_simpan;
    @BindView(R.id.content_update)
    LinearLayout content_update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barang);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading ...");

        session = new SessionManager(this);
        presenter = new BarangPresenter(this);

        presenter.getListSupplier();
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

    }

    @OnClick(R.id.select) void selectImage() {
        if (permission()) {
            Intent intentGallery = new Intent();
            intentGallery.setType("image/*");
            intentGallery.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intentGallery.createChooser(intentGallery, "Select Image"), REQUEST_GALLERY);


        }


    }

    @OnClick(R.id.simpan) void simpan() {
        CheckEditTextIsEmptyOrNot();

        if (CheckEditText) {
        File file = FileUtils.getFile(BarangActivity.this, uri);
        RequestBody foto_barangBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part foto_barangPart = MultipartBody.Part.createFormData("foto_barang", file.getName
                (), foto_barangBody);
        RequestBody id_supplierBody = RequestBody.create(MediaType.parse("text/plain"),  id_supplier);
        RequestBody nama_barangBody = RequestBody.create(MediaType.parse("text/plain"), et_nama_barang.getText()
                .toString());
        RequestBody kemasanBody = RequestBody.create(MediaType.parse("text/plain"), et_kemasan.getText()
                .toString());
        RequestBody merkBody = RequestBody.create(MediaType.parse("text/plain"), et_merk.getText()
                .toString());
        RequestBody jenisBody = RequestBody.create(MediaType.parse("text/plain"), et_jenis.getText()
                .toString());
        RequestBody stokBody = RequestBody.create(MediaType.parse("text/plain"), et_stok.getText()
                .toString());
        RequestBody hargaBody = RequestBody.create(MediaType.parse("text/plain"), et_harga.getText()
                .toString());
        RequestBody terjualBody = RequestBody.create(MediaType.parse("text/plain"), et_terjual.getText()
                .toString());

        presenter.saveBarang(

                foto_barangPart,
                id_supplierBody,
                nama_barangBody,
                kemasanBody,
                merkBody,
                jenisBody,
                stokBody,
                hargaBody,
                terjualBody

        );
        } else {

            Toast.makeText(BarangActivity.this, "Data belum diisi", Toast.LENGTH_LONG).show();

        }
    }

    @OnClick(R.id.update) void update() {
            CheckEditTextIsEmptyOrNot();

            if (CheckEditText) {

        MultipartBody.Part foto_barangPart = null;
        // Cek jika ada file gambar yang telah di set atau tidak
        if (uri == null) {
            RequestBody file = RequestBody.create(MultipartBody.FORM,"");
            foto_barangPart = MultipartBody.Part.createFormData("foto_barang", "", file);
        } else {
            File file = FileUtils.getFile(BarangActivity.this, uri);
            RequestBody foto_barangBody = RequestBody.create(MediaType.parse("image/*"), file);
            foto_barangPart = MultipartBody.Part.createFormData("foto_barang", file.getName
                    (), foto_barangBody);
        }

        RequestBody id_supplierBody = RequestBody.create(MediaType.parse("text/plain"), id_supplier);
        RequestBody nama_barangBody = RequestBody.create(MediaType.parse("text/plain"), et_nama_barang.getText()
                .toString());
        RequestBody kemasanBody = RequestBody.create(MediaType.parse("text/plain"), et_kemasan.getText()
                .toString());
        RequestBody merkBody = RequestBody.create(MediaType.parse("text/plain"), et_merk.getText()
                .toString());
        RequestBody jenisBody = RequestBody.create(MediaType.parse("text/plain"), et_jenis.getText()
                .toString());
        RequestBody stokBody = RequestBody.create(MediaType.parse("text/plain"), et_stok.getText()
                .toString());
        RequestBody hargaBody = RequestBody.create(MediaType.parse("text/plain"), et_harga.getText()
                .toString());
        RequestBody terjualBody = RequestBody.create(MediaType.parse("text/plain"), et_terjual.getText()
                .toString());



        presenter.updateBarang(

                id_barang,
                foto_barangPart,
                id_supplierBody,
                nama_barangBody,
                kemasanBody,
                merkBody,
                jenisBody,
                stokBody,
                hargaBody,
                terjualBody

        );
            } else {

                Toast.makeText(BarangActivity.this, "Data belum diisi", Toast.LENGTH_LONG).show();

            }
    }

    @OnClick(R.id.hapus) void hapus() {
        presenter.deleteBarang(

                id_barang
        );
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
    public void showProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_GALLERY && resultCode != 0) {
            uri = data.getData();
            iv_foto_barang.setImageURI(uri);
        } else if (requestCode == REQUEST_CAMERA && resultCode != 0) {
            uri = Uri.parse(currentPhotoPath);
            selectImagePath = uri.getPath();
            iv_foto_barang.setImageURI(uri);
        }
    }

    @Override
    public void setListSupplier(SupplierResponse supplierResponse) {
        adapter = new SpinnerSupplierAdapter(this, R.layout.spinner_supplier,
                supplierResponse.getData());
        s_nama.setAdapter(adapter);

        setTextEditor();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    private void initDataIntent() {
        Intent intent= getIntent();
        id_barang = intent.getStringExtra("id_barang");
        id_supplier = intent.getStringExtra("id_supplier");
        nama_barang = intent.getStringExtra("nama_barang");
        kemasan = intent.getStringExtra("kemasan");
        merk = intent.getStringExtra("merk");
        jenis = intent.getStringExtra("jenis");
        stok = intent.getStringExtra("stok");
        harga = intent.getStringExtra("harga");
        terjual = intent.getStringExtra("terjual");
        foto_barang = intent.getStringExtra("foto_barang");
    }



    private void setTextEditor() {
        if (id_barang != null) {
            getSupportActionBar().setTitle("Update data");
            s_nama.setSelection(adapter.getItemIndexById(id_supplier));
            et_nama_barang.setText(nama_barang);
            et_kemasan.setText(kemasan);
            et_merk.setText(merk);
            et_jenis.setText(jenis);
            et_stok.setText(stok);
            et_harga.setText(harga);
            et_terjual.setText(terjual);

            String URL = Url.URL + "image/barang/";

            Glide.with(this).load(URL + foto_barang)
                    .thumbnail(0.5f)
                    .transition(new DrawableTransitionOptions().crossFade())
                    .into(iv_foto_barang);

            content_update.setVisibility(View.VISIBLE);
            content_simpan.setVisibility(View.GONE);
        } else {
            getSupportActionBar().setTitle("Simpan data");
        }
    }

    private boolean permission() {
        if (
                Build.VERSION.SDK_INT >= 23
                        && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED
                        && checkSelfPermission(Manifest.permission.RECORD_AUDIO)
                        != PackageManager.PERMISSION_GRANTED
                        && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED
                        && checkSelfPermission(Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED
                ) {
            requestPermissions(new String[]{
                    Manifest.permission.RECORD_AUDIO
                    , Manifest.permission.WRITE_EXTERNAL_STORAGE
                    , Manifest.permission.READ_EXTERNAL_STORAGE
                    , Manifest.permission.CAMERA
            }, 0);
        } else {

        }
        return true;
    }

    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }

        return 0;
    }

    private Uri ambilOutputMediaFileUri(int type_foto_code) {
        // mengambil alamat directory file
        // return Uri.fromFile(ambilOutputMediaFile(type_foto_code));
        return FileProvider.getUriForFile(BarangActivity.this,
                BuildConfig.APPLICATION_ID + ".provider",
                ambilOutputMediaFile());
    }

    private File ambilOutputMediaFile() {
        // atur alamat penyimpanan (SDCard/Pictures/folder_foto)
        File penyimpananMediaDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                , folder
        );
        Log.d("Directory Fileku", penyimpananMediaDir.getAbsolutePath());


        // cek keberadaan folder
        if (!penyimpananMediaDir.exists()) {
            // cek jika tidak bisa membuat folder baru
            if (!penyimpananMediaDir.mkdir()) {
                Toast.makeText(this, "Gagal membuat folder "
                        + folder, Toast.LENGTH_SHORT).show();
                return null;
            }
        }

        // simpan format tanggal saat pengambilan foto
        String waktu = new SimpleDateFormat("yyyyMMdd_hhMss"
                , Locale.getDefault()).format(new Date());
        Log.d("Waktu Pengambilan", waktu);

        // variabel penampung nama file
        File mediaFile;
        // pasang nama foto dengan waktu
        if (type_foto_code == type_foto_code) {
            mediaFile = new File(penyimpananMediaDir.getPath() + File.separator
                    + "IMG" + waktu + ".jpg");
            Log.d("Nama FIle", mediaFile.getAbsolutePath());
        } else {
            return null;
        }
        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = "file:" + mediaFile.getAbsolutePath();
        Log.d("currentPhotoPath : ", currentPhotoPath);
        return mediaFile;
    }

    public void CheckEditTextIsEmptyOrNot() {


        // Getting values from EditText.
        String id_supplier1 = id_supplier;
        String nama_barang1 = et_nama_barang.getText().toString().trim();
        String kemasan1 = et_kemasan.getText().toString().trim();
        String merk1 = et_merk.getText().toString().trim();
        String jenis1 = et_jenis.getText().toString().trim();
        String harga1 = et_harga.getText().toString().trim();
        String stok1 = et_stok.getText().toString().trim();
        String terjual1 = et_terjual.getText().toString().trim();
        boolean i= iv_foto_barang.getDrawable()==null;
        // Checking whether EditText value is empty or not.
        if (TextUtils.isEmpty(id_supplier1) || TextUtils.isEmpty(nama_barang1) || TextUtils.isEmpty(kemasan1) || TextUtils.isEmpty(merk1) || TextUtils.isEmpty(jenis1) || TextUtils.isEmpty(harga1) || TextUtils.isEmpty(stok1) || TextUtils.isEmpty(terjual1) || i) {

            // If any of EditText is empty then set variable value as False.
            CheckEditText = false;

        } else {

            // If any of EditText is filled then set variable value as True.
            CheckEditText = true;
        }
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }
        return true;
    }
}
