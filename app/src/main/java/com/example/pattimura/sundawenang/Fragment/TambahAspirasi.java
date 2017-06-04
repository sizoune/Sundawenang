package com.example.pattimura.sundawenang.Fragment;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pattimura.sundawenang.Model.AspirasiModel;
import com.example.pattimura.sundawenang.R;
import com.example.pattimura.sundawenang.Utility;
import com.example.pattimura.sundawenang.VolleyHelper.AppHelper;
import com.example.pattimura.sundawenang.VolleyHelper.VolleyMultipartRequest;
import com.example.pattimura.sundawenang.VolleyHelper.VolleySingleton;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import me.drakeet.materialdialog.MaterialDialog;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class TambahAspirasi extends Fragment implements View.OnClickListener {
    private ProgressDialog mProgressDialog;
    private MaterialEditText isiAspirasi, nama, rt, rw;
    private TextView txtKtp;
    private ImageView fotoktp, temp;
    private String namafile;
    private Uri ktpFileUri = null;
    private File files;
    private Button submit;
    private Drawable picktp;
    private MaterialDialog mMaterialDialog;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    private String TAG, userChoosenTask;
    private boolean result = false;

    public TambahAspirasi() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tambah_aspirasi, container, false);

        temp = new ImageView(TambahAspirasi.this.getContext());
        userChoosenTask = "";
        fotoktp = (ImageView) v.findViewById(R.id.imageKTPAspirasi);
        isiAspirasi = (MaterialEditText) v.findViewById(R.id.txtisiAspirasi);
        nama = (MaterialEditText) v.findViewById(R.id.txtNamaAspirasi);
        rt = (MaterialEditText) v.findViewById(R.id.txtRTAspirasi);
        rw = (MaterialEditText) v.findViewById(R.id.txtRWAspirasi);
        txtKtp = (TextView) v.findViewById(R.id.textViewKTPTambahaspirasi);
        submit = (Button) v.findViewById(R.id.buttonKirimAspirasi);

        SharedPreferences prefs = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        TAG = prefs.getString("TAG", "not found");

        //Toast.makeText(TambahAspirasi.this.getContext(), token, Toast.LENGTH_SHORT).show();
        Picasso.with(this.getContext()).load(R.drawable.buttonuploadfotobiru).fit().into(fotoktp);

        fotoktp.setOnClickListener(this);
        submit.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        if (v == submit) {
            String isi = isiAspirasi.getText().toString();
            String name = nama.getText().toString();
            String nort = rt.getText().toString();
            String norw = rw.getText().toString();
            if (!(isi.equals("")) && !(name.equals("")) && !(nort.equals("")) && !(norw.equals("")) && ktpFileUri != null) {
                //showProgressDialog();
                kirimData(ktpFileUri, isi, name, nort, norw);
                //postGambar(ktpFileUri);
                final Fragment f = new Aspirasi();
                //hideProgressDialog();
                View vi = View.inflate(getContext(), R.layout.layoutdialog, null);
                mMaterialDialog = new MaterialDialog(TambahAspirasi.this.getContext())
                        .setTitle("Tenant Changed !")
                        .setView(vi)
                        //.setMessage("Tenant name : " + getArguments().getString("nama") + "\nDescription : " + getArguments().getString("desc") + "\nStatus : " + getArguments().getString("status"))
                        .setPositiveButton("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mMaterialDialog.dismiss();
                                FragmentTransaction ft = getFragmentManager().beginTransaction();
                                ft.replace(R.id.mainframe, f);
                                ft.commit();
                            }
                        });
                mMaterialDialog.show();
            } else {
                Toast.makeText(TambahAspirasi.this.getContext(), "Tolong lengkapi !, isi aspirasi, nama, rt, rw, ktp", Toast.LENGTH_SHORT).show();
                return;
            }
        } else if (v == fotoktp) {
            photoBuilder();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    Log.e("gif--", "fragment back key is clicked");
                    getActivity().getSupportFragmentManager().popBackStack(TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    return true;
                }
                return false;
            }
        });
    }


    private void kirimData(final Uri fileUri, final String isi, final String nama, final String rt, final String rw) {
        //final ProgressDialog dialog = ProgressDialog.show(TambahAspirasi.this.getContext(), "", "Loading. Please wait...", true);
        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, "http://94.177.203.179/api/aspiration", new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String resultResponse = new String(response.data);
//                try {
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
//
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(TambahAspirasi.this.getContext(), "Tidak dapat memuat data\nTolong perika koneksi internet anda !", Toast.LENGTH_LONG).show();
                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("rt", rt);
                params.put("rw", rw);
                params.put("aspiration", isi);
                params.put("name", nama);
                params.put("title", nama);
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                // file name could found file base or direct access from real path
                // for now just get bitmap data from ImageView
                params.put("photo_id", new DataPart(fileUri.getLastPathSegment(), AppHelper.getFileDataFromDrawable(TambahAspirasi.this.getContext(), fotoktp.getDrawable()), "image/jpeg"));

                return params;
            }
        };

        VolleySingleton.getInstance(TambahAspirasi.this.getContext()).addToRequestQueue(multipartRequest);
    }


    private void photoBuilder() {
        AlertDialog.Builder builder = new AlertDialog.Builder(TambahAspirasi.this.getContext());
        builder.setPositiveButton("Ambil Gambar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                userChoosenTask = "Ambil Foto";
                result = Utility.checkPermission(TambahAspirasi.this.getContext());
                if (result) {
                    launchCamera();
                }
            }
        });
        builder.setNegativeButton("Pilih dari Galery", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                userChoosenTask = "Pilih dari Galeri";
                result = Utility.checkPermission(TambahAspirasi.this.getContext());
                if (result) {
                    pickdariGalery();
                }
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask.equals("Ambil Foto"))
                        launchCamera();
                    else if (userChoosenTask.equals("Pilih dari Galeri"))
                        pickdariGalery();
                } else {
                    //code for deny
                }
                break;
        }
    }

    private void launchCamera() {

        // Create intent
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Choose file storage location
        File file = new File(Environment.getExternalStorageDirectory(), UUID.randomUUID().toString() + ".jpg");
        files = file;
        ktpFileUri = FileProvider.getUriForFile(TambahAspirasi.this.getContext(), TambahAspirasi.this.getActivity().getApplicationContext().getPackageName() + ".provider", file);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, ktpFileUri);
        namafile = ktpFileUri.getLastPathSegment();
        // Launch intent
        startActivityForResult(takePictureIntent, 0);
    }

    private void pickdariGalery() {
        Intent i = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, 1);
    }


    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(TambahAspirasi.this.getContext());
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                txtKtp.setText(namafile);
                //postGambar(ktpFileUri);
                picktp = Drawable.createFromPath(ktpFileUri.getPath());
                Picasso.with(TambahAspirasi.this.getContext()).load(files).fit().into(fotoktp);
            } else {
                Toast.makeText(TambahAspirasi.this.getContext(), "Photo gagal diambil, tolong ulangi lagi !", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = TambahAspirasi.this.getContext().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String filePath = cursor.getString(columnIndex);
                cursor.close();
                File file = new File(filePath);
                files = file;
                ktpFileUri = Uri.fromFile(file);
                picktp = Drawable.createFromPath(ktpFileUri.getPath());
                //postGambar(ktpFileUri);
                txtKtp.setText(ktpFileUri.getLastPathSegment());
                Picasso.with(TambahAspirasi.this.getContext()).load(file).fit().into(fotoktp);
            } else {
                Toast.makeText(TambahAspirasi.this.getContext(), "Photo gagal diambil, tolong ulangi lagi !", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
