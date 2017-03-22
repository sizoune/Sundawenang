package com.example.pattimura.sundawenang.Fragment;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pattimura.sundawenang.Adapter.AdapterAspirasi;
import com.example.pattimura.sundawenang.Model.AspirasiModel;
import com.example.pattimura.sundawenang.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Aspirasi extends Fragment {
    ArrayList<AspirasiModel> listaspirasi = new ArrayList<>();
    AdapterAspirasi adapter;
    Fragment fragment;

    public Aspirasi() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_aspirasi, container, false);
        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.floatingActionButton);
        ImageView cover = (ImageView) v.findViewById(R.id.imageView5);
        ListView listAspirasi = (ListView) v.findViewById(R.id.listAspirasi);

        Picasso.with(Aspirasi.this.getContext()).load(R.drawable.aspirasi).fit().into(cover);
        //getdataAspirasi();
        Bundle b = this.getArguments();
        if (b != null) {
            listaspirasi.add(new AspirasiModel(b.getString("isi"), b.getString("nama"), "", "", ""));
        }
        RelativeLayout lay = (RelativeLayout) v.findViewById(R.id.layoutaspirasi);
        if (!listaspirasi.isEmpty()) {
            lay.setVisibility(View.GONE);
            adapter = new AdapterAspirasi(Aspirasi.this.getContext(), listaspirasi);
            listAspirasi.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } else {
            lay.setVisibility(View.VISIBLE);
            TextView a = (TextView) v.findViewById(R.id.txtEmptyaspirasi);
            a.setText("Maaf, untuk sekarang belum ada Aspirasi !, Mulailah menuliskan Aspirasi Anda !");
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new TambahAspirasi();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.mainframe, fragment);
                ft.commit();
            }
        });

        return v;
    }

    public void getdataAspirasi() {
        //hardcode
        if (listaspirasi.isEmpty()) {
            listaspirasi.add(new AspirasiModel("halo ini adalah tweet pertama loh wkwk", "wildan", "", "01", "12"));
            listaspirasi.add(new AspirasiModel("halo ini adalah tweet kedua loh wkwk", "wendi", "", "01", "12"));
            listaspirasi.add(new AspirasiModel("halo ini adalah tweet ketiga loh wkwk", "rahmat", "", "01", "12"));
            listaspirasi.add(new AspirasiModel("halo ini adalah tweet keempat loh wkwk", "indah", "", "01", "12"));
        }
    }


}
