package com.example.btaplon.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import com.example.btaplon.connection.DatabaseHelper;

public class NongSanRepository {
    private DatabaseHelper dbHelper;

    public NongSanRepository(Context context) {
        this.dbHelper = new DatabaseHelper(context);
    }

    public ArrayList<NongSan> getAllNongSan() {
        ArrayList<NongSan> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NONGSAN + " ORDER BY " + DatabaseHelper.COL_MA + " DESC", null);

        while (cursor.moveToNext()) {
            list.add(new NongSan(
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

    public ArrayList<NongSan> getNongSanTheoLoai(int maLoai) {
        ArrayList<NongSan> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NONGSAN + " WHERE " + DatabaseHelper.COL_MA_LOAI + " = " + maLoai, null);

        while (cursor.moveToNext()) {
            list.add(new NongSan(
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

    public ArrayList<NongSan> getThuHoachGanDay() {
        ArrayList<NongSan> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // SQLite query for last 7 days. Assuming YYYY-MM-DD format.
        String sql = "SELECT * FROM " + DatabaseHelper.TABLE_NONGSAN + 
                     " WHERE " + DatabaseHelper.COL_NGAY_THU_HOACH + " >= date('now', '-7 days')";
        Cursor cursor = db.rawQuery(sql, null);

        while (cursor.moveToNext()) {
            list.add(new NongSan(
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

    public ArrayList<LoaiNongSan> getLoaiNongSan() {
        ArrayList<LoaiNongSan> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_LOAI, null);
            while (cursor.moveToNext()) {
                list.add(new LoaiNongSan(cursor.getInt(0), cursor.getString(1)));
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return list;
    }

    public boolean themNongSan(NongSan ns) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = String.format(
                "INSERT INTO %s (%s, %s, %s, %s) VALUES ('%s', %d, '%s', %d)",
                DatabaseHelper.TABLE_NONGSAN, DatabaseHelper.COL_TEN, DatabaseHelper.COL_MA_LOAI, 
                DatabaseHelper.COL_NGAY_THU_HOACH, DatabaseHelper.COL_GIA,
                ns.getTen(), ns.getMaLoai(), ns.getNgayThuHoach(), ns.getGia()
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

    public boolean suaNongSan(NongSan ns) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = String.format(
                "UPDATE %s SET %s='%s', %s=%d, %s='%s', %s=%d WHERE %s=%d",
                DatabaseHelper.TABLE_NONGSAN, 
                DatabaseHelper.COL_TEN, ns.getTen(),
                DatabaseHelper.COL_MA_LOAI, ns.getMaLoai(),
                DatabaseHelper.COL_NGAY_THU_HOACH, ns.getNgayThuHoach(),
                DatabaseHelper.COL_GIA, ns.getGia(),
                DatabaseHelper.COL_MA, ns.getMa()
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

    public boolean xoaNongSan(int ma) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            db.execSQL("DELETE FROM " + DatabaseHelper.TABLE_NONGSAN + " WHERE " + DatabaseHelper.COL_MA + " = " + ma);
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            db.close();
        }
    }
}