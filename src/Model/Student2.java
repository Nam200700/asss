/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author ACER
 */
public class Student2 {
    public String masinhvien;
    public String tensinhvien;
    public String mamon;
    public  boolean gioitinh;
    public int tuoi;
    public String malop;


    public Student2(String masinhvien, String tensinhvien, String mamon,boolean gioitinh, int tuoi,  String malop) {
        this.masinhvien = masinhvien;
        this.tensinhvien = tensinhvien;
        this.mamon = mamon;
        this.gioitinh = gioitinh;
        this.tuoi = tuoi;
        this.malop = malop;
    }
    public Student2(){
        
    }

    public String getMasinhvien() {
        return masinhvien;
    }

    public String getTensinhvien() {
        return tensinhvien;
    }


    public String getMamon() {
        return mamon;
    }

    public boolean getGioitinh() {
        return gioitinh;
    }

    public int getTuoi() {
        return tuoi;
    }
    public String getMalop() {
        return malop;
    }

    public void setMasinhvien(String masinhvien) {
        this.masinhvien = masinhvien;
    }

    public void setTensinhvien(String tensinhvien) {
        this.tensinhvien = tensinhvien;
    }

    public void setMamon(String mamon) {
        this.mamon = mamon;
    }

    public void setGioitinh(boolean gioitinh) {
        this.gioitinh = gioitinh;
    }

    public void setTuoi(int tuoi) {
        this.tuoi = tuoi;
    }
     public void setMalop(String malop) {
        this.malop = malop;
    }

}
