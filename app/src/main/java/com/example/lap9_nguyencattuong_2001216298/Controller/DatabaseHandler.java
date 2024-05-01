package com.example.lap9_nguyencattuong_2001216298.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.lap9_nguyencattuong_2001216298.Model.Product;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {

private static final String DATABASE_NAME = "productsDB";
private static final int DATABASE_VERSION = 1;
private static final String TABLE_NAME = "products";

private static String KEY_ID = "id";
private static final String KEY_NAME = "name";
private static final String KEY_QUATITY = "quatity";



    public DatabaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String drop_products_table = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);
        db.execSQL(drop_products_table);
        String create_products_table = String.format("CREATE TABLE %s ( %s INTEGER PRIMARY KEY, " +
                " %s TEXT, %s INTEGER)",TABLE_NAME, KEY_ID,KEY_NAME,KEY_QUATITY);
        db.execSQL(create_products_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String drop_products_table = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);
        db.execSQL(drop_products_table);
        onCreate(db);

    }

    // Thêm một dòng dữ liệu

    public void addProducts (Product product)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, product.getName());
        values.put(KEY_QUATITY,product.getQuantity());
        db.insert(TABLE_NAME,null,values);
        db.close();
    }

    // Đọc một record trong bảng với id là tham số

    public Product getProduct (int productId)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,null,KEY_ID +"=?", new String[]
                {String.valueOf(productId)},null,null,null);
        if(cursor != null)
        {
            cursor.moveToFirst();
        }

        Product product = new Product(cursor.getInt(0),
                cursor.getString(1),cursor.getInt(2));
        return product;
    }

    // Đọc tất cả dữ liệu trong bảng

    public ArrayList<Product> getAllProducts()
    {
        ArrayList<Product> productArrayList = new ArrayList<>();
        String query = " SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false)
        {
            Product product = new Product(cursor.getInt(0), cursor.getString(1),cursor.getInt(2));
            productArrayList.add(product);
            cursor.moveToNext();

        }

        return productArrayList;
    }

    // Cập nhật dữ liệu bảng

    public  void updateProduct (Product product)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, product.getName());
        values.put(KEY_QUATITY,product.getQuantity());
        db.update(TABLE_NAME,values,KEY_ID = "?", new String[] {String.valueOf(product.getId())});
        db.close();
    }

    // Xóa một record

    public  void deleteProduct (int productId)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + "=?", new String[] {String.valueOf(productId)});
        db.close();
    }



}
