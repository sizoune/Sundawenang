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

import com.example.pattimura.sundawenang.R;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.picasso.Picasso;

import java.io.File;
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

            } else {
                Toast.makeText(Pengaduan.this.getContext(), "Tolong lengkapi, isi pengaduan atau kelengkapan foto anda !", Toast.LENGTH_SHORT).show();
            }
        }
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
            txtKtp.setText(ktpFileUri.getLastPathSegment());
            Picasso.with(Pengaduan.this.getContext()).load(R.drawable.done).fit().into(ktp);
        } else if (status.equals("pajak")) {
            pajakFileUri = Uri.fromFile(file);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, pajakFileUri);
            txtPajak.setText(pajakFileUri.getLastPathSegment());
            Picasso.with(Pengaduan.this.getContext()).load(R.drawable.done).fit().into(pajak);
        } else {

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
