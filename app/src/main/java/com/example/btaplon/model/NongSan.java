package com.example.btaplon.model;

public class NongSan {
    private int ma;
    private String ten;
    private int maLoai;
    private String ngayThuHoach;
    private int gia;

    public NongSan(int ma, String ten, int maLoai, String ngayThuHoach, int gia) {
        this.ma = ma;
        this.ten = ten;
        this.maLoai = maLoai;
        this.ngayThuHoach = ngayThuHoach;
        this.gia = gia;
    }

    public int getMa() { return ma; }
    public void setMa(int ma) { this.ma = ma; }
    public String getTen() { return ten; }
    public void setTen(String ten) { this.ten = ten; }
    public int getMaLoai() { return maLoai; }
    public void setMaLoai(int maLoai) { this.maLoai = maLoai; }
    public String getNgayThuHoach() { return ngayThuHoach; }
    public void setNgayThuHoach(String ngayThuHoach) { this.ngayThuHoach = ngayThuHoach; }
    public int getGia() { return gia; }
    public void setGia(int gia) { this.gia = gia; }

    @Override
    public String toString() {
        return ma + " | " + ten + " - " + ngayThuHoach + " - " + gia + "đ";
    }
}