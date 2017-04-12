package com.example.pattimura.sundawenang.Fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pattimura.sundawenang.R;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.UUID;

import fr.ganfra.materialspinner.MaterialSpinner;

import static android.app.Activity.RESULT_OK;
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
    private String namafile;
    private ImageView ktp, pajak, lainnya;
    private Button submit;
    private TextView txtKtp, txtPajak, txtLainnya;
    private MaterialEditText nama, notel;

    public Layanan() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_layanan, container, false);

        ktp = (ImageView) v.findViewById(R.id.imageViewktplayanan);
        pajak = (ImageView) v.findViewById(R.id.imageViewpajaklayanan);
        lainnya = (ImageView) v.findViewById(R.id.imageViewlainlayanan);
        txtKtp = (TextView) v.findViewById(R.id.txtKTP);
        txtPajak = (TextView) v.findViewById(R.id.txtPajak);
        txtLainnya = (TextView) v.findViewById(R.id.txtLainnya);
        nama = (MaterialEditText) v.findViewById(R.id.txtNama);
        notel = (MaterialEditText) v.findViewById(R.id.txtTelp);
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
            if (!nama.getText().toString().equals("") && ktpFileUri != null && pajakFileUri != null && !notel.getText().toString().equals("") && spinner.getSelectedItemPosition() != 0) {

            } else {
                Toast.makeText(Layanan.this.getContext(), "Tolong lengkapi, Jenis layanan dan nama dan no telepon dan ktp dan pajak !", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void photoBuilder() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Layanan.this.getContext());
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
        } else if (status.equals("lainnya")) {
            lainFileUri = Uri.fromFile(file);
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
                    Picasso.with(Layanan.this.getContext()).load(R.drawable.done).fit().into(ktp);
                } else if (status.equals("pajak")) {
                    txtPajak.setText(namafile);
                    Picasso.with(Layanan.this.getContext()).load(R.drawable.done).fit().into(pajak);
                } else if (status.equals("lainnya")) {
                    txtLainnya.setText(namafile);
                    Picasso.with(Layanan.this.getContext()).load(R.drawable.done).fit().into(lainnya);
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
                    ktpFileUri = Uri.fromFile(file);
                    txtKtp.setText(ktpFileUri.getLastPathSegment());
                    Picasso.with(Layanan.this.getContext()).load(R.drawable.done).fit().into(ktp);
                } else if (status.equals("pajak")) {
                    pajakFileUri = Uri.fromFile(file);
                    txtPajak.setText(pajakFileUri.getLastPathSegment());
                    Picasso.with(Layanan.this.getContext()).load(R.drawable.done).fit().into(pajak);
                } else if (status.equals("lainnya")) {
                    lainFileUri = Uri.fromFile(file);
                    txtLainnya.setText(lainFileUri.getLastPathSegment());
                    Picasso.with(Layanan.this.getContext()).load(R.drawable.done).fit().into(lainnya);
                }
            } else {
                Toast.makeText(Layanan.this.getContext(), "Photo gagal diambil, tolong ulangi lagi !", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
