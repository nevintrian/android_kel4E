package com.example.inventoria;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.inventoria.tools.SessionManager;

import com.example.inventoria.tools.Url;
import com.example.inventoria.ui.barang.BarangFragment;
import com.example.inventoria.ui.gudang.GudangFragment;
import com.example.inventoria.ui.home.HomeFragment;
import com.example.inventoria.ui.keluar.KeluarFragment;
import com.example.inventoria.ui.masuk.MasukFragment;
import com.example.inventoria.ui.pelanggan.PelangganFragment;

import com.example.inventoria.ui.profil.ProfilFragment;
import com.example.inventoria.ui.sales.SalesFragment;
import com.example.inventoria.ui.search.SearchActivity;
import com.example.inventoria.ui.supplier.SupplierFragment;
import com.example.inventoria.ui.user.UserFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sessionManager = new SessionManager(getApplicationContext());
        sessionManager.checkLogin();

//        Benerin bug ketika rotasi landsacpe malah pindah ke mainActivity
        if (savedInstanceState == null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.mainFrame, new HomeFragment());
            fragmentTransaction.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Set text Navigation drawer
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView tv_username = (TextView) headerView.findViewById(R.id.username);
        tv_username.setText(sessionManager.getKeyUsername());

        TextView tv_level = (TextView) headerView.findViewById(R.id.level);
        tv_level.setText(sessionManager.getKeyLevel());


        String link = Url.URL + "image/user/";
        Glide.with(this).load(link + sessionManager.getKeyFoto()).into((ImageView) headerView.findViewById(R.id.foto));






        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


       if (id == R.id.nav_home) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.mainFrame, new HomeFragment())
                    .commit();
       } else if (id == R.id.nav_gudang) {
           getSupportFragmentManager()
                   .beginTransaction()
                   .replace(R.id.mainFrame, new GudangFragment())
                   .commit();
       } else if (id == R.id.nav_sales) {
           getSupportFragmentManager()
                   .beginTransaction()
                   .replace(R.id.mainFrame, new SalesFragment())
                   .commit();
       } else if (id == R.id.nav_pelanggan) {
           getSupportFragmentManager()
                   .beginTransaction()
                   .replace(R.id.mainFrame, new PelangganFragment())
                   .commit();
        } else if (id == R.id.nav_supplier) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.mainFrame, new SupplierFragment())
                    .commit();
       } else if (id == R.id.nav_barang) {
           getSupportFragmentManager()
                   .beginTransaction()
                   .replace(R.id.mainFrame, new BarangFragment())
                   .commit();
       } else if (id == R.id.nav_user) {
           getSupportFragmentManager()
                   .beginTransaction()
                   .replace(R.id.mainFrame, new UserFragment())
                   .commit();
       } else if (id == R.id.nav_profil) {
           getSupportFragmentManager()
                   .beginTransaction()
                   .replace(R.id.mainFrame, new ProfilFragment())
                   .commit();
       } else if (id == R.id.nav_masuk) {
           getSupportFragmentManager()
                   .beginTransaction()
                   .replace(R.id.mainFrame, new MasukFragment())
                   .commit();
       } else if (id == R.id.nav_keluar) {
           getSupportFragmentManager()
                   .beginTransaction()
                   .replace(R.id.mainFrame, new KeluarFragment())
                   .commit();
        } else if (id == R.id.nav_logout) {
            sessionManager.logoutUser();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {



        getMenuInflater().inflate(R.menu.search, menu);
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


            if (item.getItemId() == R.id.search) {
                startActivity(new Intent(MainActivity.this, SearchActivity.class));
            }

        return super.onOptionsItemSelected(item);
    }

}
