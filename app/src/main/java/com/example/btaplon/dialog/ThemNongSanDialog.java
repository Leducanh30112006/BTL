package com.example.btaplon.dialog;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.*;
import com.example.btaplon.model.LoaiNongSan;
import com.example.btaplon.model.NongSan;
import com.example.btaplon.model.NongSanRepository;
import java.util.ArrayList;
import java.util.Calendar;

public class ThemNongSanDialog {
    private Context context;
    private NongSanRepository repository;
    private Runnable onSuccessListener;

    public ThemNongSanDialog(Context context, NongSanRepository repository, Runnable onSuccessListener) {
        this.context = context;
        this.repository = repository;
        this.onSuccessListener = onSuccessListener;
    }

    public void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("THÊM NÔNG SẢN MỚI");

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 30, 50, 30);

        EditText edtTen = new EditText(context);
        edtTen.setHint("Tên nông sản");
        layout.addView(edtTen);

        EditText edtNgay = new EditText(context);
        edtNgay.setHint("Ngày thu hoạch (YYYY-MM-DD)");
        edtNgay.setFocusable(false);
        edtNgay.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            new DatePickerDialog(context, (view, year, month, dayOfMonth) -> {
                String date = String.format("%d-%02d-%02d", year, month + 1, dayOfMonth);
                edtNgay.setText(date);
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
        });
        layout.addView(edtNgay);

        EditText edtGia = new EditText(context);
        edtGia.setHint("Giá");
        edtGia.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
        layout.addView(edtGia);

        Spinner spinnerLoai = new Spinner(context);
        ArrayList<LoaiNongSan> dsLoai = repository.getLoaiNongSan();
        ArrayAdapter<LoaiNongSan> spinnerAdapter = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_item, dsLoai);
        spinnerLoai.setAdapter(spinnerAdapter);
        layout.addView(spinnerLoai);

        builder.setView(layout);

        builder.setPositiveButton("THÊM", (dialog, which) -> {
            String ten = edtTen.getText().toString().trim();
            String ngay = edtNgay.getText().toString().trim();
            String giaStr = edtGia.getText().toString().trim();

            if (ten.isEmpty() || ngay.isEmpty() || giaStr.isEmpty()) {
                Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            int gia = Integer.parseInt(giaStr);
            LoaiNongSan selectedLoai = (LoaiNongSan) spinnerLoai.getSelectedItem();

            NongSan ns = new NongSan(0, ten, selectedLoai.getMaLoai(), ngay, gia);

            if (repository.themNongSan(ns)) {
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