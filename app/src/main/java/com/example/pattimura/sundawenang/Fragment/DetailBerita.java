package com.example.pattimura.sundawenang.Fragment;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.KeyEvent;
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

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailBerita extends Fragment {
    WebView deskripsi;
    int lebar;
    ProgressDialog mProgressDialog;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    private String TAG;

    public DetailBerita() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_detail_berita, container, false);

        SharedPreferences prefs = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        TAG = prefs.getString("TAG", "not found");
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    Log.e("gif--", "fragment back key is clicked");
                    getActivity().getSupportFragmentManager().popBackStack(TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    return true;
                }
                return false;
            }
        });
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
