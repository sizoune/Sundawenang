package com.example.pattimura.sundawenang.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.pattimura.sundawenang.Model.AspirasiModel;
import com.example.pattimura.sundawenang.R;

import java.util.ArrayList;

/**
 * Created by wildan on 18/03/17.
 */

public class AdapterAspirasi extends BaseAdapter {
    private Context context;
    private ArrayList<AspirasiModel> isiAspirasi;

    public AdapterAspirasi(Context context, ArrayList<AspirasiModel> isiAspirasi) {
        this.context = context;
        this.isiAspirasi = isiAspirasi;
    }

    @Override
    public int getCount() {
        return isiAspirasi.size();
    }

    @Override
    public AspirasiModel getItem(int position) {
        return isiAspirasi.get(getCount() - position - 1);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.list_row_aspirasi, parent, false);

        TextView isi = (TextView) v.findViewById(R.id.textAspirasi);
        TextView nama = (TextView) v.findViewById(R.id.textNamaAspirasi);
        isi.setText("\"" + getItem(position).getIsi() + "\"");
        nama.setText(getItem(position).getNama());

        return v;
    }
}
