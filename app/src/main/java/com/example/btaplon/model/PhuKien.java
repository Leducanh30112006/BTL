package com.example.btaplon.model;

public class PhuKien {
    private int ma;
    private String ten;
    private int maLoai;
    private String tuongThichHang;
    private int gia;

    public PhuKien(int ma, String ten, int maLoai, String tuongThichHang, int gia) {
        this.ma = ma;
        this.ten = ten;
        this.maLoai = maLoai;
        this.tuongThichHang = tuongThichHang;
        this.gia = gia;
    }

    public int getMa() { return ma; }
    public void setMa(int ma) { this.ma = ma; }
    public String getTen() { return ten; }
    public void setTen(String ten) { this.ten = ten; }
    public int getMaLoai() { return maLoai; }
    public void setMaLoai(int maLoai) { this.maLoai = maLoai; }
    public String getTuongThichHang() { return tuongThichHang; }
    public void setTuongThichHang(String tuongThichHang) { this.tuongThichHang = tuongThichHang; }
    public int getGia() { return gia; }
    public void setGia(int gia) { this.gia = gia; }

    @Override
    public String toString() {
        return ma + " | " + ten + " - " + tuongThichHang + " - " + gia + "đ";
    }
}