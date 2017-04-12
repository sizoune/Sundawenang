package com.example.pattimura.sundawenang.Fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pattimura.sundawenang.R;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class Pengaduan extends Fragment implements View.OnClickListener {
    private Uri pajakFileUri = null;
    private Uri ktpFileUri = null;
    private String status = "";
    private ImageView ktp, pajak;
    private String namafile;
    private Button submit;
    private TextView txtKtp, txtPajak;
    private MaterialEditText isiPengaduan;


    public Pengaduan() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_pengaduan, container, false);

        isiPengaduan = (MaterialEditText) v.findViewById(R.id.txtisiPengaduan);
        txtKtp = (TextView) v.findViewById(R.id.txtKTPpengaduan);
        txtPajak = (TextView) v.findViewById(R.id.txtPajakpengaduan);
        ktp = (ImageView) v.findViewById(R.id.imageViewKTPPengaduan);
        pajak = (ImageView) v.findViewById(R.id.imageViewPajakPengaduan);
        submit = (Button) v.findViewById(R.id.buttonPengaduan);

        Picasso.with(this.getContext()).load(R.drawable.buttonuploadfotobiru).fit().into(ktp);
        Picasso.with(this.getContext()).load(R.drawable.buttonuploadfotoijo).fit().into(pajak);

        ktp.setOnClickListener(this);
        pajak.setOnClickListener(this);
        submit.setOnClickListener(this);

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
        } else if (v == submit) {
            if (!isiPengaduan.getText().toString().equals("") && ktpFileUri != null && pajakFileUri != null) {
                KirimData(isiPengaduan.getText().toString());
                Toast.makeText(Pengaduan.this.getContext(), "Pengaduan berhasil ditambahkan !", Toast.LENGTH_SHORT).show();
                clearData();
            } else {
                Toast.makeText(Pengaduan.this.getContext(), "Tolong lengkapi, isi pengaduan atau kelengkapan foto anda !", Toast.LENGTH_SHORT).show();
            }
        }
    }

    void clearData() {
        isiPengaduan.setText("");
        ktpFileUri = null;
        pajakFileUri = null;
        Picasso.with(this.getContext()).load(R.drawable.buttonuploadfotobiru).fit().into(ktp);
        Picasso.with(this.getContext()).load(R.drawable.buttonuploadfotoijo).fit().into(pajak);
        txtKtp.setText("Upload Foto KTP");
        txtPajak.setText("Upload Foto Pajak");
    }

    void KirimData(final String isi) {
        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://94.177.203.179/api/report",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //tempat response di dapatkan
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                        error.printStackTrace();
                        Toast.makeText(Pengaduan.this.getContext(), "erroring: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                try {
                    //Adding parameters to request
                    params.put("title", "judul report");
                    params.put("report", isi);
                    //returning parameter
                    return params;
                } catch (Exception e) {
                    Toast.makeText(Pengaduan.this.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    return params;
                }
            }
        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this.getContext());
        requestQueue.add(stringRequest);
    }

    private void photoBuilder() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Pengaduan.this.getContext());
        builder.setPositiveButton("Ambil Gambar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                launchCamera();
            }
        });
        builder.setNegativeButton("Pilih dari Galery", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                pickdariGalery();
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
            ktpFileUri = Uri.fromFile(file);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, ktpFileUri);
            namafile = ktpFileUri.getLastPathSegment();
        } else if (status.equals("pajak")) {
            pajakFileUri = Uri.fromFile(file);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, pajakFileUri);
            namafile = pajakFileUri.getLastPathSegment();
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
                    Picasso.with(Pengaduan.this.getContext()).load(R.drawable.done).fit().into(ktp);
                } else if (status.equals("pajak")) {
                    txtPajak.setText(namafile);
                    Picasso.with(Pengaduan.this.getContext()).load(R.drawable.done).fit().into(pajak);
                }
            } else {
                Toast.makeText(Pengaduan.this.getContext(), "Photo gagal diambil, tolong ulangi lagi !", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = Pengaduan.this.getContext().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String filePath = cursor.getString(columnIndex);
                cursor.close();
                File file = new File(filePath);
                if (status.equals("ktp")) {
                    ktpFileUri = Uri.fromFile(file);
                    txtKtp.setText(ktpFileUri.getLastPathSegment());
                    Picasso.with(Pengaduan.this.getContext()).load(R.drawable.done).fit().into(ktp);
                } else if (status.equals("pajak")) {
                    pajakFileUri = Uri.fromFile(file);
                    txtPajak.setText(pajakFileUri.getLastPathSegment());
                    Picasso.with(Pengaduan.this.getContext()).load(R.drawable.done).fit().into(pajak);
                }
            } else {
                Toast.makeText(Pengaduan.this.getContext(), "Photo gagal diambil, tolong ulangi lagi !", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
