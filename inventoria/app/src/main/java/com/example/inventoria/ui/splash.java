package com.example.inventoria.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.inventoria.MainActivity;
import com.example.inventoria.R;


public class splash extends AppCompatActivity {
    private int waktu_loading=300;

    //4000=4 detik

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                //setelah loading maka akan langsung berpindah ke home activity
                Intent intent=new Intent(splash.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        },waktu_loading);
    }
}