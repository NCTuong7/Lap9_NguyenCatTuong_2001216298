package com.example.lap9_nguyencattuong_2001216298.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.lap9_nguyencattuong_2001216298.Model.SinhVien;

import java.util.ArrayList;

public class SinhVienHandler extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "qlsinhvien.db";

    private static int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "SinhVien";
    private static final String STUDENT_ID = "maSinhvien";
    private static final String STUDENT_NAME = "tenSinhVien";


    public static String path = "/data/data/com.example.lap9_nguyencattuong_2001216298/db/qlsinhvien.db";

    public SinhVienHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        String query = "CREATE TABLE " + TABLE_NAME + " ( " + STUDENT_ID + "TEXT PRIMARY KEY AUTOINCREMENT, " + STUDENT_NAME + " TEXT) ";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void initData() {
        SQLiteDatabase db;
        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        onCreate(db);

        String row1 = "INSERT INTO " + TABLE_NAME + " (" + STUDENT_ID + ", " + STUDENT_NAME + ") VALUES ('1', 'Nguyễn Văn A' ) ";
        db.execSQL(row1);

        String row2 = "INSERT INTO " + TABLE_NAME + " (" + STUDENT_ID + ", " + STUDENT_NAME + ") VALUES ('2', 'Nguyễn Văn B' ) ";
        db.execSQL(row2);

        String row3 = "INSERT INTO " + TABLE_NAME + " (" + STUDENT_ID + ", " + STUDENT_NAME + ") VALUES ('3', 'Nguyễn Văn A' ) ";
        db.execSQL(row3);

        db.close();
    }


    public ArrayList<SinhVien> loadSinhVien() {
        SQLiteDatabase db;
        ArrayList<SinhVien> lsSinhVien = new ArrayList<>();
        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        Cursor cursor = db.rawQuery("SELECT * FROM SinhVien", null);

        do {

            SinhVien sinhVien = new SinhVien();
            sinhVien.setStudentId(cursor.getString(0));
            sinhVien.setStudentName(cursor.getString(1));
            lsSinhVien.add(sinhVien);

        } while (cursor.moveToNext());
        cursor.close();
        return lsSinhVien;
    }

    public void insertNewSinhVien(SinhVien sinhVien) {
        SQLiteDatabase db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
        ContentValues values = new ContentValues();
        values.put(STUDENT_ID, sinhVien.getStudentId());
        values.put(STUDENT_NAME, sinhVien.getStudentName());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void deleteSinhVien(String studentId)
    {
        SQLiteDatabase db = SQLiteDatabase.openDatabase(path,null, SQLiteDatabase.OPEN_READWRITE);
        db.delete(TABLE_NAME, STUDENT_ID  + "=?", new String[] {studentId});
        db.close();
    }


    public void updateSinhVien(SinhVien newSinhVien, SinhVien oldSinhVien)
    {
        SQLiteDatabase db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
        ContentValues values = new ContentValues();
        values.put(STUDENT_ID, newSinhVien.getStudentId());
        values.put(STUDENT_NAME, newSinhVien.getStudentName());
        db.update(TABLE_NAME, values, "maSinhVien=?",
                new String[]{oldSinhVien.getStudentId()});
        db.close();
    }
}
