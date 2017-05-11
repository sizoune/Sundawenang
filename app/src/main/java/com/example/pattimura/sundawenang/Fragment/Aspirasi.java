package com.example.pattimura.sundawenang.Fragment;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
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
import com.baoyz.widget.PullRefreshLayout;
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

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class Aspirasi extends Fragment {
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    ArrayList<AspirasiModel> listaspirasi;
    AdapterAspirasi adapter;
    Fragment fragment;
    RelativeLayout lay;
    String token;
    ListView listAspirasi;
    int currentpage, lastpage, banyakdata, currentFirstVisibleItem, currentVisibleItemCount, currentScrollState;
    private ProgressDialog mProgressDialog;
    private boolean loading = false;

    public Aspirasi() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        currentpage = 1;
        lastpage = 1;
        View v = inflater.inflate(R.layout.fragment_aspirasi, container, false);
        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);
        ImageView cover = (ImageView) v.findViewById(R.id.imageView5);
        listAspirasi = (ListView) v.findViewById(R.id.listAspirasi);
        SharedPreferences prefs = Aspirasi.this.getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        token = prefs.getString("token", "not found");
        Picasso.with(Aspirasi.this.getContext()).load(R.drawable.aspirasi).fit().into(cover);
        listaspirasi = new ArrayList<>();
        adapter = new AdapterAspirasi(Aspirasi.this.getContext(), listaspirasi);
        lay = (RelativeLayout) v.findViewById(R.id.layoutaspirasi);
        showProgressDialog();
        getdataAspirasi(currentpage);
        adapter.notifyDataSetChanged();

        final PullRefreshLayout layout = (PullRefreshLayout) v.findViewById(R.id.swipeRefreshLayout);
        layout.setRefreshStyle(PullRefreshLayout.STYLE_MATERIAL);
        layout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                layout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (currentpage != lastpage) {
                            listaspirasi = new ArrayList<>();
                            adapter = new AdapterAspirasi(Aspirasi.this.getContext(), listaspirasi);
                            getdataAspirasi(currentpage);
                        }
                        layout.setRefreshing(false);
                    }
                }, 1000);
            }
        });

        listAspirasi.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                currentScrollState = scrollState;
                isScrollCompleted();
            }

            private void isScrollCompleted() {
                if (currentVisibleItemCount > 0 && currentScrollState == SCROLL_STATE_IDLE) {
                    /*** In this way I detect if there's been a scroll which has completed ***/
                    /*** do the work for load more date! ***/
                    if (currentpage != lastpage) {
                        currentpage++;
                        getdataAspirasi(currentpage);
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                //Toast.makeText(Aspirasi.this.getContext(), Integer.toString(totalItemCount), Toast.LENGTH_SHORT).show();
                currentFirstVisibleItem = firstVisibleItem;
                currentVisibleItemCount = visibleItemCount;
            }
        });
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

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(Aspirasi.this.getContext());
            mProgressDialog.setMessage("Mohon tunggu, sedang mengambil data !");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
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

    public void getdataAspirasi(int page) {
        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://94.177.203.179/api/aspiration?token=\"" + token + "\"&&page=" + page,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject listdata = new JSONObject(response);
                            currentpage = listdata.getInt("current_page");
                            lastpage = listdata.getInt("last_page");
                            banyakdata = listdata.getInt("total");
                            JSONArray isiaspirasi = listdata.getJSONArray("data");
                            for (int i = 0; i < isiaspirasi.length(); i++) {
                                JSONObject object = isiaspirasi.getJSONObject(i);
                                AspirasiModel asp = new AspirasiModel(object.getString("aspiration"), object.getString("name"), "http://94.177.203.179/storage/" + object.getString("photo_id"), object.getString("rt"), object.getString("rw"));
                                listaspirasi.add(asp);
                            }
//                            for (int i = 0; i < listdata.length(); i++) {
//                                JSONObject object = listdata.getJSONObject(i);
//                                AspirasiModel asp = new AspirasiModel(object.getString("aspiration"), object.getString("name"), "http://94.177.203.179/storage/" + object.getString("photo_id"), object.getString("rt"), object.getString("rw"));
//                                //Toast.makeText(Aspirasi.this.getContext(), asp.getIsi(), Toast.LENGTH_SHORT).show();
//                                listaspirasi.add(asp);
//                            }
                            if (!listaspirasi.isEmpty()) {
                                hideProgressDialog();
                                lay.setVisibility(View.GONE);
                                listAspirasi.setAdapter(adapter);
                                loading = false;
                                adapter.notifyDataSetChanged();
                            } else {
                                hideProgressDialog();
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
                        //Toast.makeText(Aspirasi.this.getContext(), "erroring: " + error.getMessage(), Toast.LENGTH_SHORT).show();
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



