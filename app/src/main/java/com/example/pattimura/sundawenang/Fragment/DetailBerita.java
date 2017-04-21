package com.example.pattimura.sundawenang.Fragment;


import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.pattimura.sundawenang.Model.BeritaModel;
import com.example.pattimura.sundawenang.R;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailBerita extends Fragment {
    WebView deskripsi;
    int lebar;
    ProgressDialog mProgressDialog;

    public DetailBerita() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_detail_berita, container, false);

//        ImageView cover = (ImageView) v.findViewById(R.id.imageCoverberita);
//        TextView judul = (TextView) v.findViewById(R.id.txtJuduldetailBerita);
//        TextView tanggal = (TextView) v.findViewById(R.id.txtTanggaldetailberita);
//        TextView kategori = (TextView) v.findViewById(R.id.txtKategoridetailberita);
        deskripsi = (WebView) v.findViewById(R.id.video);

        Bundle b = this.getArguments();
        if (b != null) {
            BeritaModel berita = b.getParcelable("Berita");
//            if (!berita.cekDaftarGambar()) {
//                Picasso.with(this.getContext()).load(berita.getGambarke(0).getUrl()).fit().into(cover);
//            } else {
//                Picasso.with(this.getContext()).load(R.drawable.imagedefault).fit().into(cover);
//            }

//            judul.setText(berita.getJudul());
//            tanggal.setText(berita.getTanggal());
//            kategori.setText(berita.getKategori());
            deskripsi.setWebViewClient(new MyBrowser());
            deskripsi.getSettings().setJavaScriptEnabled(true);
            deskripsi.loadUrl(berita.getIsiBerita());

            //deskripsi.loadData(berita.getIsiBerita(), "text/html", "UTF-8");
//            if (!berita.getUrlvideo().equals("")) {
//                video.setVisibility(View.VISIBLE);
//                video.setWebViewClient(new WebViewClient() {
//                    @Override
//                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                        return false;
//                    }
//                });
//                video.getSettings().setJavaScriptEnabled(true);
//                video.loadUrl(berita.getUrlvideo());
//
//            }
        }


        return v;
    }

    @Override
    public void onResume() {
        deskripsi.onResume();
        super.onResume();
    }

    @Override
    public void onStop() {
        deskripsi.onPause();
        super.onStop();
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(DetailBerita.this.getContext());
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

    private class MyBrowser extends WebViewClient {
        private ProgressBar progressBar;

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            showProgressDialog();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);
            hideProgressDialog();
        }
    }
}
