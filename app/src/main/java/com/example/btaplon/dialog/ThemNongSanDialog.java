// Khai báo package
package com.example.btaplon.dialog;

// Import các thư viện
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.*;
import com.example.btaplon.model.LoaiNongSan;
import com.example.btaplon.model.NongSan;
import com.example.btaplon.model.NongSanRepository;
import java.util.ArrayList;
import java.util.Calendar;

// Lớp tạo Dialog Thêm Nông Sản mới
public class ThemNongSanDialog {
    private Context context; // Ngữ cảnh
    private NongSanRepository repository; // Database
    private Runnable onSuccessListener; // Callback refresh list

    // Constructor
    public ThemNongSanDialog(Context context, NongSanRepository repository, Runnable onSuccessListener) {
        this.context = context;
        this.repository = repository;
        this.onSuccessListener = onSuccessListener;
    }

    // Hàm hiển thị Dialog
    public void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("THÊM NÔNG SẢN MỚI"); // Tiêu đề

        // Tạo khung chứa theo chiều dọc
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 30, 50, 30);

        // Khởi tạo ô nhập tên (trống)
        EditText edtTen = new EditText(context);
        edtTen.setHint("Tên nông sản");
        layout.addView(edtTen);

        // Khởi tạo ô chọn ngày
        EditText edtNgay = new EditText(context);
        edtNgay.setHint("Ngày thu hoạch (YYYY-MM-DD)");
        edtNgay.setFocusable(false); // Chặn gõ phím
        edtNgay.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            // Lịch popup
            new DatePickerDialog(context, (view, year, month, dayOfMonth) -> {
                String date = String.format("%d-%02d-%02d", year, month + 1, dayOfMonth);
                edtNgay.setText(date);
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
        });
        layout.addView(edtNgay);

        // Khởi tạo ô nhập giá (chỉ cho nhập số)
        EditText edtGia = new EditText(context);
        edtGia.setHint("Giá");
        edtGia.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
        layout.addView(edtGia);

        // Khởi tạo Spinner danh mục
        Spinner spinnerLoai = new Spinner(context);
        ArrayList<LoaiNongSan> dsLoai = repository.getLoaiNongSan(); // Lấy list danh mục
        ArrayAdapter<LoaiNongSan> spinnerAdapter = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_item, dsLoai);
        spinnerLoai.setAdapter(spinnerAdapter);
        layout.addView(spinnerLoai);

        // Gắn layout vào dialog
        builder.setView(layout);

        // Xử lý khi nhấn nút THÊM
        builder.setPositiveButton("THÊM", (dialog, which) -> {
            String ten = edtTen.getText().toString().trim();
            String ngay = edtNgay.getText().toString().trim();
            String giaStr = edtGia.getText().toString().trim();

            // Validate dữ liệu rỗng
            if (ten.isEmpty() || ngay.isEmpty() || giaStr.isEmpty()) {
                Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            int gia = Integer.parseInt(giaStr);
            // Ép kiểu Object từ Spinner về class LoaiNongSan
            LoaiNongSan selectedLoai = (LoaiNongSan) spinnerLoai.getSelectedItem();

            // Khởi tạo đối tượng Nông Sản mới (Mã truyền 0 vì DB cài AUTOINCREMENT tự tăng)
            NongSan ns = new NongSan(0, ten, selectedLoai.getMaLoai(), ngay, gia);

            // Gọi hàm thêm vào DB
            if (repository.themNongSan(ns)) {
                Toast.makeText(context, "Thêm thành công!", Toast.LENGTH_SHORT).show();
                onSuccessListener.run(); // Cập nhật lại ListView bên ngoài
            } else {
                Toast.makeText(context, "Thêm thất bại!", Toast.LENGTH_SHORT).show();
            }
        });

        // Nút HỦY tắt dialog
        builder.setNegativeButton("HỦY", null);
        builder.show();
    }
}
