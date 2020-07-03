package com.example.inventoria.ui.masuk.editor;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.example.inventoria.R;
import com.example.inventoria.tools.Url;

public class MasukWeb extends AppCompatActivity {
    String id_masuk;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masuk1);
        initDataIntent();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        WebView web = (WebView) findViewById(R.id.webview1);
        web.loadUrl(Url.URL + "masuk/cetak_penjualan/" + id_masuk);
        web.setWebViewClient(new WebViewClient());
            }


    private void initDataIntent() {
        Intent intent= getIntent();
        id_masuk = intent.getStringExtra("id_masuk");

    }


    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == android.R.id.home) {
            finish();
        }
        return true;
    }
}
