package com.example.pattimura.sundawenang.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pattimura.sundawenang.Model.BeritaModel;
import com.example.pattimura.sundawenang.R;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;

/**
 * Created by wildan on 20/03/17.
 */

public class AdapterBerita extends BaseAdapter implements Filterable {
    private Context context;
    private ArrayList<BeritaModel> daftarberita;
    private ArrayList<BeritaModel> daftarberitakw;
    private Filter beritaFilter;

    public AdapterBerita(Context context, ArrayList<BeritaModel> daftarberita) {
        this.context = context;
        this.daftarberita = daftarberita;
        this.daftarberitakw = daftarberita;
    }

    @Override
    public int getCount() {
        return daftarberitakw.size();
    }

    @Override
    public BeritaModel getItem(int position) {
        return daftarberitakw.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.list_row_berita, parent, false);

        TextView judul = (TextView) v.findViewById(R.id.textViewNamaBerita);
        TextView tanggal = (TextView) v.findViewById(R.id.textViewTanggalberita);
        TextView kategori = (TextView) v.findViewById(R.id.textViewkategori);
        ImageView imagedepan = (ImageView) v.findViewById(R.id.imageViewBerita);

        Transformation transformation = new RoundedTransformationBuilder()
                .cornerRadiusDp(5)
                .oval(false)
                .build();

        if (!getItem(position).cekDaftarGambar()) {
            Picasso.with(v.getContext()).load(getItem(position).getGambarke(0).getUrl()).fit().transform(transformation).into(imagedepan);
        } else {
            Picasso.with(v.getContext()).load(R.drawable.imagedefault).fit().transform(transformation).into(imagedepan);
        }

        judul.setText(getItem(position).getJudul());
        tanggal.setText(getItem(position).getTanggal());
        kategori.setText(getItem(position).getKategori());

        return v;
    }

    public void resetData() {
        daftarberitakw = daftarberita;
    }

    @Override
    public Filter getFilter() {
        if (beritaFilter == null) {
            beritaFilter = new BeritaFilter();
        }
        return beritaFilter;
    }

    private class BeritaFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint == null || constraint.length() == 0) {
                // No filter implemented we return all the list
                results.values = daftarberita;
                results.count = daftarberita.size();
            } else {
                ArrayList<BeritaModel> nBerita = new ArrayList<>();
                for (BeritaModel bm : daftarberitakw) {
                    if (bm.getKategori().toUpperCase().startsWith(constraint.toString().toUpperCase())) {
                        nBerita.add(bm);
                    }
                }
                results.values = nBerita;
                results.count = nBerita.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.count == 0) {
                notifyDataSetInvalidated();
            } else {
                daftarberitakw = (ArrayList<BeritaModel>) results.values;
                notifyDataSetChanged();
            }
        }
    }
}
