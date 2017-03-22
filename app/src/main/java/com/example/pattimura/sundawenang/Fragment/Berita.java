package com.example.pattimura.sundawenang.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pattimura.sundawenang.Adapter.AdapterBerita;
import com.example.pattimura.sundawenang.Model.BeritaModel;
import com.example.pattimura.sundawenang.R;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Berita extends Fragment {
    AdapterBerita adapter;
    ArrayList<BeritaModel> daftarberita = new ArrayList<>();

    public Berita() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_berita, container, false);


        ListView list = (ListView) v.findViewById(R.id.listberita);
        //MaterialEditText cari = (MaterialEditText) v.findViewById(R.id.txtfilterberita);
        getAllBerita();
        RelativeLayout lay = (RelativeLayout) v.findViewById(R.id.layoutberita);
        if (!daftarberita.isEmpty()) {
            lay.setVisibility(View.GONE);
            adapter = new AdapterBerita(this.getContext(), daftarberita);
            list.setAdapter(adapter);
            adapter.notifyDataSetChanged();
//        cari.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                System.out.println("Text [" + s + "] - Start [" + start + "] - Before [" + before + "] - Count [" + count + "]");
//                if (count < before) {
//                    // We're deleting char so we need to reset the adapter data
//                    adapter.resetData();
//                }
//                adapter.getFilter().filter(s.toString());
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    BeritaModel bm = daftarberita.get(position);
                    Bundle b = new Bundle();
                    Fragment f = new DetailBerita();
                    b.putParcelable("Berita", bm);
                    f.setArguments(b);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.mainframe, f);
                    ft.commit();
                }
            });
        } else {
            lay.setVisibility(View.VISIBLE);
            TextView a = (TextView) v.findViewById(R.id.txtEmptyberita);
            a.setText("Maaf, untuk sekarang belum ada Berita !");
        }


        return v;
    }

    private void getAllBerita() {
        daftarberita.add(new BeritaModel(lorem(), "Kades Sambut Wali Band Renovasi Mushola Al-Ikhlas", "17 Februari 2017", "Kabar Desa, Publikasi"));
        daftarberita.add(new BeritaModel(lorem(), "Ambulance Desa Sundawenang", "16 Februari 2017", "Kabar Desa, Publikasi"));
        daftarberita.add(new BeritaModel(lorem(), "Ridwan Kamil Menerima Penghargaan HAM", "02 Maret 2017", "Bandung, Publikasi"));
        daftarberita.get(0).setUrlvideo("https://www.youtube.com/embed/dxkXpKnS3k0");
        daftarberita.get(0).addGambar("Acara Syukuran", R.drawable.waliband);
        daftarberita.get(1).addGambar("Mobil Ambulan", R.drawable.ambulan);
    }

    private String lorem() {
        return "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";
    }

}
