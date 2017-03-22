package com.example.pattimura.sundawenang.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Transformers.BaseTransformer;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.pattimura.sundawenang.Model.GambarProduk;
import com.example.pattimura.sundawenang.Model.LowonganModel;
import com.example.pattimura.sundawenang.R;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailLowongan extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {
    private SliderLayout mDemoSlider;
    private LowonganModel lm;

    public DetailLowongan() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_detail_lowongan, container, false);

        mDemoSlider = (SliderLayout) v.findViewById(R.id.sliderlowongan);
        Bundle b = this.getArguments();
        if (b != null) {
            lm = b.getParcelable("Lowongan");
            TextView judul = (TextView) v.findViewById(R.id.textNamadetaillowongan);
            TextView desc = (TextView) v.findViewById(R.id.textDescDetailLowongan);
            TextView tanggal = (TextView) v.findViewById(R.id.textViewTanggalDetaillowongan);

            judul.setText(lm.getJudul());
            desc.setText(lm.getDeskripsi());
            tanggal.setText(lm.getTanggal());

            HashMap<String, Integer> file_maps = new HashMap<String, Integer>();
            if (lm.cekDaftarGambar()) {
                file_maps.put("Coming soon !", R.drawable.imagedefault);
            }
            for (GambarProduk g : lm.getDaftargambar()) {
                file_maps.put(g.getNama(), g.getUrl());
            }
            for (String name : file_maps.keySet()) {
                TextSliderView textSliderView = new TextSliderView(this.getContext());
                // initialize a SliderLayout
                textSliderView
                        .description(name)
                        .image(file_maps.get(name))
                        .setScaleType(BaseSliderView.ScaleType.Fit)
                        .setOnSliderClickListener(DetailLowongan.this);
                mDemoSlider.addSlider(textSliderView);
            }
            mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
            if (lm.getBanyakgambar() > 1) {
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
        }

        return v;
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
