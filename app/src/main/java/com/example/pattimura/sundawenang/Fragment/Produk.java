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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pattimura.sundawenang.Adapter.AdapterProduk;
import com.example.pattimura.sundawenang.Model.ProdukModel;
import com.example.pattimura.sundawenang.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class Produk extends Fragment {
    AdapterProduk adapter;
    RelativeLayout lay;
    ListView list;
    ArrayList<ProdukModel> daftarproduk = new ArrayList<>();

    public Produk() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_produk, container, false);
        list = (ListView) v.findViewById(R.id.listproduk);
        lay = (RelativeLayout) v.findViewById(R.id.layoutproduk);
        getallproduk();


        return v;
    }

//    private void getallproduk() {
//        daftarproduk.add(new ProdukModel(lorem(), "Produk Tanah Liat", "17 Februari 2017", "09888"));
//        daftarproduk.add(new ProdukModel("ini adalah deskripsi produk", "Produk Boneka", "19 Februari 2017", "087656"));
//        daftarproduk.add(new ProdukModel("ini adalah deskripsi produk", "Produk Rotan", "20 Februari 2017", "0192876"));
//        daftarproduk.get(0).addGambar("Guci Sasirangan", R.drawable.produk1);
//        daftarproduk.get(0).addGambar("Tanah Liat", R.drawable.produk1a);
//        daftarproduk.get(0).addGambar("Tanah Liat1", R.drawable.produk1b);
//        daftarproduk.get(1).addGambar("Boneka Lucu", R.drawable.produk2);
//        daftarproduk.get(1).addGambar("Boneka Lucu1", R.drawable.produk1a);
//    }


//    private String lorem() {
//        return "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";
//    }

    void getallproduk() {
        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://94.177.203.179/api/product",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray listdata = new JSONArray(response);
                            for (int i = 0; i < listdata.length(); i++) {
                                JSONObject object = listdata.getJSONObject(i);
                                ProdukModel pm = new ProdukModel(object.getString("description"), object.getString("name_product"), object.getString("created_at"), object.getString("phone"));
                                pm.addGambar("Produk ", object.getString("photo_id"));
                                daftarproduk.add(pm);
                            }
                            if (!daftarproduk.isEmpty()) {
                                lay.setVisibility(View.GONE);
                                adapter = new AdapterProduk(Produk.this.getContext(), daftarproduk);
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

                            }
                        } catch (Exception e) {
                            Toast.makeText(Produk.this.getContext(), "error : " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
//tempat response di dapatkan
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                        error.printStackTrace();
                        Toast.makeText(Produk.this.getContext(), "erroring: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                try {
                    //Adding parameters to request

                    //returning parameter
                    return params;
                } catch (Exception e) {
                    Toast.makeText(Produk.this.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    return params;
                }
            }
        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this.getContext());
        requestQueue.add(stringRequest);
    }

}
