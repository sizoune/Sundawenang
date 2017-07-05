package com.example.pattimura.sundawenang.Fragment;


import android.app.AlertDialog;
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
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
import com.example.pattimura.sundawenang.R;
import com.example.pattimura.sundawenang.Utility;
import com.example.pattimura.sundawenang.VolleyHelper.AppHelper;
import com.example.pattimura.sundawenang.VolleyHelper.VolleyMultipartRequest;
import com.example.pattimura.sundawenang.VolleyHelper.VolleySingleton;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import fr.ganfra.materialspinner.MaterialSpinner;
import me.drakeet.materialdialog.MaterialDialog;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;
import static com.example.pattimura.sundawenang.R.drawable.layanan;

/**
 * A simple {@link Fragment} subclass.
 */
public class Layanan extends Fragment implements View.OnClickListener {
    private MaterialSpinner spinner;
    private Uri pajakFileUri = null;
    private Uri ktpFileUri = null;
    private Uri lainFileUri = null;
    private String status = "";
    private String namafile, userChoosenTask;
    private File filesktp, filespajak, fileslainnya;
    private ImageView ktp, pajak, lainnya;
    private Button submit;
    private TextView txtKtp, txtPajak, txtLainnya;
    private MaterialEditText nama, notel, njop;
    private Drawable picktp, picpajak, piclain;
    private MaterialDialog mMaterialDialog;
    private boolean result;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    private String TAG;

