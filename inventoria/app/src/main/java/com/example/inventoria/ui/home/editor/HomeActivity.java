package com.example.inventoria.ui.home.editor;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;


import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.inventoria.BuildConfig;
import com.example.inventoria.R;
import com.example.inventoria.model.Barang;
import com.example.inventoria.tools.FileUtils;
import com.example.inventoria.tools.SessionManager;
import com.example.inventoria.tools.Url;
import com.example.inventoria.ui.barang.editor.BarangPresenter;
import com.example.inventoria.ui.barang.editor.BarangView;
import com.example.inventoria.ui.keluar.editor.KeluarActivity;


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

public class HomeActivity extends AppCompatActivity implements HomeView {

    SessionManager session;
    ProgressDialog progressDialog;
    Uri uri;

    String id_barang, id_supplier, nama_barang, kemasan, merk, jenis, stok, harga, terjual, foto_barang;
    String currentPhotoPath;
    String selectImagePath;
    static final String folder = "AndroidInventory";
    static final int type_foto_code = 1;
    static final int REQUEST_GALLERY = 1;
    static final int REQUEST_CAMERA = 2;

    @BindView(R.id.update)
    Button update;
    @BindView(R.id.id_supplier)
    TextView et_id_supplier;
    @BindView(R.id.nama_barang)
    TextView et_nama_barang;
    @BindView(R.id.kemasan)
    TextView et_kemasan;
    @BindView(R.id.merk)
    TextView et_merk;
    @BindView(R.id.jenis)
    TextView et_jenis;
    @BindView(R.id.stok)
    TextView et_stok;
    @BindView(R.id.harga)
    TextView et_harga;
    @BindView(R.id.terjual)
    TextView et_terjual;
    @BindView(R.id.foto_barang)
    ImageView iv_foto_barang;
    @BindView(R.id.content_simpan)
    LinearLayout content_simpan;
    @BindView(R.id.content_update)
    LinearLayout content_update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading ...");

        session = new SessionManager(this);



        initDataIntent();
        setTextEditor();

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
            getSupportActionBar().setTitle("Lihat Barang");
            et_id_supplier.setText(id_supplier);
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

    private void permission() {
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
        return FileProvider.getUriForFile(HomeActivity.this,
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









    @OnClick(R.id.update) void update(){
        Intent intent = new Intent(HomeActivity.this, KeluarActivity.class);

        intent.putExtra("id_barang", id_barang);
        intent.putExtra("id_user", session.getKeyId_user());
        startActivity(intent);
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }
        return true;
    }
}
