package com.example.lap9_nguyencattuong_2001216298.Model;

public class SinhVien {
    String studentId;
    String studentName;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public SinhVien() {
    }

    public SinhVien(String studentId, String studentName) {
        this.studentId = studentId;
        this.studentName = studentName;
    }
}
