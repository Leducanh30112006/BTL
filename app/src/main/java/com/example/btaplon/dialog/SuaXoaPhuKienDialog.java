package com.example.btaplon.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.*;
import com.example.btaplon.model.LoaiPhuKien;
import com.example.btaplon.model.PhuKien;
import com.example.btaplon.model.PhuKienRepository;
import java.util.ArrayList;

public class SuaXoaPhuKienDialog {
    private Context context;
    private PhuKienRepository repository;
    private PhuKien phuKien;
    private Runnable onSuccessListener;

    public SuaXoaPhuKienDialog(Context context, PhuKienRepository repository,
                               PhuKien phuKien, Runnable onSuccessListener) {
        this.context = context;
        this.repository = repository;
        this.phuKien = phuKien;
        this.onSuccessListener = onSuccessListener;
    }

    public void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("SỬA / XÓA PHỤ KIỆN");

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 30, 50, 30);

        EditText edtTen = new EditText(context);
        edtTen.setText(phuKien.getTen());
        edtTen.setHint("Tên phụ kiện");
        layout.addView(edtTen);

        EditText edtHang = new EditText(context);
        edtHang.setText(phuKien.getTuongThichHang());
        edtHang.setHint("Hãng tương thích");
        layout.addView(edtHang);

        EditText edtGia = new EditText(context);
        edtGia.setText(String.valueOf(phuKien.getGia()));
        edtGia.setHint("Giá");
        edtGia.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
        layout.addView(edtGia);

        Spinner spinnerLoai = new Spinner(context);
        ArrayList<LoaiPhuKien> dsLoai = repository.getLoaiPhuKien();
        ArrayAdapter<LoaiPhuKien> spinnerAdapter = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_item, dsLoai);
        spinnerLoai.setAdapter(spinnerAdapter);

        for (int i = 0; i < dsLoai.size(); i++) {
            if (dsLoai.get(i).getMaLoai() == phuKien.getMaLoai()) {
                spinnerLoai.setSelection(i);
                break;
            }
        }
        layout.addView(spinnerLoai);

        builder.setView(layout);

        builder.setPositiveButton("SỬA", (dialog, which) -> {
            String ten = edtTen.getText().toString().trim();
            String hang = edtHang.getText().toString().trim();
            String giaStr = edtGia.getText().toString().trim();

            if (ten.isEmpty() || hang.isEmpty() || giaStr.isEmpty()) {
                Toast.makeText(context, "Vui lòng nhập đầy đủ", Toast.LENGTH_SHORT).show();
                return;
            }

            int gia = Integer.parseInt(giaStr);
            LoaiPhuKien selectedLoai = (LoaiPhuKien) spinnerLoai.getSelectedItem();

            phuKien.setTen(ten);
            phuKien.setTuongThichHang(hang);
            phuKien.setGia(gia);
            phuKien.setMaLoai(selectedLoai.getMaLoai());

            if (repository.suaPhuKien(phuKien)) {
                Toast.makeText(context, "Sửa thành công!", Toast.LENGTH_SHORT).show();
                onSuccessListener.run();
            } else {
                Toast.makeText(context, "Sửa thất bại!", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNeutralButton("XÓA", (dialog, which) -> {
            new AlertDialog.Builder(context)
                    .setTitle("Xác nhận xóa")
                    .setMessage("Bạn có chắc muốn xóa phụ kiện " + phuKien.getTen() + "?")
                    .setPositiveButton("XÓA", (d, w) -> {
                        if (repository.xoaPhuKien(phuKien.getMa())) {
                            Toast.makeText(context, "Xóa thành công!", Toast.LENGTH_SHORT).show();
                            onSuccessListener.run();
                        } else {
                            Toast.makeText(context, "Xóa thất bại!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("HỦY", null)
                    .show();
        });

        builder.setNegativeButton("HỦY", null);
        builder.show();
    }
}