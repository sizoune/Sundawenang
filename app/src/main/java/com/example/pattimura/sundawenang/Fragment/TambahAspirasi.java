package com.example.pattimura.sundawenang.Fragment;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pattimura.sundawenang.Model.AspirasiModel;
import com.example.pattimura.sundawenang.R;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class TambahAspirasi extends Fragment implements View.OnClickListener {
    private ProgressDialog mProgressDialog;
    private MaterialEditText isiAspirasi, nama, rt, rw;
    private TextView txtKtp;
    private ImageView fotoktp;
    private String namafile;
    private Uri ktpFileUri = null;
    private Button submit;

    public TambahAspirasi() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tambah_aspirasi, container, false);

        fotoktp = (ImageView) v.findViewById(R.id.imageKTPAspirasi);
        isiAspirasi = (MaterialEditText) v.findViewById(R.id.txtisiAspirasi);
        nama = (MaterialEditText) v.findViewById(R.id.txtNamaAspirasi);
        rt = (MaterialEditText) v.findViewById(R.id.txtRTAspirasi);
        rw = (MaterialEditText) v.findViewById(R.id.txtRWAspirasi);
        txtKtp = (TextView) v.findViewById(R.id.textViewKTPTambahaspirasi);
        submit = (Button) v.findViewById(R.id.buttonKirimAspirasi);

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
                showProgressDialog();
                Bundle b = new Bundle();
                b.putString("isi", isi);
                b.putString("nama", name);
                Fragment f = new Aspirasi();
                f.setArguments(b);
                hideProgressDialog();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.mainframe, f);
                ft.commit();
            } else {
                Toast.makeText(TambahAspirasi.this.getContext(), "Tolong lengkapi !, isi aspirasi, nama, rt, rw, ktp", Toast.LENGTH_SHORT).show();
                return;
            }
        } else if (v == fotoktp) {
            photoBuilder();
        }
    }

    private void photoBuilder() {
        AlertDialog.Builder builder = new AlertDialog.Builder(TambahAspirasi.this.getContext());
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
        ktpFileUri = Uri.fromFile(file);
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
                Picasso.with(TambahAspirasi.this.getContext()).load(R.drawable.done).fit().into(fotoktp);
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
                ktpFileUri = Uri.fromFile(file);
                txtKtp.setText(ktpFileUri.getLastPathSegment());
                Picasso.with(TambahAspirasi.this.getContext()).load(R.drawable.done).fit().into(fotoktp);
            } else {
                Toast.makeText(TambahAspirasi.this.getContext(), "Photo gagal diambil, tolong ulangi lagi !", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
