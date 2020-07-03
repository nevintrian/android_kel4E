package com.example.inventoria.ui.keluar.editor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.inventoria.R;
import com.example.inventoria.network.response.BarangResponse;
import com.example.inventoria.network.response.UserResponse;
import com.example.inventoria.tools.SessionManager;
import com.example.inventoria.tools.Url;
import com.example.inventoria.ui.masuk.editor.SpinnerBarangAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class KeluarWeb extends AppCompatActivity {
    String id_keluar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keluar1);
        initDataIntent();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        WebView web = (WebView) findViewById(R.id.webview);
        web.loadUrl(Url.URL + "keluar/cetak_penjualan/" + id_keluar);
        web.setWebViewClient(new WebViewClient());
            }


    private void initDataIntent() {
        Intent intent= getIntent();
        id_keluar = intent.getStringExtra("id_keluar");

    }


    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == android.R.id.home) {
            finish();
        }
        return true;
    }
}
