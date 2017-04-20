package com.example.pattimura.sundawenang;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pattimura.sundawenang.Adapter.AdapterProduk;
import com.example.pattimura.sundawenang.Fragment.Aspirasi;
import com.example.pattimura.sundawenang.Fragment.Berita;
import com.example.pattimura.sundawenang.Fragment.DetailProduk;
import com.example.pattimura.sundawenang.Fragment.Layanan;
import com.example.pattimura.sundawenang.Fragment.Lowongan;
import com.example.pattimura.sundawenang.Fragment.Pengaduan;
import com.example.pattimura.sundawenang.Fragment.Produk;
import com.example.pattimura.sundawenang.Model.ProdukModel;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LandingPage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Fragment fragment;
    TextView judul;
    String token;
    private ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        Bundle b = getIntent().getExtras();
        if (b != null) {
            token = b.getString("token");
            //Toast.makeText(this, token, Toast.LENGTH_SHORT).show();
        }

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        ImageView gambar = (ImageView) header.findViewById(R.id.gambarHeader);
        Picasso.with(this).load(R.drawable.head).fit().into(gambar);
        judul = (TextView) toolbar.findViewById(R.id.toolbar_title);
        judul.setText("BERITA");
        fragment = new Berita();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mainframe, fragment);
        ft.commit();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.landing_page, menu);
        return true;
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Mohon tunggu, sedang mengambil data !");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_berita) {
            judul.setText("BERITA");
            fragment = new Berita();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mainframe, fragment);
            ft.commit();
        } else if (id == R.id.nav_lowongan) {
            judul.setText("LOWONGAN");
            fragment = new Lowongan();
            Bundle b = new Bundle();
            b.putString("token", token);
            fragment.setArguments(b);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mainframe, fragment);
            ft.commit();
        } else if (id == R.id.nav_produk) {
            judul.setText("PRODUK");
            fragment = new Produk();
            Bundle b = new Bundle();
            b.putString("token", token);
            fragment.setArguments(b);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mainframe, fragment);
            ft.commit();
        } else if (id == R.id.nav_layanan) {
            judul.setText("PELAYANAN");
            fragment = new Layanan();
            Bundle b = new Bundle();
            b.putString("token", token);
            fragment.setArguments(b);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mainframe, fragment);
            ft.commit();
        } else if (id == R.id.nav_aspirasi) {
            judul.setText("ASPIRASI");
            fragment = new Aspirasi();
            Bundle b = new Bundle();
            b.putString("token", token);
            fragment.setArguments(b);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mainframe, fragment);
            ft.commit();
        } else if (id == R.id.nav_pengaduan) {
            judul.setText("PENGADUAN");
            fragment = new Pengaduan();
            Bundle b = new Bundle();
            b.putString("token", token);
            fragment.setArguments(b);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mainframe, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
