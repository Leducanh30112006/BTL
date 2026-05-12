// Khai báo package chứa class
package com.example.btaplon.adapter;

// Import các thư viện cần thiết của Android và Java
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.btaplon.R;
import com.example.btaplon.model.NongSan;
import java.util.List;

// Lớp NongSanAdapter kế thừa từ ArrayAdapter với kiểu dữ liệu là đối tượng NongSan
public class NongSanAdapter extends ArrayAdapter<NongSan> {
    // Biến lưu trữ Context (ngữ cảnh của Activity gọi nó)
    private Context context;
    // Danh sách các đối tượng Nông Sản cần hiển thị
    private List<NongSan> nongSanList;

    // Constructor để khởi tạo Adapter với Context và danh sách dữ liệu
    public NongSanAdapter(Context context, List<NongSan> nongSanList) {
        // Gọi constructor của lớp cha (ArrayAdapter), truyền 0 vì ta dùng custom layout
        super(context, 0, nongSanList);
        // Gán context được truyền vào cho biến nội bộ
        this.context = context;
        // Gán danh sách truyền vào cho biến nội bộ
        this.nongSanList = nongSanList;
    }

    // Hàm này được gọi cho mỗi item trong ListView để tạo giao diện hiển thị
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Nếu convertView là null (tức là chưa có View nào được tái sử dụng)
        if (convertView == null) {
            // Nạp (inflate) giao diện từ file XML item_nong_san.xml để tạo thành một View
            convertView = LayoutInflater.from(context).inflate(R.layout.item_nong_san, parent, false);
        }

        // Lấy đối tượng Nông Sản tại vị trí hiện tại (position) trong danh sách
        NongSan ns = nongSanList.get(position);

        // Ánh xạ các TextView từ giao diện XML thông qua ID
        TextView tvTen = convertView.findViewById(R.id.tvTen);
        TextView tvHang = convertView.findViewById(R.id.tvHang);
        TextView tvGia = convertView.findViewById(R.id.tvGia);
        TextView tvIcon = convertView.findViewById(R.id.tvIcon);

        // Gán tên nông sản vào TextView tên
        tvTen.setText(ns.getTen());
        // Gán ngày thu hoạch với chuỗi text đi kèm
        tvHang.setText("Ngày thu hoạch: " + ns.getNgayThuHoach());

        // Format giá tiền theo chuẩn (vd: 15,000) và thêm chữ "₫" ở cuối
        String giaFormatted = String.format("%,d", ns.getGia()) + " ₫";
        // Gán giá đã format vào TextView giá
        tvGia.setText(giaFormatted);

        // Kiểm tra mã loại nông sản để hiển thị Icon/Emoji tương ứng
        switch (ns.getMaLoai()) {
            case 1: // Nếu là 1 (Rau)
                tvIcon.setText("🥬"); // Set icon cây rau
                break;
            case 2: // Nếu là 2 (Củ)
                tvIcon.setText("🥕"); // Set icon củ cà rốt
                break;
            case 3: // Nếu là 3 (Trái cây)
                tvIcon.setText("🍎"); // Set icon quả táo
                break;
            default: // Nếu mã khác các giá trị trên
                tvIcon.setText("🌿"); // Set icon chiếc lá mặc định
                break;
        }

        // Trả về View đã được gán đầy đủ dữ liệu để ListView hiển thị
        return convertView;
    }
}
