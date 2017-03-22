package com.example.pattimura.sundawenang.Fragment;


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
import android.widget.TextView;

import com.example.pattimura.sundawenang.Model.BeritaModel;
import com.example.pattimura.sundawenang.R;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailBerita extends Fragment {
    WebView video;
    int lebar;

    public DetailBerita() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_detail_berita, container, false);

        ImageView cover = (ImageView) v.findViewById(R.id.imageCoverberita);
        TextView judul = (TextView) v.findViewById(R.id.txtJuduldetailBerita);
        TextView tanggal = (TextView) v.findViewById(R.id.txtTanggaldetailberita);
        TextView kategori = (TextView) v.findViewById(R.id.txtKategoridetailberita);
        TextView deskripsi = (TextView) v.findViewById(R.id.txtDetailBerita);
        video = (WebView) v.findViewById(R.id.video);
        video.setVisibility(View.GONE);

        Bundle b = this.getArguments();
        if (b != null) {
            BeritaModel berita = b.getParcelable("Berita");
            if (!berita.cekDaftarGambar()) {
                Picasso.with(this.getContext()).load(berita.getGambarke(0).getUrl()).fit().into(cover);
            } else {
                Picasso.with(this.getContext()).load(R.drawable.imagedefault).fit().into(cover);
            }

            judul.setText(berita.getJudul());
            tanggal.setText(berita.getTanggal());
            kategori.setText(berita.getKategori());
            deskripsi.setText(berita.getIsiBerita());
            if (!berita.getUrlvideo().equals("")) {
                video.setVisibility(View.VISIBLE);
                video.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        return false;
                    }
                });
                video.getSettings().setJavaScriptEnabled(true);
                video.loadUrl(berita.getUrlvideo());

            }
        }


        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        video.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        video.onPause();
    }

}
