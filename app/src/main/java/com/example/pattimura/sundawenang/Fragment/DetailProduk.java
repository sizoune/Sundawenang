package com.example.pattimura.sundawenang.Fragment;


import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.andexert.library.RippleView;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Transformers.BaseTransformer;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.pattimura.sundawenang.Model.GambarProduk;
import com.example.pattimura.sundawenang.Model.ProdukModel;
import com.example.pattimura.sundawenang.R;

import java.util.HashMap;

import info.hoang8f.widget.FButton;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailProduk extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {
    ProdukModel produk;
    //FButton telp, sms;
    private SliderLayout mDemoSlider;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    private String TAG;

    public DetailProduk() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_detail_produk, container, false);

        SharedPreferences prefs = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        TAG = prefs.getString("TAG", "not found");

        mDemoSlider = (SliderLayout) v.findViewById(R.id.slider);

        Bundle b = this.getArguments();
        if (b != null) {
            produk = b.getParcelable("Produk");
            TextView judul = (TextView) v.findViewById(R.id.textNamadetailProduk);
            TextView desc = (TextView) v.findViewById(R.id.textDescDetailProduk);
            TextView tanggal = (TextView) v.findViewById(R.id.textViewTanggalDetailProduk);
            RippleView telp = (RippleView) v.findViewById(R.id.call_button);
            RippleView sms = (RippleView) v.findViewById(R.id.pesan_button);
            judul.setText(produk.getNama());
            desc.setText(produk.getDeskripsi());
            tanggal.setText(produk.getTanggal());

            HashMap<String, String> file_maps = new HashMap<String, String>();
            if (produk.cekDaftarGambar()) {
                file_maps.put("Coming soon !", "");
                Toast.makeText(DetailProduk.this.getContext(), Boolean.toString(produk.cekDaftarGambar()), Toast.LENGTH_SHORT).show();
            }
            for (GambarProduk g : produk.getDaftargambar()) {
                file_maps.put(g.getNama(), g.getUrl());
            }
            for (String name : file_maps.keySet()) {
                TextSliderView textSliderView = new TextSliderView(this.getContext());
                // initialize a SliderLayout
                textSliderView
                        .description(name)
                        .image(file_maps.get(name))
                        .setScaleType(BaseSliderView.ScaleType.Fit)
                        .setOnSliderClickListener(DetailProduk.this);
                mDemoSlider.addSlider(textSliderView);
            }
            mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
            if (produk.getBanyakgambar() > 1) {
                mDemoSlider.setPresetTransformer(SliderLayout.Transformer.ZoomOut);
                mDemoSlider.setCustomAnimation(new DescriptionAnimation());
                mDemoSlider.setDuration(4000);
            } else {
                mDemoSlider.stopAutoCycle();
                mDemoSlider.setPagerTransformer(false, new BaseTransformer() {
                    @Override
                    protected void onTransform(View view, float v) {
                    }
                });
            }

            telp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (android.os.Build.VERSION.SDK_INT == Build.VERSION_CODES.M) {
                        onCall(produk.getNotel());
                    } else {
                        Intent callintent = new Intent(Intent.ACTION_CALL);
                        callintent.setData(Uri.parse("tel:" + produk.getNotel()));
                        startActivity(callintent);
                    }
                }
            });

            sms.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
                    smsIntent.setData(Uri.parse("smsto:" + Uri.encode(produk.getNotel())));
                    startActivity(smsIntent);
                }
            });
        }


        return v;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 123:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    onCall(produk.getNotel());
                } else {
                    Toast.makeText(DetailProduk.this.getContext(), "maaf anda tidak mengizinkan penggunaan fitur ini !", Toast.LENGTH_SHORT).show();
                }
                break;

            default:
                break;
        }
    }

    public void onCall(String notel) {
        int permissionCheck = ContextCompat.checkSelfPermission(DetailProduk.this.getContext(), Manifest.permission.CALL_PHONE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    DetailProduk.this.getActivity(),
                    new String[]{Manifest.permission.CALL_PHONE},
                    123);
        } else {
            startActivity(new Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:" + notel)));
        }
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
        mDemoSlider.startAutoCycle();
        super.onResume();
    }

    @Override
    public void onPause() {
        mDemoSlider.stopAutoCycle();
        super.onPause();
    }

    @Override
    public void onStop() {
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
