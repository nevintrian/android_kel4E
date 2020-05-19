package com.example.inventoria.ui.profil;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.inventoria.R;
import com.example.inventoria.tools.SessionManager;
import com.example.inventoria.tools.SimpleDividerItemDecoration;
import com.example.inventoria.ui.barang.editor.BarangActivity;
import com.example.inventoria.ui.daftar.DaftarActivity;
import com.example.inventoria.ui.gudang.GudangAdapter;
import com.example.inventoria.ui.gudang.GudangPresenter;
import com.example.inventoria.ui.login.LoginActivity;
import com.example.inventoria.ui.user.UserFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfilFragment extends Fragment {


    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.recyclerUser)
    RecyclerView recyclerView;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipe;


    public ProfilFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View x = inflater.inflate(R.layout.fragment_profil, container, false);
        ButterKnife.bind(this, x);
        getActivity().setTitle("Setting Akun");

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new ProfilActivity();
            }
        });


        return x;
    }

    @OnClick(R.id.fab) void editor() {
        Intent intent = new Intent(getActivity(), ProfilActivity.class);
        startActivity(intent);
    }
}

