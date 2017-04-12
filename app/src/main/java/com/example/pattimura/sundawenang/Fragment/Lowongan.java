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
import com.example.pattimura.sundawenang.Adapter.AdpterLowongan;
import com.example.pattimura.sundawenang.Model.AspirasiModel;
import com.example.pattimura.sundawenang.Model.LowonganModel;
import com.example.pattimura.sundawenang.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class Lowongan extends Fragment {
    AdpterLowongan adapter;
    ArrayList<LowonganModel> daftarlowongan = new ArrayList<>();
    RelativeLayout kosong;
    ListView listlowongan;

    public Lowongan() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_lowongan, container, false);

        listlowongan = (ListView) v.findViewById(R.id.listLowongan);
        kosong = (RelativeLayout) v.findViewById(R.id.layoutlowongan);
        getAlllowongan();



        return v;
    }

//    private void getAlllowongan() {
//        daftarlowongan.add(new LowonganModel("Membangun rumah", "17 Februari 2017", lorem()));
//        daftarlowongan.add(new LowonganModel("Merangkai corak bunga", "16 Februari 2017", lorem()));
//        daftarlowongan.get(0).addGambar("Membangun Rumah", R.drawable.oranggenteng);
//        daftarlowongan.get(1).addGambar("Merangkai Corak Bunga", R.drawable.ibujahit);
//    }
//
//    private String lorem() {
//        return "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";
//    }

    void getAlllowongan() {
        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://94.177.203.179/api/job",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray listdata = new JSONArray(response);
                            for (int i = 0; i < listdata.length(); i++) {
                                JSONObject object = listdata.getJSONObject(i);
                                LowonganModel asp = new LowonganModel(object.getString("job_name"), object.getString("created_at"),object.getString("description"),object.getString("job_owner"),object.getString("phone"));
                                //Toast.makeText(Lowongan.this.getContext(), asp.getIsi(), Toast.LENGTH_SHORT).show();
                                daftarlowongan.add(asp);
                            }
                            if (!daftarlowongan.isEmpty()) {
                                kosong.setVisibility(View.GONE);
                                adapter = new AdpterLowongan(Lowongan.this.getContext(), daftarlowongan);
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
                            }
                        } catch (Exception e) {
                            Toast.makeText(Lowongan.this.getContext(), "error : " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
//tempat response di dapatkan
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                        error.printStackTrace();
                        Toast.makeText(Lowongan.this.getContext(), "erroring: " + error.getMessage(), Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(Lowongan.this.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    return params;
                }
            }
        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this.getContext());
        requestQueue.add(stringRequest);

    }

}
