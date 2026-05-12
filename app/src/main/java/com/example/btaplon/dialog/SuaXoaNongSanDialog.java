// Khai báo package
package com.example.btaplon.dialog;

// Import thư viện
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.*;
import com.example.btaplon.model.LoaiNongSan;
import com.example.btaplon.model.NongSan;
import com.example.btaplon.model.NongSanRepository;
import java.util.ArrayList;
import java.util.Calendar;

// Lớp tạo Dialog Sửa/Xóa
public class SuaXoaNongSanDialog {
    private Context context; // Ngữ cảnh
    private NongSanRepository repository; // Đối tượng thao tác với Database
    private NongSan nongSan; // Đối tượng Nông sản đang cần sửa/xóa
    private Runnable onSuccessListener; // Callback thực thi khi Sửa/Xóa thành công (dùng để load lại ListView)

    // Constructor nhận vào các tham số cần thiết
    public SuaXoaNongSanDialog(Context context, NongSanRepository repository,
                               NongSan nongSan, Runnable onSuccessListener) {
        this.context = context;
        this.repository = repository;
        this.nongSan = nongSan;
        this.onSuccessListener = onSuccessListener;
    }

    // Hàm hiển thị Dialog
    public void show() {
        // Khởi tạo đối tượng Builder để xây dựng Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("SỬA / XÓA NÔNG SẢN"); // Đặt tiêu đề Dialog

        // Tạo một LinearLayout bằng code để chứa các thành phần giao diện
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL); // Sắp xếp theo chiều dọc
        layout.setPadding(50, 30, 50, 30); // Căn lề trong (padding)

        // Tạo ô nhập Tên
        EditText edtTen = new EditText(context);
        edtTen.setText(nongSan.getTen()); // Đổ dữ liệu tên cũ vào
        edtTen.setHint("Tên nông sản"); // Chữ gợi ý mờ
        layout.addView(edtTen); // Thêm ô nhập Tên vào layout

        // Tạo ô chọn Ngày
        EditText edtNgay = new EditText(context);
        edtNgay.setText(nongSan.getNgayThuHoach()); // Đổ ngày cũ vào
        edtNgay.setHint("Ngày thu hoạch (YYYY-MM-DD)");
        edtNgay.setFocusable(false); // Không cho phép gõ phím trực tiếp
        // Bắt sự kiện click vào ô ngày để hiện Calendar (Lịch)
        edtNgay.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance(); // Lấy ngày giờ hiện tại
            // Hiển thị Dialog chọn ngày
            new DatePickerDialog(context, (view, year, month, dayOfMonth) -> {
                // Format lại ngày được chọn theo chuẩn YYYY-MM-DD
                String date = String.format("%d-%02d-%02d", year, month + 1, dayOfMonth);
                edtNgay.setText(date); // Gán text vào ô nhập
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
        });
        layout.addView(edtNgay); // Thêm ô chọn Ngày vào layout

        // Tạo ô nhập Giá
        EditText edtGia = new EditText(context);
        edtGia.setText(String.valueOf(nongSan.getGia())); // Đổ giá cũ vào
        edtGia.setHint("Giá");
        edtGia.setInputType(android.text.InputType.TYPE_CLASS_NUMBER); // Ràng buộc chỉ cho nhập số
        layout.addView(edtGia); // Thêm ô nhập Giá vào layout

        // Tạo Spinner (Dropdown) để chọn Loại Nông Sản
        Spinner spinnerLoai = new Spinner(context);
        // Lấy danh sách Loại từ Database
        ArrayList<LoaiNongSan> dsLoai = repository.getLoaiNongSan();
        // Tạo adapter cho Spinner
        ArrayAdapter<LoaiNongSan> spinnerAdapter = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_item, dsLoai);
        spinnerLoai.setAdapter(spinnerAdapter);

        // Chạy vòng lặp để tìm và set lựa chọn mặc định của Spinner đúng với loại của Nông Sản hiện tại
        for (int i = 0; i < dsLoai.size(); i++) {
            if (dsLoai.get(i).getMaLoai() == nongSan.getMaLoai()) {
                spinnerLoai.setSelection(i);
                break;
            }
        }
        layout.addView(spinnerLoai); // Thêm Spinner vào layout

        // Đưa toàn bộ layout vừa tạo vào Dialog
        builder.setView(layout);

        // Nút "SỬA" (Nút tích cực - màu bên phải)
        builder.setPositiveButton("SỬA", (dialog, which) -> {
            // Lấy dữ liệu người dùng nhập
            String ten = edtTen.getText().toString().trim();
            String ngay = edtNgay.getText().toString().trim();
            String giaStr = edtGia.getText().toString().trim();

            // Kiểm tra rỗng
            if (ten.isEmpty() || ngay.isEmpty() || giaStr.isEmpty()) {
                Toast.makeText(context, "Vui lòng nhập đầy đủ", Toast.LENGTH_SHORT).show();
                return; // Dừng lại nếu thiếu dữ liệu
            }

            // Chuyển chuỗi giá thành số nguyên
            int gia = Integer.parseInt(giaStr);
            // Lấy đối tượng Loại Nông Sản đang được chọn trong Spinner
            LoaiNongSan selectedLoai = (LoaiNongSan) spinnerLoai.getSelectedItem();

            // Cập nhật các giá trị mới vào đối tượng Nông Sản hiện tại
            nongSan.setTen(ten);
            nongSan.setNgayThuHoach(ngay);
            nongSan.setGia(gia);
            nongSan.setMaLoai(selectedLoai.getMaLoai());

            // Gọi hàm Sửa trong Repository
            if (repository.suaNongSan(nongSan)) {
                Toast.makeText(context, "Sửa thành công!", Toast.LENGTH_SHORT).show();
                onSuccessListener.run(); // Gọi callback để refresh danh sách
            } else {
                Toast.makeText(context, "Sửa thất bại!", Toast.LENGTH_SHORT).show();
            }
        });

        // Nút "XÓA" (Nút trung lập)
        builder.setNeutralButton("XÓA", (dialog, which) -> {
            // Hiện thêm 1 Dialog nữa để yêu cầu xác nhận trước khi xóa
            new AlertDialog.Builder(context)
                    .setTitle("Xác nhận xóa")
                    .setMessage("Bạn có chắc muốn xóa " + nongSan.getTen() + "?")
                    .setPositiveButton("XÓA", (d, w) -> {
                        // Nếu bấm XÓA, gọi hàm xóa theo Mã từ Repository
                        if (repository.xoaNongSan(nongSan.getMa())) {
                            Toast.makeText(context, "Xóa thành công!", Toast.LENGTH_SHORT).show();
                            onSuccessListener.run(); // Refresh danh sách
                        } else {
                            Toast.makeText(context, "Xóa thất bại!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("HỦY", null) // Bấm hủy thì không làm gì cả
                    .show();
        });

        // Nút "HỦY" của Dialog Sửa/Xóa ban đầu (Không làm gì cả, tự đóng Dialog)
        builder.setNegativeButton("HỦY", null);
        
        // Hiển thị Dialog lên màn hình
        builder.show();
    }
}
