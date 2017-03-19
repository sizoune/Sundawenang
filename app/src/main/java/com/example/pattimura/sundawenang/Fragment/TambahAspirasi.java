package com.example.pattimura.sundawenang.Fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.pattimura.sundawenang.Model.AspirasiModel;
import com.example.pattimura.sundawenang.R;
import com.rengwuxian.materialedittext.MaterialEditText;

/**
 * A simple {@link Fragment} subclass.
 */
public class TambahAspirasi extends Fragment {
    private ProgressDialog mProgressDialog;

    public TambahAspirasi() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tambah_aspirasi, container, false);

        Button tambah = (Button) v.findViewById(R.id.buttonKirimAspirasi);
        final MaterialEditText isiAspirasi = (MaterialEditText) v.findViewById(R.id.txtisiAspirasi);
        final MaterialEditText nama = (MaterialEditText) v.findViewById(R.id.txtNamaAspirasi);
        final MaterialEditText rt = (MaterialEditText) v.findViewById(R.id.txtRTAspirasi);
        final MaterialEditText rw = (MaterialEditText) v.findViewById(R.id.txtRWAspirasi);


        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String isi = isiAspirasi.getText().toString();
                String name = nama.getText().toString();
                String nort = rt.getText().toString();
                String norw = rw.getText().toString();

                if (!(isi.equals("")) && !(name.equals("")) && !(nort.equals("")) && !(norw.equals(""))) {
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
                    Toast.makeText(TambahAspirasi.this.getContext(), "Tolong lengkapi !", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

        return v;
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

}
