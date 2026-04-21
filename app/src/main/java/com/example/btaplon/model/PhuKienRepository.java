package com.example.btaplon.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import com.example.btaplon.connection.DatabaseHelper;

public class PhuKienRepository {
    private DatabaseHelper dbHelper;

    public PhuKienRepository(Context context) {
        this.dbHelper = new DatabaseHelper(context);
    }

    public ArrayList<PhuKien> getAllPhuKien() {
        ArrayList<PhuKien> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM phukien ORDER BY ma DESC", null);

        while (cursor.moveToNext()) {
            list.add(new PhuKien(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getString(3),
                    cursor.getInt(4)
            ));
        }
        cursor.close();
        db.close();
        return list;
    }

    public ArrayList<PhuKien> getPhuKienTheoLoai(int maLoai) {
        ArrayList<PhuKien> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM phukien WHERE maLoai = " + maLoai, null);

        while (cursor.moveToNext()) {
            list.add(new PhuKien(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getString(3),
                    cursor.getInt(4)
            ));
        }
        cursor.close();
        db.close();
        return list;
    }

    public ArrayList<PhuKien> timMacBookDuoi500k() {
        ArrayList<PhuKien> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM phukien WHERE tuongThichHang LIKE '%MacBook%' AND gia < 500000",
                null
        );

        while (cursor.moveToNext()) {
            list.add(new PhuKien(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getString(3),
                    cursor.getInt(4)
            ));
        }
        cursor.close();
        db.close();
        return list;
    }

    public ArrayList<LoaiPhuKien> getLoaiPhuKien() {
        ArrayList<LoaiPhuKien> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            Cursor cursor = db.rawQuery("SELECT * FROM loai", null);

            while (cursor.moveToNext()) {
                list.add(new LoaiPhuKien(cursor.getInt(0), cursor.getString(1)));
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
            // Nếu chưa có bảng, trả về list rỗng
        } finally {
            db.close();
        }

        return list;
    }

    public boolean themPhuKien(PhuKien phuKien) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = String.format(
                "INSERT INTO phukien (ten, maLoai, tuongThichHang, gia) VALUES ('%s', %d, '%s', %d)",
                phuKien.getTen(), phuKien.getMaLoai(), phuKien.getTuongThichHang(), phuKien.getGia()
        );
        try {
            db.execSQL(sql);
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            db.close();
        }
    }

    public boolean suaPhuKien(PhuKien phuKien) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = String.format(
                "UPDATE phukien SET ten='%s', maLoai=%d, tuongThichHang='%s', gia=%d WHERE ma=%d",
                phuKien.getTen(), phuKien.getMaLoai(), phuKien.getTuongThichHang(),
                phuKien.getGia(), phuKien.getMa()
        );
        try {
            db.execSQL(sql);
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            db.close();
        }
    }

    public boolean xoaPhuKien(int ma) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            db.execSQL("DELETE FROM phukien WHERE ma = " + ma);
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            db.close();
        }
    }

    public PhuKien getPhuKienById(int ma) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM phukien WHERE ma = " + ma, null);
        PhuKien phuKien = null;

        if (cursor.moveToFirst()) {
            phuKien = new PhuKien(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getString(3),
                    cursor.getInt(4)
            );
        }
        cursor.close();
        db.close();
        return phuKien;
    }
}