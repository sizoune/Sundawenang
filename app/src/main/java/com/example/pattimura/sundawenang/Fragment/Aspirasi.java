package com.example.pattimura.sundawenang.Fragment;


import android.os.Bundle;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pattimura.sundawenang.Adapter.AdapterAspirasi;
import com.example.pattimura.sundawenang.Model.AspirasiModel;
import com.example.pattimura.sundawenang.R;
import com.github.clans.fab.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class Aspirasi extends Fragment {
    ArrayList<AspirasiModel> listaspirasi;
    AdapterAspirasi adapter;
    Fragment fragment;
    RelativeLayout lay;
    ListView listAspirasi;

    public Aspirasi() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_aspirasi, container, false);
        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);
        ImageView cover = (ImageView) v.findViewById(R.id.imageView5);
        listAspirasi = (ListView) v.findViewById(R.id.listAspirasi);

        Picasso.with(Aspirasi.this.getContext()).load(R.drawable.aspirasi).fit().into(cover);
        listaspirasi = new ArrayList<>();
        adapter = new AdapterAspirasi(Aspirasi.this.getContext(), listaspirasi);
        lay = (RelativeLayout) v.findViewById(R.id.layoutaspirasi);
        getdataAspirasi();


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

//    public void getdataAspirasi() {
//        //hardcode
//        if (listaspirasi.isEmpty()) {
//            listaspirasi.add(new AspirasiModel("halo ini adalah tweet pertama loh wkwk", "wildan", "", "01", "12"));
//            listaspirasi.add(new AspirasiModel("halo ini adalah tweet kedua loh wkwk", "wendi", "", "01", "12"));
//            listaspirasi.add(new AspirasiModel("halo ini adalah tweet ketiga loh wkwk", "rahmat", "", "01", "12"));
//            listaspirasi.add(new AspirasiModel("halo ini adalah tweet keempat loh wkwk", "indah", "", "01", "12"));
//        }
//    }

    public void getdataAspirasi() {
        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://94.177.203.179/api/aspiration",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray listdata = new JSONArray(response);
                            for (int i = 0; i < listdata.length(); i++) {
                                JSONObject object = listdata.getJSONObject(i);
                                AspirasiModel asp = new AspirasiModel(object.getString("aspiration"), object.getString("name"), "http://94.177.203.179/storage/" + object.getString("photo_id"), object.getString("rt"), object.getString("rw"));
                                //Toast.makeText(Aspirasi.this.getContext(), asp.getIsi(), Toast.LENGTH_SHORT).show();
                                listaspirasi.add(asp);
                            }
                            if (!listaspirasi.isEmpty()) {
                                lay.setVisibility(View.GONE);
                                listAspirasi.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            } else {
                                lay.setVisibility(View.VISIBLE);
                            }
                        } catch (Exception e) {
                            Toast.makeText(Aspirasi.this.getContext(), "error : " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
//tempat response di dapatkan
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                        error.printStackTrace();
                        Toast.makeText(Aspirasi.this.getContext(), "erroring: " + error.getMessage(), Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(Aspirasi.this.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    return params;
                }
            }
        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this.getContext());
        requestQueue.add(stringRequest);
    }
}



