// Khai báo package
package com.example.btaplon.connection;

// Import thư viện
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// Kế thừa SQLiteOpenHelper để quản lý Database
public class DatabaseHelper extends SQLiteOpenHelper {

    // Khai báo tên file database
    private static final String DATABASE_NAME = "quanlynongsan.db";
    // Khai báo phiên bản database
    private static final int DATABASE_VERSION = 1;

    // Các hằng số cho Bảng LOAI (Loại Nông Sản)
    public static final String TABLE_LOAI = "loai";
    public static final String COL_MA_LOAI = "maLoai";
    public static final String COL_TEN_LOAI = "tenLoai";

    // Các hằng số cho Bảng NONGSAN (Nông Sản)
    public static final String TABLE_NONGSAN = "nongsan";
    public static final String COL_MA = "ma";
    public static final String COL_TEN = "ten";
    public static final String COL_NGAY_THU_HOACH = "ngayThuHoach";
    public static final String COL_GIA = "gia";

    // Constructor để khởi tạo helper
    public DatabaseHelper(Context context) {
        // Gọi constructor lớp cha truyền vào context, tên DB, null (cho cursor factory) và version
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Hàm này chỉ chạy MỘT LẦN DUY NHẤT khi database được tạo lần đầu tiên
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Câu lệnh SQL tạo bảng LOAI với maLoai là khóa chính tự tăng
        String createLoaiTable = "CREATE TABLE " + TABLE_LOAI + " (" +
                COL_MA_LOAI + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_TEN_LOAI + " TEXT NOT NULL)";
        // Thực thi câu lệnh tạo bảng LOAI
        db.execSQL(createLoaiTable);

        // Chèn dữ liệu mẫu các loại nông sản vào bảng LOAI
        db.execSQL("INSERT INTO " + TABLE_LOAI + " (" + COL_TEN_LOAI + ") VALUES ('Rau')");
        db.execSQL("INSERT INTO " + TABLE_LOAI + " (" + COL_TEN_LOAI + ") VALUES ('Củ')");
        db.execSQL("INSERT INTO " + TABLE_LOAI + " (" + COL_TEN_LOAI + ") VALUES ('Trái cây')");

        // Câu lệnh SQL tạo bảng NONGSAN, maLoai là khóa ngoại trỏ tới bảng LOAI
        String createNongSanTable = "CREATE TABLE " + TABLE_NONGSAN + " (" +
                COL_MA + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_TEN + " TEXT NOT NULL, " +
                COL_MA_LOAI + " INTEGER, " +
                COL_NGAY_THU_HOACH + " TEXT, " +
                COL_GIA + " INTEGER, " +
                "FOREIGN KEY(" + COL_MA_LOAI + ") REFERENCES " + TABLE_LOAI + "(" + COL_MA_LOAI + "))";
        // Thực thi câu lệnh tạo bảng NONGSAN
        db.execSQL(createNongSanTable);

        // Chèn dữ liệu mẫu vào bảng NONGSAN (phục vụ việc test tính năng lọc 7 ngày)
        db.execSQL("INSERT INTO " + TABLE_NONGSAN + " (ten, maLoai, ngayThuHoach, gia) VALUES ('Cải bẹ xanh', 1, '2023-10-25', 15000)");
        db.execSQL("INSERT INTO " + TABLE_NONGSAN + " (ten, maLoai, ngayThuHoach, gia) VALUES ('Cà rốt', 2, '2023-10-24', 25000)");
        db.execSQL("INSERT INTO " + TABLE_NONGSAN + " (ten, maLoai, ngayThuHoach, gia) VALUES ('Táo Ninh Thuận', 3, '2023-10-23', 45000)");
    }

    // Hàm này chạy khi DATABASE_VERSION tăng lên (database được nâng cấp)
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xóa bảng NONGSAN nếu tồn tại
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NONGSAN);
        // Xóa bảng LOAI nếu tồn tại
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOAI);
        // Gọi lại hàm onCreate để tạo lại database mới hoàn toàn
        onCreate(db);
    }
}
