package com.example.inventoria.ui.profil;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.example.inventoria.R;
import com.example.inventoria.tools.SessionManager;
import com.example.inventoria.ui.user.UserFragment;
import com.google.android.material.navigation.NavigationView;

public class ProfilActivity extends AppCompatActivity {

    SessionManager sessionManager;


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
        sessionManager = new SessionManager(getApplicationContext());

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

        // Assigning ID's to Button.
        Simpan = (Button) findViewById(R.id.simpan);






    }
}