    public Layanan() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_layanan, container, false);

        SharedPreferences prefs = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        TAG = prefs.getString("TAG", "not found");

        result = false;
        ktp = (ImageView) v.findViewById(R.id.imageViewktplayanan);
        pajak = (ImageView) v.findViewById(R.id.imageViewpajaklayanan);
        lainnya = (ImageView) v.findViewById(R.id.imageViewlainlayanan);
        txtKtp = (TextView) v.findViewById(R.id.txtKTP);
        txtPajak = (TextView) v.findViewById(R.id.txtPajak);
        txtLainnya = (TextView) v.findViewById(R.id.txtLainnya);
        nama = (MaterialEditText) v.findViewById(R.id.txtNama);
        notel = (MaterialEditText) v.findViewById(R.id.txtTelp);
        njop = (MaterialEditText) v.findViewById(R.id.txtNJOP);
        submit = (Button) v.findViewById(R.id.buttonLayanan);

        Picasso.with(this.getContext()).load(R.drawable.buttonuploadfotobiru).fit().into(ktp);
        Picasso.with(this.getContext()).load(R.drawable.buttonuploadfotoijo).fit().into(pajak);
        Picasso.with(this.getContext()).load(R.drawable.buttonuploalainnya).fit().into(lainnya);


        String[] layanan = {"Pembuatan E-KTP", "Pembuatan Kartu Keluarga", "Layanan Posyandu"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Layanan.this.getContext(), android.R.layout.simple_spinner_item, layanan);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = (MaterialSpinner) v.findViewById(R.id.spinner1);
        spinner.setAdapter(adapter);

        ktp.setOnClickListener(this);
        pajak.setOnClickListener(this);
        submit.setOnClickListener(this);
        lainnya.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        if (v == ktp) {
            status = "ktp";
            photoBuilder();
        } else if (v == pajak) {
            status = "pajak";
            photoBuilder();
        } else if (v == lainnya) {
            status = "lainnya";
            photoBuilder();
        } else if (v == submit) {
            if (!nama.getText().toString().equals("") && ktpFileUri != null && pajakFileUri != null && !notel.getText().toString().equals("") && spinner.getSelectedItemPosition() != 0 && !njop.getText().toString().equals("")) {
                kirimData(nama.getText().toString(), spinner.getSelectedItem().toString(), notel.getText().toString(), ktpFileUri, pajakFileUri, lainFileUri, njop.getText().toString());
                View vi = View.inflate(getContext(), R.layout.layoutdialog, null);
                mMaterialDialog = new MaterialDialog(Layanan.this.getContext())
                        .setTitle("Tenant Changed !")
                        .setView(vi)
                        //.setMessage("Tenant name : " + getArguments().getString("nama") + "\nDescription : " + getArguments().getString("desc") + "\nStatus : " + getArguments().getString("status"))
                        .setPositiveButton("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mMaterialDialog.dismiss();
                            }
                        });
                mMaterialDialog.show();
                clearData();

            } else {
                Toast.makeText(Layanan.this.getContext(), "Tolong lengkapi, Jenis layanan dan nama dan no telepon dan ktp dan pajak !", Toast.LENGTH_SHORT).show();
                clearData();
            }
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
                    Layanan.this.getActivity().finish();
                    return true;
                }
                return false;
            }
        });
    }

    void clearData() {
        spinner.setSelection(0);
        nama.setText("");
        notel.setText("");
        njop.setText("");
        ktpFileUri = null;
        pajakFileUri = null;
        Picasso.with(this.getContext()).load(R.drawable.buttonuploadfotobiru).fit().into(ktp);
        Picasso.with(this.getContext()).load(R.drawable.buttonuploadfotoijo).fit().into(pajak);
        Picasso.with(this.getContext()).load(R.drawable.buttonuploalainnya).fit().into(lainnya);
        txtKtp.setText("Upload Foto KTP");
        txtPajak.setText("Upload Foto Pajak");
        txtLainnya.setText("Upload Lainnya");

    }

    private void kirimData(final String nama, final String tipe, final String pone, final Uri ktpuri, final Uri pajakuri, final Uri lainnyauri, final String njop) {
        //final ProgressDialog dialog = ProgressDialog.show(TambahAspirasi.this.getContext(), "", "Loading. Please wait...", true);
        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, "http://212.237.31.161/api/service", new Response.Listener<NetworkResponse>() {
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
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(Layanan.this.getContext(), "Tidak dapat memuat data\nTolong perika koneksi internet anda !", Toast.LENGTH_LONG).show();
                }
//
                //dialog.dismiss();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("name", nama);
                params.put("type_service", tipe);
                params.put("phone", pone);
                params.put("nop", njop);
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                // file name could found file base or direct access from real path
                // for now just get bitmap data from ImageView
                params.put("photo_id", new DataPart(ktpuri.getLastPathSegment(), AppHelper.getFileDataFromDrawable(Layanan.this.getContext(), picktp), "image/jpeg"));
                params.put("photo_tax", new DataPart(pajakuri.getLastPathSegment(), AppHelper.getFileDataFromDrawable(Layanan.this.getContext(), picpajak), "image/jpeg"));
                if (lainnyauri != null) {
                    params.put("photos", new DataPart(lainnyauri.getLastPathSegment(), AppHelper.getFileDataFromDrawable(Layanan.this.getContext(), piclain), "image/jpeg"));
                }
                //params.put("photo")
                return params;
            }

        };

        VolleySingleton.getInstance(Layanan.this.getContext()).addToRequestQueue(multipartRequest);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    launchCamera();
                } else {
                    //code for deny
                }
                break;
        }
    }


    private void photoBuilder() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Layanan.this.getContext());
        builder.setPositiveButton("Ambil Gambar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                userChoosenTask = "Ambil Foto";
                result = Utility.checkPermission(Layanan.this.getContext());
                if (result) {
                    launchCamera();
                }
            }
        });
        builder.setNegativeButton("Pilih dari Galery", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                userChoosenTask = "Pilih dari Galeri";
                result = Utility.checkPermission(Layanan.this.getContext());
                if (result) {
                    pickdariGalery();
                }
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void launchCamera() {

        // Create intent
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Choose file storage location
        File file = new File(Environment.getExternalStorageDirectory(), UUID.randomUUID().toString() + ".jpg");
        if (status.equals("ktp")) {
            filesktp = file;
//            ktpFileUri = Uri.fromFile(file);
            ktpFileUri = FileProvider.getUriForFile(Layanan.this.getContext(), Layanan.this.getActivity().getApplicationContext().getPackageName() + ".provider", file);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, ktpFileUri);
            namafile = ktpFileUri.getLastPathSegment();
        } else if (status.equals("pajak")) {
            filespajak = file;
//            pajakFileUri = Uri.fromFile(file);
            pajakFileUri = FileProvider.getUriForFile(Layanan.this.getContext(), Layanan.this.getActivity().getApplicationContext().getPackageName() + ".provider", file);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, pajakFileUri);
            namafile = pajakFileUri.getLastPathSegment();
        } else if (status.equals("lainnya")) {
            fileslainnya = file;
//            lainFileUri = Uri.fromFile(file);
            lainFileUri = FileProvider.getUriForFile(Layanan.this.getContext(), Layanan.this.getActivity().getApplicationContext().getPackageName() + ".provider", file);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, lainFileUri);
            namafile = lainFileUri.getLastPathSegment();
        }
        // Launch intent
        startActivityForResult(takePictureIntent, 0);
    }

    private void pickdariGalery() {
        Intent i = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                if (status.equals("ktp")) {
                    txtKtp.setText(namafile);
                    picktp = Drawable.createFromPath(ktpFileUri.getPath());
                    Picasso.with(Layanan.this.getContext()).load(filesktp).fit().into(ktp);
                } else if (status.equals("pajak")) {
                    txtPajak.setText(namafile);
                    picpajak = Drawable.createFromPath(pajakFileUri.getPath());
                    Picasso.with(Layanan.this.getContext()).load(filespajak).fit().into(pajak);
                } else if (status.equals("lainnya")) {
                    txtLainnya.setText(namafile);
                    piclain = Drawable.createFromPath(lainFileUri.getPath());
                    Picasso.with(Layanan.this.getContext()).load(fileslainnya).fit().into(lainnya);
                }
            } else {
                Toast.makeText(Layanan.this.getContext(), "Photo gagal diambil, tolong ulangi lagi !", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = Layanan.this.getContext().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String filePath = cursor.getString(columnIndex);
                cursor.close();
                File file = new File(filePath);
                if (status.equals("ktp")) {
                    filesktp = file;
                    ktpFileUri = Uri.fromFile(file);
                    txtKtp.setText(ktpFileUri.getLastPathSegment());
                    picktp = Drawable.createFromPath(ktpFileUri.getPath());
                    Picasso.with(Layanan.this.getContext()).load(filesktp).fit().into(ktp);
                } else if (status.equals("pajak")) {
                    filespajak = file;
                    pajakFileUri = Uri.fromFile(file);
                    txtPajak.setText(pajakFileUri.getLastPathSegment());
                    picpajak = Drawable.createFromPath(pajakFileUri.getPath());
                    Picasso.with(Layanan.this.getContext()).load(filespajak).fit().into(pajak);
                } else if (status.equals("lainnya")) {
                    fileslainnya = file;
                    lainFileUri = Uri.fromFile(file);
                    piclain = Drawable.createFromPath(lainFileUri.getPath());
                    txtLainnya.setText(lainFileUri.getLastPathSegment());
                    Picasso.with(Layanan.this.getContext()).load(fileslainnya).fit().into(lainnya);
                }
            } else {
                Toast.makeText(Layanan.this.getContext(), "Photo gagal diambil, tolong ulangi lagi !", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
