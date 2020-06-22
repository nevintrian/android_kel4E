package com.example.inventoria.ui.pelanggan.editor;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;


import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.inventoria.BuildConfig;
import com.example.inventoria.R;
import com.example.inventoria.tools.FileUtils;
import com.example.inventoria.tools.SessionManager;
import com.example.inventoria.tools.Url;
import com.example.inventoria.ui.user.editor.UserActivity;


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

public class PelangganActivity extends AppCompatActivity implements PelangganView {

    SessionManager session;
    ProgressDialog progressDialog;
    PelangganPresenter presenter;
    Uri uri;
    Boolean CheckEditText;
    String id_user, email, username, password, level, nama, tgl_lahir, jenis_kelamin, alamat, no_telp, foto;
    String currentPhotoPath;
    String selectImagePath;
    static final String folder = "AndroidInventory";
    static final int type_foto_code = 1;
    static final int REQUEST_GALLERY = 1;
    static final int REQUEST_CAMERA = 2;


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
    @BindView(R.id.alamat)
    EditText et_alamat;
    @BindView(R.id.no_telp)
    EditText et_no_telp;
    @BindView(R.id.foto)
    ImageView iv_foto;
    @BindView(R.id.content_simpan)
    LinearLayout content_simpan;
    @BindView(R.id.content_update)
    LinearLayout content_update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pelanggan);
        ButterKnife.bind(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading ...");

        session = new SessionManager(this);
        presenter = new PelangganPresenter(this);


        initDataIntent();
        setTextEditor();

    }

    @OnClick(R.id.select) void selectImage() {

                                Intent intentGallery = new Intent();
                                intentGallery.setType("image/*");
                                intentGallery.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(intentGallery.createChooser(intentGallery, "Select Image"), REQUEST_GALLERY);

                        }


    @OnClick(R.id.simpan) void simpan() {
        CheckEditTextIsEmptyOrNot();

        if (CheckEditText) {
        File file = FileUtils.getFile(PelangganActivity.this, uri);
        RequestBody fotoBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part fotoPart = MultipartBody.Part.createFormData("foto", file.getName
                (), fotoBody);
        RequestBody emailBody = RequestBody.create(MediaType.parse("text/plain"), et_email.getText()
                .toString());
        RequestBody usernameBody = RequestBody.create(MediaType.parse("text/plain"), et_username.getText()
                .toString());
        RequestBody passwordBody = RequestBody.create(MediaType.parse("text/plain"), et_password.getText()
                .toString());
        RequestBody levelBody = RequestBody.create(MediaType.parse("text/plain"), et_level.getText()
                .toString());
        RequestBody namaBody = RequestBody.create(MediaType.parse("text/plain"), et_nama.getText()
                .toString());
        RequestBody tgl_lahirBody = RequestBody.create(MediaType.parse("text/plain"), et_tgl_lahir.getText()
                .toString());
        RequestBody jenis_kelaminBody = RequestBody.create(MediaType.parse("text/plain"), et_jenis_kelamin.getText()
                .toString());
        RequestBody alamatBody = RequestBody.create(MediaType.parse("text/plain"), et_alamat.getText()
                .toString());
        RequestBody no_telpBody = RequestBody.create(MediaType.parse("text/plain"), et_no_telp.getText()
                .toString());

        presenter.savePelanggan(

                fotoPart,
                emailBody,
                usernameBody,
                passwordBody,
                levelBody,
                namaBody,
                tgl_lahirBody,
                jenis_kelaminBody,
                alamatBody,
                no_telpBody

        );
        } else {

            Toast.makeText(PelangganActivity.this, "Data belum diisi", Toast.LENGTH_LONG).show();

        }
    }

    @OnClick(R.id.update) void update() {
            CheckEditTextIsEmptyOrNot();

            if (CheckEditText) {

        MultipartBody.Part fotoPart = null;
        // Cek jika ada file gambar yang telah di set atau tidak
        if (uri == null) {
            RequestBody file = RequestBody.create(MultipartBody.FORM,"");
            fotoPart = MultipartBody.Part.createFormData("foto", "", file);
        } else {
            File file = FileUtils.getFile(PelangganActivity.this, uri);
            RequestBody fotoBody = RequestBody.create(MediaType.parse("image/*"), file);
            fotoPart = MultipartBody.Part.createFormData("foto", file.getName
                    (), fotoBody);
        }

        RequestBody emailBody = RequestBody.create(MediaType.parse("text/plain"), et_email.getText()
                .toString());
        RequestBody usernameBody = RequestBody.create(MediaType.parse("text/plain"), et_username.getText()
                .toString());
        RequestBody passwordBody = RequestBody.create(MediaType.parse("text/plain"), et_password.getText()
                .toString());
        RequestBody levelBody = RequestBody.create(MediaType.parse("text/plain"), et_level.getText()
                .toString());
        RequestBody namaBody = RequestBody.create(MediaType.parse("text/plain"), et_nama.getText()
                .toString());
        RequestBody tgl_lahirBody = RequestBody.create(MediaType.parse("text/plain"), et_tgl_lahir.getText()
                .toString());
        RequestBody jenis_kelaminBody = RequestBody.create(MediaType.parse("text/plain"), et_jenis_kelamin.getText()
                .toString());
        RequestBody alamatBody = RequestBody.create(MediaType.parse("text/plain"), et_alamat.getText()
                .toString());
        RequestBody no_telpBody = RequestBody.create(MediaType.parse("text/plain"), et_no_telp.getText()
                .toString());


        presenter.updatePelanggan(

                id_user,
                fotoPart,
                emailBody,
                usernameBody,
                passwordBody,
                levelBody,
                namaBody,
                tgl_lahirBody,
                jenis_kelaminBody,
                alamatBody,
                no_telpBody

        );
            } else {

                Toast.makeText(PelangganActivity.this, "Data belum diisi", Toast.LENGTH_LONG).show();

            }
    }

    @OnClick(R.id.hapus) void hapus() {
        presenter.deletePelanggan(

                id_user
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
        progressDialog.show();
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
            iv_foto.setImageURI(uri);
        } else if (requestCode == REQUEST_CAMERA && resultCode != 0) {
            uri = Uri.parse(currentPhotoPath);
            selectImagePath = uri.getPath();
            iv_foto.setImageURI(uri);
        }
    }

    @Override
    protected void onDestroy() {
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
        foto = intent.getStringExtra("foto");

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

            String URL = Url.URL + "image/user/";

            Glide.with(this).load(URL + foto)
                    .thumbnail(0.5f)
                    .transition(new DrawableTransitionOptions().crossFade())
                    .into(iv_foto);

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
        return FileProvider.getUriForFile(PelangganActivity.this,
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
        String email1 = et_email.getText().toString().trim();
        String username1 = et_username.getText().toString().trim();
        String password1 = et_password.getText().toString().trim();
        String level1 = et_level.getText().toString().trim();
        String nama1 = et_nama.getText().toString().trim();
        String tgl_lahir1 = et_tgl_lahir.getText().toString().trim();
        String jenis_kelamin1 = et_jenis_kelamin.getText().toString().trim();
        String alamat1 = et_alamat.getText().toString().trim();
        String no_telp1 = et_no_telp.getText().toString().trim();
        boolean i= iv_foto.getDrawable()==null;
        // Checking whether EditText value is empty or not.
        if (TextUtils.isEmpty(email1) || TextUtils.isEmpty(username1) || TextUtils.isEmpty(password1) || TextUtils.isEmpty(level1) || TextUtils.isEmpty(nama1) || TextUtils.isEmpty(tgl_lahir1) || TextUtils.isEmpty(jenis_kelamin1) || TextUtils.isEmpty(alamat1) || TextUtils.isEmpty(no_telp1) || i) {

            // If any of EditText is empty then set variable value as False.
            CheckEditText = false;

        } else {

            // If any of EditText is filled then set variable value as True.
            CheckEditText = true;
        }
    }

}
