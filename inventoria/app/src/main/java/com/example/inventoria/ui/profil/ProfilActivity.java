package com.example.inventoria.ui.profil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.inventoria.R;
import com.example.inventoria.tools.SessionManager;
import com.example.inventoria.tools.Url;
import com.example.inventoria.ui.sales.editor.SalesPresenter;


import butterknife.OnClick;

public class ProfilActivity extends AppCompatActivity implements ProfilView {

    SessionManager sessionManager;
    ProfilPresenter presenter;
    ProgressDialog progressDialog;

    EditText Id_user;
    EditText Email;
    EditText Username;
    EditText Password;
    EditText Nama;
    EditText Level;
    EditText Tgl_lahir;
    EditText Jenis_kelamin ;
    EditText Alamat ;
    EditText No_telp ;
    LinearLayout content_simpan;
    Button Simpan;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading ...");
        presenter = new ProfilPresenter(this);



        sessionManager = new SessionManager(getApplicationContext());

        Id_user = (EditText) findViewById(R.id.id_user);
        Id_user.setText(sessionManager.getKeyId_user());

        Email = (EditText) findViewById(R.id.email);
        Email.setText(sessionManager.getKeyEmail());

        Username = (EditText) findViewById(R.id.username);
        Username.setText(sessionManager.getKeyUsername());

        Password = (EditText) findViewById(R.id.password);
        Password.setText(sessionManager.getKeyPassword());

        Nama = (EditText) findViewById(R.id.nama);
        Nama.setText(sessionManager.getKeyNama());

        Level = (EditText) findViewById(R.id.level);
        Level.setText(sessionManager.getKeyLevel());

        Tgl_lahir = (EditText) findViewById(R.id.tgl_lahir);
        Tgl_lahir.setText(sessionManager.getKeyTgl_lahir());

        Jenis_kelamin = (EditText) findViewById(R.id.jenis_kelamin);
        Jenis_kelamin.setText(sessionManager.getKeyJenis_kelamin());

        Alamat = (EditText) findViewById(R.id.alamat);
        Alamat.setText(sessionManager.getKeyAlamat());

        No_telp = (EditText) findViewById(R.id.no_telp);
        No_telp.setText(sessionManager.getKeyNo_telp());

        String link = Url.URL + "image/user/";
        Glide.with(this).load(link + sessionManager.getKeyFoto()).into((ImageView) findViewById(R.id.foto));


        // Assigning ID's to Button.
        Simpan = (Button) findViewById(R.id.simpan);



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


    @OnClick(R.id.simpan) void update() {
        presenter.updateProfil(

                Id_user.getText().toString(),
                Username.getText().toString(),
                Email.getText().toString(),
                Password.getText().toString(),
                Level.getText().toString(),
                Nama.getText().toString(),
                Tgl_lahir.getText().toString(),
                Jenis_kelamin.getText().toString(),
                No_telp.getText().toString(),
                Alamat.getText().toString()
        );
    }


}

