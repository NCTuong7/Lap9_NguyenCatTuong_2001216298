package com.example.lap9_nguyencattuong_2001216298.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lap9_nguyencattuong_2001216298.Controller.DatabaseHandler;
import com.example.lap9_nguyencattuong_2001216298.Model.Khoa;
import com.example.lap9_nguyencattuong_2001216298.Model.Product;
import com.example.lap9_nguyencattuong_2001216298.R;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Bai1 extends AppCompatActivity {
EditText edtMaSanPham, edtTenSanPham, edtSoLuong;
Button btnHienThi, btnThem, btnXoa, btnSua;
ListView lvSanPham;

ArrayAdapter<String> adapter;
ArrayList <Product> lsSanPham;


DatabaseHandler databaseHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai1);
        addControl();

      databaseHandler = new DatabaseHandler(Bai1.this);

      lsSanPham = databaseHandler.getAllProducts();
      adapter = new ArrayAdapter<>(Bai1.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,toArrStringLsSanPham());
      lvSanPham.setAdapter(adapter);

        addEvent();
    }

    void addControl()
    {
        edtMaSanPham = (EditText) findViewById(R.id.edtMaSanPham);
        edtTenSanPham = (EditText) findViewById(R.id.edtTenSanPham);
        edtSoLuong = (EditText) findViewById(R.id.edtSoLuong);

        btnHienThi = (Button) findViewById(R.id.btnHienThi);
        btnThem = (Button) findViewById(R.id.btnThem);
        btnXoa = (Button) findViewById(R.id.btnXoa);
        btnSua = (Button) findViewById(R.id.btnSua);

        lvSanPham = (ListView) findViewById(R.id.lvSanPham);
    }

    void addEvent()
    {
        btnHienThi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lsSanPham.clear();
                databaseHandler = new DatabaseHandler(Bai1.this);
                lsSanPham = databaseHandler.getAllProducts();
                adapter = new ArrayAdapter<>(Bai1.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,toArrStringLsSanPham());
                lvSanPham.setAdapter(adapter);
            }
        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Product product = new Product(Integer.parseInt(edtMaSanPham.getText().toString()),
                        edtTenSanPham.getText().toString(),Integer.parseInt(edtSoLuong.getText().toString()));
                databaseHandler.addProducts(product);
                databaseHandler.getAllProducts();
                Toast.makeText(Bai1.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
            }
        });

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                databaseHandler.deleteProduct(Integer.parseInt(edtMaSanPham.getText().toString()));
                Toast.makeText(Bai1.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
            }
        });

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(edtMaSanPham.getText().toString() == null)
            {
                return;
            }
            else
            {
                Product newProduct = new Product(Integer.parseInt(edtMaSanPham.getText().toString()),
                        edtTenSanPham.getText().toString(),Integer.parseInt(edtSoLuong.getText().toString()));
                databaseHandler.updateProduct(newProduct);
                databaseHandler.getAllProducts();
            }
                Toast.makeText(Bai1.this, "Sửa thành công", Toast.LENGTH_SHORT).show();
            }
        });

        lvSanPham.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product product = lsSanPham.get(position);
                edtMaSanPham.setText(String.valueOf(product.getId()));
                edtTenSanPham.setText(product.getName());
                edtSoLuong.setText(String.valueOf(product.getQuantity()));
            }
        });

    }

    ArrayList<String> toArrStringLsSanPham()
    {
        ArrayList<String> kq = new ArrayList<>();
        for (Product p : lsSanPham)
        {
            kq.add(String.valueOf(p.getId()) + " " + p.getName() + " " + p.getQuantity() +"\n");
        }
        return kq;
    }

    private Product findProduct (int maSanPham, ArrayList<Product> lsSanPham)
    {
        for (Product product : lsSanPham)
        {
            if (product.getId() == maSanPham)
            {
                return product;
            }
        }
        return null;
    }

}
