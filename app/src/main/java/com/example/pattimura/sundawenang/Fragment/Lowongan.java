package com.example.pattimura.sundawenang.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pattimura.sundawenang.Adapter.AdpterLowongan;
import com.example.pattimura.sundawenang.Model.LowonganModel;
import com.example.pattimura.sundawenang.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Lowongan extends Fragment {
    AdpterLowongan adapter;
    ArrayList<LowonganModel> daftarlowongan = new ArrayList<>();

    public Lowongan() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_lowongan, container, false);

        ListView listlowongan = (ListView) v.findViewById(R.id.listLowongan);
        RelativeLayout kosong = (RelativeLayout) v.findViewById(R.id.layoutlowongan);
        getAlllowongan();
        if (!daftarlowongan.isEmpty()) {
            kosong.setVisibility(View.GONE);
            adapter = new AdpterLowongan(this.getContext(), daftarlowongan);
            listlowongan.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            listlowongan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    LowonganModel pm = daftarlowongan.get(position);
                    Bundle b = new Bundle();
                    Fragment f = new DetailLowongan();
                    b.putParcelable("Lowongan", pm);
                    f.setArguments(b);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.mainframe, f);
                    ft.commit();
                }
            });
        } else {
            kosong.setVisibility(View.VISIBLE);
            TextView tulis = (TextView) v.findViewById(R.id.txtEmpty);
            tulis.setText("Maaf, untuk sekarang belum ada lowongan yang tersedia !");
        }


        return v;
    }

    private void getAlllowongan() {
        daftarlowongan.add(new LowonganModel("Membangun rumah", "17 Februari 2017", lorem()));
        daftarlowongan.add(new LowonganModel("Merangkai corak bunga", "16 Februari 2017", lorem()));
        daftarlowongan.get(0).addGambar("Membangun Rumah", R.drawable.oranggenteng);
        daftarlowongan.get(1).addGambar("Merangkai Corak Bunga", R.drawable.ibujahit);
    }

    private String lorem() {
        return "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";
    }

}
