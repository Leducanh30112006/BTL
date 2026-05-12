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

public class SuaXoaNongSanDialog {
    private Context context;
    private NongSanRepository repository;
    private NongSan nongSan;
    private Runnable onSuccessListener;

    public SuaXoaNongSanDialog(Context context, NongSanRepository repository,
                               NongSan nongSan, Runnable onSuccessListener) {
        this.context = context;
        this.repository = repository;
        this.nongSan = nongSan;
        this.onSuccessListener = onSuccessListener;
    }

    public void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("SỬA / XÓA NÔNG SẢN");

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 30, 50, 30);

        EditText edtTen = new EditText(context);
        edtTen.setText(nongSan.getTen());
        edtTen.setHint("Tên nông sản");
        layout.addView(edtTen);

        EditText edtNgay = new EditText(context);
        edtNgay.setText(nongSan.getNgayThuHoach());
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
        edtGia.setText(String.valueOf(nongSan.getGia()));
        edtGia.setHint("Giá");
        edtGia.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
        layout.addView(edtGia);

        Spinner spinnerLoai = new Spinner(context);
        ArrayList<LoaiNongSan> dsLoai = repository.getLoaiNongSan();
        ArrayAdapter<LoaiNongSan> spinnerAdapter = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_item, dsLoai);
        spinnerLoai.setAdapter(spinnerAdapter);

        for (int i = 0; i < dsLoai.size(); i++) {
            if (dsLoai.get(i).getMaLoai() == nongSan.getMaLoai()) {
                spinnerLoai.setSelection(i);
                break;
            }
        }
        layout.addView(spinnerLoai);

        builder.setView(layout);

        builder.setPositiveButton("SỬA", (dialog, which) -> {
            String ten = edtTen.getText().toString().trim();
            String ngay = edtNgay.getText().toString().trim();
            String giaStr = edtGia.getText().toString().trim();

            if (ten.isEmpty() || ngay.isEmpty() || giaStr.isEmpty()) {
                Toast.makeText(context, "Vui lòng nhập đầy đủ", Toast.LENGTH_SHORT).show();
                return;
            }

            int gia = Integer.parseInt(giaStr);
            LoaiNongSan selectedLoai = (LoaiNongSan) spinnerLoai.getSelectedItem();

            nongSan.setTen(ten);
            nongSan.setNgayThuHoach(ngay);
            nongSan.setGia(gia);
            nongSan.setMaLoai(selectedLoai.getMaLoai());

            if (repository.suaNongSan(nongSan)) {
                Toast.makeText(context, "Sửa thành công!", Toast.LENGTH_SHORT).show();
                onSuccessListener.run();
            } else {
                Toast.makeText(context, "Sửa thất bại!", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNeutralButton("XÓA", (dialog, which) -> {
            new AlertDialog.Builder(context)
                    .setTitle("Xác nhận xóa")
                    .setMessage("Bạn có chắc muốn xóa " + nongSan.getTen() + "?")
                    .setPositiveButton("XÓA", (d, w) -> {
                        if (repository.xoaNongSan(nongSan.getMa())) {
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