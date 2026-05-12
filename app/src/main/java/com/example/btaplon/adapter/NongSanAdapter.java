package com.example.btaplon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.btaplon.R;
import com.example.btaplon.model.NongSan;
import java.util.List;

public class NongSanAdapter extends ArrayAdapter<NongSan> {
    private Context context;
    private List<NongSan> nongSanList;

    public NongSanAdapter(Context context, List<NongSan> nongSanList) {
        super(context, 0, nongSanList);
        this.context = context;
        this.nongSanList = nongSanList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_nong_san, parent, false);
        }

        NongSan ns = nongSanList.get(position);

        TextView tvTen = convertView.findViewById(R.id.tvTen);
        TextView tvHang = convertView.findViewById(R.id.tvHang);
        TextView tvGia = convertView.findViewById(R.id.tvGia);
        TextView tvIcon = convertView.findViewById(R.id.tvIcon);

        tvTen.setText(ns.getTen());
        tvHang.setText("Ngày thu hoạch: " + ns.getNgayThuHoach());

        String giaFormatted = String.format("%,d", ns.getGia()) + " ₫";
        tvGia.setText(giaFormatted);

        switch (ns.getMaLoai()) {
            case 1:
                tvIcon.setText("🥬");
                break;
            case 2:
                tvIcon.setText("🥕");
                break;
            case 3:
                tvIcon.setText("🍎");
                break;
            default:
                tvIcon.setText("🌿");
                break;
        }

        return convertView;
    }
}