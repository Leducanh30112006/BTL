package com.example.btaplon.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.*;
import com.example.btaplon.R;
import com.example.btaplon.model.LoaiPhuKien;
import com.example.btaplon.model.PhuKien;
import com.example.btaplon.model.PhuKienRepository;
import java.util.ArrayList;

public class ThemPhuKienDialog {
    private Context context;
    private PhuKienRepository repository;
    private Runnable onSuccessListener;

    public ThemPhuKienDialog(Context context, PhuKienRepository repository, Runnable onSuccessListener) {
        this.context = context;
        this.repository = repository;
        this.onSuccessListener = onSuccessListener;
    }

    public void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("THÊM PHỤ KIỆN MỚI");

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 30, 50, 30);

        EditText edtTen = new EditText(context);
        edtTen.setHint("Tên phụ kiện");
        layout.addView(edtTen);

        EditText edtHang = new EditText(context);
        edtHang.setHint("Hãng tương thích (VD: MacBook, Dell)");
        layout.addView(edtHang);

        EditText edtGia = new EditText(context);
        edtGia.setHint("Giá (VD: 500000)");
        edtGia.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
        layout.addView(edtGia);

        Spinner spinnerLoai = new Spinner(context);
        ArrayList<LoaiPhuKien> dsLoai = repository.getLoaiPhuKien();
        ArrayAdapter<LoaiPhuKien> spinnerAdapter = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_item, dsLoai);
        spinnerLoai.setAdapter(spinnerAdapter);
        layout.addView(spinnerLoai);

        builder.setView(layout);

        builder.setPositiveButton("THÊM", (dialog, which) -> {
            String ten = edtTen.getText().toString().trim();
            String hang = edtHang.getText().toString().trim();
            String giaStr = edtGia.getText().toString().trim();

            if (ten.isEmpty() || hang.isEmpty() || giaStr.isEmpty()) {
                Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            int gia = Integer.parseInt(giaStr);
            LoaiPhuKien selectedLoai = (LoaiPhuKien) spinnerLoai.getSelectedItem();

            PhuKien phuKien = new PhuKien(0, ten, selectedLoai.getMaLoai(), hang, gia);

            if (repository.themPhuKien(phuKien)) {
                Toast.makeText(context, "Thêm thành công!", Toast.LENGTH_SHORT).show();
                onSuccessListener.run();
            } else {
                Toast.makeText(context, "Thêm thất bại!", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("HỦY", null);
        builder.show();
    }
}