package com.example.btaplon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.btaplon.R;
import com.example.btaplon.model.PhuKien;
import java.util.List;

public class PhuKienAdapter extends ArrayAdapter<PhuKien> {
    private Context context;
    private List<PhuKien> phuKienList;

    public PhuKienAdapter(Context context, List<PhuKien> phuKienList) {
        super(context, 0, phuKienList);
        this.context = context;
        this.phuKienList = phuKienList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_phukien, parent, false);
        }

        PhuKien phuKien = phuKienList.get(position);

        TextView tvTen = convertView.findViewById(R.id.tvTen);
        TextView tvHang = convertView.findViewById(R.id.tvHang);
        TextView tvGia = convertView.findViewById(R.id.tvGia);
        TextView tvIcon = convertView.findViewById(R.id.tvIcon);

        tvTen.setText(phuKien.getTen());

        tvHang.setText("Tương thích: " + phuKien.getTuongThichHang());

        String giaFormatted = String.format("%,d", phuKien.getGia()) + " ₫";
        tvGia.setText(giaFormatted);

        switch (phuKien.getMaLoai()) {
            case 1:
                tvIcon.setText("🖱️");
                break;
            case 2:
                tvIcon.setText("⌨️");
                break;
            case 3:
                tvIcon.setText("📱");
                break;
            default:
                tvIcon.setText("💻");
                break;
        }

        return convertView;
    }
}