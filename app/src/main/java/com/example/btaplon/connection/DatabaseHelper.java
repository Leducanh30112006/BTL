package com.example.btaplon.connection;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "quanlynongsan.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_LOAI = "loai";
    public static final String COL_MA_LOAI = "maLoai";
    public static final String COL_TEN_LOAI = "tenLoai";

    public static final String TABLE_NONGSAN = "nongsan";
    public static final String COL_MA = "ma";
    public static final String COL_TEN = "ten";
    public static final String COL_NGAY_THU_HOACH = "ngayThuHoach";
    public static final String COL_GIA = "gia";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createLoaiTable = "CREATE TABLE " + TABLE_LOAI + " (" +
                COL_MA_LOAI + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_TEN_LOAI + " TEXT NOT NULL)";
        db.execSQL(createLoaiTable);

        db.execSQL("INSERT INTO " + TABLE_LOAI + " (" + COL_TEN_LOAI + ") VALUES ('Rau')");
        db.execSQL("INSERT INTO " + TABLE_LOAI + " (" + COL_TEN_LOAI + ") VALUES ('Củ')");
        db.execSQL("INSERT INTO " + TABLE_LOAI + " (" + COL_TEN_LOAI + ") VALUES ('Trái cây')");

        String createNongSanTable = "CREATE TABLE " + TABLE_NONGSAN + " (" +
                COL_MA + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_TEN + " TEXT NOT NULL, " +
                COL_MA_LOAI + " INTEGER, " +
                COL_NGAY_THU_HOACH + " TEXT, " +
                COL_GIA + " INTEGER, " +
                "FOREIGN KEY(" + COL_MA_LOAI + ") REFERENCES " + TABLE_LOAI + "(" + COL_MA_LOAI + "))";
        db.execSQL(createNongSanTable);

        // Sample data with recent dates for testing "7 days" query
        db.execSQL("INSERT INTO " + TABLE_NONGSAN + " (ten, maLoai, ngayThuHoach, gia) VALUES ('Cải bẹ xanh', 1, '2023-10-25', 15000)");
        db.execSQL("INSERT INTO " + TABLE_NONGSAN + " (ten, maLoai, ngayThuHoach, gia) VALUES ('Cà rốt', 2, '2023-10-24', 25000)");
        db.execSQL("INSERT INTO " + TABLE_NONGSAN + " (ten, maLoai, ngayThuHoach, gia) VALUES ('Táo Ninh Thuận', 3, '2023-10-23', 45000)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NONGSAN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOAI);
        onCreate(db);
    }
}