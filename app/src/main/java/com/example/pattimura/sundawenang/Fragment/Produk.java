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
import android.widget.Toast;

import com.example.pattimura.sundawenang.Adapter.AdapterProduk;
import com.example.pattimura.sundawenang.Model.ProdukModel;
import com.example.pattimura.sundawenang.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Produk extends Fragment {
    AdapterProduk adapter;
    ArrayList<ProdukModel> daftarproduk = new ArrayList<>();

    public Produk() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_produk, container, false);
        ListView list = (ListView) v.findViewById(R.id.listproduk);
        RelativeLayout lay = (RelativeLayout) v.findViewById(R.id.layoutproduk);
        getallproduk();
        if (!daftarproduk.isEmpty()) {
            lay.setVisibility(View.GONE);
            adapter = new AdapterProduk(this.getContext(), daftarproduk);
            list.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ProdukModel pm = daftarproduk.get(position);
                    Bundle b = new Bundle();
                    Fragment f = new DetailProduk();
                    b.putParcelable("Produk", pm);
                    f.setArguments(b);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.mainframe, f);
                    ft.commit();
                }
            });
        } else {
            lay.setVisibility(View.VISIBLE);
            TextView a = (TextView) v.findViewById(R.id.txtEmptyproduk);
            a.setText("Maaf, untuk sekarang belum ada Produk yang dijual !");
        }


        return v;
    }

    private void getallproduk() {
        daftarproduk.add(new ProdukModel(lorem(), "Produk Tanah Liat", "17 Februari 2017", "09888"));
        daftarproduk.add(new ProdukModel("ini adalah deskripsi produk", "Produk Boneka", "19 Februari 2017", "087656"));
        daftarproduk.add(new ProdukModel("ini adalah deskripsi produk", "Produk Rotan", "20 Februari 2017", "0192876"));
        daftarproduk.get(0).addGambar("Guci Sasirangan", R.drawable.produk1);
        daftarproduk.get(0).addGambar("Tanah Liat", R.drawable.produk1a);
        daftarproduk.get(0).addGambar("Tanah Liat1", R.drawable.produk1b);
        daftarproduk.get(1).addGambar("Boneka Lucu", R.drawable.produk2);
        daftarproduk.get(1).addGambar("Boneka Lucu1", R.drawable.produk1a);
    }

    private String lorem() {
        return "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";
    }

}
