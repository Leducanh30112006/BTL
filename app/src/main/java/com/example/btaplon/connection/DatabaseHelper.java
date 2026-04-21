package com.example.btaplon.connection;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "quanlyphukien.db";
    private static final int DATABASE_VERSION = 2;

    private static final String TABLE_LOAI = "loai";
    private static final String COL_MA_LOAI = "maLoai";
    private static final String COL_TEN_LOAI = "tenLoai";

    private static final String TABLE_PHUKIEN = "phukien";
    private static final String COL_MA = "ma";
    private static final String COL_TEN = "ten";
    private static final String COL_TUONG_THICH_HANG = "tuongThichHang";
    private static final String COL_GIA = "gia";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createLoaiTable = "CREATE TABLE " + TABLE_LOAI + " (" +
                COL_MA_LOAI + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_TEN_LOAI + " TEXT NOT NULL)";
        db.execSQL(createLoaiTable);

        db.execSQL("INSERT INTO " + TABLE_LOAI + " (" + COL_TEN_LOAI + ") VALUES ('Chuột')");
        db.execSQL("INSERT INTO " + TABLE_LOAI + " (" + COL_TEN_LOAI + ") VALUES ('Bàn phím')");
        db.execSQL("INSERT INTO " + TABLE_LOAI + " (" + COL_TEN_LOAI + ") VALUES ('Ốp lưng')");
        db.execSQL("INSERT INTO " + TABLE_LOAI + " (" + COL_TEN_LOAI + ") VALUES ('MacBook')");

        String createPhuKienTable = "CREATE TABLE " + TABLE_PHUKIEN + " (" +
                COL_MA + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_TEN + " TEXT NOT NULL, " +
                COL_MA_LOAI + " INTEGER, " +
                COL_TUONG_THICH_HANG + " TEXT, " +
                COL_GIA + " INTEGER, " +
                "FOREIGN KEY(" + COL_MA_LOAI + ") REFERENCES " + TABLE_LOAI + "(" + COL_MA_LOAI + "))";
        db.execSQL(createPhuKienTable);

        db.execSQL("INSERT INTO " + TABLE_PHUKIEN + " (ten, maLoai, tuongThichHang, gia) VALUES ('Chuột không dây Logitech', 1, 'MacBook', 450000)");
        db.execSQL("INSERT INTO " + TABLE_PHUKIEN + " (ten, maLoai, tuongThichHang, gia) VALUES ('Ốp lưng MacBook Air', 3, 'MacBook', 250000)");
        db.execSQL("INSERT INTO " + TABLE_PHUKIEN + " (ten, maLoai, tuongThichHang, gia) VALUES ('Bàn phím cơ', 2, 'Dell', 1200000)");
        db.execSQL("INSERT INTO " + TABLE_PHUKIEN + " (ten, maLoai, tuongThichHang, gia) VALUES ('Chuột Bluetooth Magic Mouse', 1, 'MacBook', 480000)");
        db.execSQL("INSERT INTO " + TABLE_PHUKIEN + " (ten, maLoai, tuongThichHang, gia) VALUES ('Ốp lưng MacBook Pro', 3, 'MacBook', 350000)");
        db.execSQL("INSERT INTO " + TABLE_PHUKIEN + " (ten, maLoai, tuongThichHang, gia) VALUES ('Bàn phím cơ', 2, 'Dell', 360000)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PHUKIEN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOAI);

        onCreate(db);
    }

}