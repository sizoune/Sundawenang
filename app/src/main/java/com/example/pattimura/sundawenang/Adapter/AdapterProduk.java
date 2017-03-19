package com.example.pattimura.sundawenang.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pattimura.sundawenang.Model.ProdukModel;
import com.example.pattimura.sundawenang.R;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;

/**
 * Created by wildan on 19/03/17.
 */

public class AdapterProduk extends BaseAdapter {
    private Context context;
    private ArrayList<ProdukModel> daftarproduk;

    public AdapterProduk(Context context, ArrayList<ProdukModel> daftarproduk) {
        this.context = context;
        this.daftarproduk = daftarproduk;
    }

    @Override
    public int getCount() {
        return daftarproduk.size();
    }

    @Override
    public ProdukModel getItem(int position) {
        return daftarproduk.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.list_row_produk, parent, false);

        TextView judul = (TextView) v.findViewById(R.id.textViewNamaItem);
        TextView tanggal = (TextView) v.findViewById(R.id.textViewTanggal);
        ImageView imagedepan = (ImageView) v.findViewById(R.id.imageViewNewsFoto);

        Transformation transformation = new RoundedTransformationBuilder()
                .cornerRadiusDp(5)
                .oval(false)
                .build();

        if (!getItem(position).cekDaftarGambar()) {
            Picasso.with(v.getContext()).load(getItem(position).getGambarke(0).getUrl()).fit().transform(transformation).into(imagedepan);
        } else {
            Picasso.with(v.getContext()).load(R.drawable.imagedefault).fit().transform(transformation).into(imagedepan);
        }
        judul.setText(getItem(position).getNama());
        tanggal.setText(getItem(position).getTanggal());

        return v;
    }
}
