package com.example.pattimura.sundawenang.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pattimura.sundawenang.Model.LowonganModel;
import com.example.pattimura.sundawenang.R;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;

/**
 * Created by wildan on 22/03/17.
 */

public class AdpterLowongan extends BaseAdapter {
    private Context context;
    private ArrayList<LowonganModel> daftarlowongan;

    public AdpterLowongan(Context context, ArrayList<LowonganModel> daftarlowongan) {
        this.context = context;
        this.daftarlowongan = daftarlowongan;
    }

    @Override
    public int getCount() {
        return daftarlowongan.size();
    }

    @Override
    public LowonganModel getItem(int position) {
        return daftarlowongan.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.list_row_lowongan, parent, false);

        ImageView cover = (ImageView) v.findViewById(R.id.imageViewLowonganFoto);
        TextView judul = (TextView) v.findViewById(R.id.txtJudullowongan);
        TextView tanggal = (TextView) v.findViewById(R.id.textViewLowonganTanggal);

        Transformation transformation = new RoundedTransformationBuilder()
                .cornerRadiusDp(5)
                .oval(false)
                .build();

        if (!getItem(position).cekDaftarGambar()) {
            Picasso.with(v.getContext()).load(getItem(position).getGambarke(0).getUrl()).fit().transform(transformation).into(cover);
        } else {
            Picasso.with(v.getContext()).load(R.drawable.imagedefault).fit().transform(transformation).into(cover);
        }

        judul.setText(getItem(position).getJudul());
        tanggal.setText(getItem(position).getTanggal());

        return v;
    }
}
