package com.example.pattimura.sundawenang.Fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pattimura.sundawenang.Adapter.AdapterBerita;
import com.example.pattimura.sundawenang.Model.BeritaModel;
import com.example.pattimura.sundawenang.R;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class Berita extends Fragment {
    AdapterBerita adapter;
    ArrayList<BeritaModel> daftarberita = new ArrayList<>();
    RelativeLayout lay;
    ListView list;
    private ProgressDialog mProgressDialog;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    SharedPreferences.Editor editor;
    private String TAG;

    public Berita() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        editor = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_berita, container, false);
        list = (ListView) v.findViewById(R.id.listberita);
        //MaterialEditText cari = (MaterialEditText) v.findViewById(R.id.txtfilterberita);
        showProgressDialog();
        getAllBerita();
        lay = (RelativeLayout) v.findViewById(R.id.layoutberita);
        adapter = new AdapterBerita(Berita.this.getContext(), daftarberita);
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

        return v;
    }

    private void getAllBerita() {
//        daftarberita.add(new BeritaModel(descBerita1(), "Kades Sambut Wali Band Renovasi Mushola Al-Ikhlas", "17 Februari 2017", "Kabar Desa, Publikasi"));
//        daftarberita.add(new BeritaModel(lorem(), "Ambulance Desa Sundawenang", "16 Februari 2017", "Kabar Desa, Publikasi"));
//        daftarberita.add(new BeritaModel(lorem(), "Ridwan Kamil Menerima Penghargaan HAM", "02 Maret 2017", "Bandung, Publikasi"));
//        daftarberita.get(0).setUrlvideo("https://www.youtube.com/embed/dxkXpKnS3k0");
//        daftarberita.get(0).addGambar("Acara Syukuran", "http://sundawenang-parungkuda.desa.id/wp-content/uploads/sites/391/2017/02/LOGO-RPJM-300x75.png");
//        daftarberita.get(1).addGambar("Mobil Ambulan", "http://sundawenang-parungkuda.desa.id/wp-content/uploads/sites/391/2017/02/LOGO-RPJM-300x75.png");
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://sundawenang-parungkuda.16mb.com/wp-json/wp/v2/posts?&_embed=true",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray listdata = new JSONArray(response);
                            for (int i = 0; i < listdata.length(); i++) {
                                JSONObject data = listdata.getJSONObject(i);
                                JSONObject judulO = data.getJSONObject("title");
                                JSONObject isiO = data.getJSONObject("guid");
                                JSONObject gmb = data.getJSONObject("_embedded");
                                String gambar = "";
                                if (gmb.has("wp:featuredmedia")) {
                                    JSONArray media = gmb.getJSONArray("wp:featuredmedia");
                                    JSONObject iss = media.getJSONObject(0);
                                    gambar = iss.getString("source_url");
                                    String judul = judulO.getString("rendered");
                                    String desc = isiO.getString("rendered");
                                    String kategori = data.getString("status");
                                    String tanggal = data.getString("date");
                                    BeritaModel bm = new BeritaModel(desc, judul, tanggal, kategori);
                                    bm.addGambar("berita", gambar);
                                    daftarberita.add(bm);
                                } else {
                                    String judul = judulO.getString("rendered");
                                    String desc = isiO.getString("rendered");
                                    String kategori = data.getString("status");
                                    String tanggal = data.getString("date");
                                    BeritaModel bm = new BeritaModel(desc, judul, tanggal, kategori);
                                    daftarberita.add(bm);
                                }

                            }
                            if (!daftarberita.isEmpty()) {
                                hideProgressDialog();
                                lay.setVisibility(View.GONE);
                                adapter = new AdapterBerita(Berita.this.getContext(), daftarberita);
                                list.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        BeritaModel bm = daftarberita.get(position);
                                        Bundle b = new Bundle();
                                        Fragment f = new DetailBerita();
                                        b.putParcelable("Berita", bm);
                                        TAG = "Berita";
                                        editor.putString("TAG", TAG);
                                        editor.commit();
                                        f.setArguments(b);
                                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                                        ft.replace(R.id.mainframe, f);
                                        ft.addToBackStack(TAG);
                                        ft.commit();
                                    }
                                });
                            } else {
                                hideProgressDialog();
                                lay.setVisibility(View.VISIBLE);
                            }
                        } catch (Exception e) {
                            Toast.makeText(Berita.this.getContext(), "error : " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
//tempat response di dapatkan
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                        error.printStackTrace();
                        Toast.makeText(Berita.this.getContext(), "erroring: " + error.getMessage(), Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(Berita.this.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    return params;
                }
            }
        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this.getContext());
        requestQueue.add(stringRequest);
    }

    private String lorem() {
        return "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";
    }

    private String descBerita1() {
        return " <p>Kepala Desa Sundawenang berserta Muspika dan warga, Kamis (16/2) sore, sambut kedatangan Grup Band Wali di Kampung Pangadegan RT 17/08, Desa Sundawenang Kecamatan Parungkuda Kabupaten Sukabumi. Kedatangan Apoy dan rekan-rekannya bukan untuk konser melainkan untuk mewujudkan pembangunan 100 Mushola Indah.</p> <p><iframe frameborder=\"0\" width=\"640\" height=\"362\" src=\"https://www.dailymotion.com/embed/video/x5c6jvt\" allowfullscreen></iframe></p> <blockquote><p>Perbaikan Mushola Al-Ikhlas ini dikarenakan kondisi yang cukup rusak sehingga Program Muin dari Grup Band Wali bisa direalisasikan. Waliband mencanangkan waktu perbaikan ini bisa rampung dalam kurun waktu satu bulan.</p> <p>“Mudah-mudahan tidak 100 Mushola saja yang diperbaiki akan tetapi bisa 1000 atau 1 juta mushola yang diperbaiki” tutur Kepala desa Sundawenang. Program ini mudah-mudahan bisa memotivasi warga Desa sundawenang beserta pihak-pihak terkait untuk mewujudkan Pembangunan di Desa Sundawenang. “Kami kesini bukan bukan memberikan uang, melaikan kami kesini untuk menyindir bahwa kami saja yang jauh peduli masa pribuminya tidak peduli’ ungkap Kang Apoy.</p> <p>#salam 5 waktu.</p> <p>Reporter: Kang Zey &amp; Redaktur: Rizzal</p></blockquote> <p>&nbsp;</p>";
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(Berita.this.getContext());
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

}
