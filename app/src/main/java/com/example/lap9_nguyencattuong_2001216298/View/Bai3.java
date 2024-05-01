package com.example.lap9_nguyencattuong_2001216298.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lap9_nguyencattuong_2001216298.Controller.SinhVienHandler;
import com.example.lap9_nguyencattuong_2001216298.Model.Product;
import com.example.lap9_nguyencattuong_2001216298.Model.SinhVien;
import com.example.lap9_nguyencattuong_2001216298.R;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Bai3 extends AppCompatActivity {

    EditText edtStudentId, edtStudentName;
    Button btnShowStudent, btnAddStudent, btnDeleteStudent, btnEditStudent;
    ListView lvStudent;
    ArrayList <SinhVien> lsStudent;

    ArrayAdapter<String> adapter;
    SinhVienHandler sinhVienHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai3);

        addControl();

        sinhVienHandler = new SinhVienHandler(getApplicationContext(),"qlsinhvien.db",null,1);
        sinhVienHandler.initData();
        lsStudent = sinhVienHandler.loadSinhVien();
        adapter = new ArrayAdapter<>(Bai3.this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item,toArrStringSinhVien());
        lvStudent.setAdapter(adapter);

        addEvent();

    }

    void addControl()
    {
        edtStudentId = (EditText) findViewById(R.id.edtStudentId);
        edtStudentName = (EditText) findViewById(R.id.edtStudentName);

        btnShowStudent = (Button) findViewById(R.id.btnShowStudent);
        btnShowStudent = (Button) findViewById(R.id.btnShowStudent);
        btnShowStudent = (Button) findViewById(R.id.btnShowStudent);
        btnShowStudent = (Button) findViewById(R.id.btnShowStudent);

        lvStudent = (ListView) findViewById(R.id.lvStudent);

    }

    void addEvent()
    {
btnShowStudent.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        lsStudent.clear();
        sinhVienHandler = new SinhVienHandler(getApplicationContext(),"qlsinhvien.db",null,1);
        sinhVienHandler.initData();
        lsStudent = sinhVienHandler.loadSinhVien();
        adapter = new ArrayAdapter<>(Bai3.this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item,toArrStringSinhVien());
        lvStudent.setAdapter(adapter);
    }
});

btnAddStudent.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
    SinhVien sinhVien = new SinhVien(edtStudentId.getText().toString(), edtStudentName.getText().toString());
    sinhVienHandler.insertNewSinhVien(sinhVien);
    sinhVienHandler.loadSinhVien();
    }
});

btnDeleteStudent.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        if (edtStudentId.getText().toString().isEmpty())
        {
            Toast.makeText(Bai3.this, "Bạn chưa nhập mã sinh viên cần xóa", Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
            sinhVienHandler.deleteSinhVien(edtStudentId.getText().toString());
            Toast.makeText(Bai3.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
        }
    }
});

lvStudent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SinhVien sinhVien = lsStudent.get(position);
        edtStudentId.setText(sinhVien.getStudentId());
        edtStudentName.setText(sinhVien.getStudentName());
        edtStudentId.setEnabled(false);
    }
});

btnEditStudent.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if(edtStudentId.getText().toString() == null)
        {
            return;
        }
        else
        {
            SinhVien newSinhVien = new SinhVien(edtStudentId.getText().toString(),edtStudentName.getText().toString());
            SinhVien oldSinhVien = findSinhVien(edtStudentId.getText().toString(),lsStudent);
            sinhVienHandler.updateSinhVien(newSinhVien,oldSinhVien);
            sinhVienHandler.loadSinhVien();
        }
        Toast.makeText(Bai3.this, "Sửa thành công", Toast.LENGTH_SHORT).show();
    }
});

    }

    ArrayList<String> toArrStringSinhVien()
    {
        ArrayList<String> listItem = new ArrayList<>();

        for (SinhVien s : lsStudent)
        {
            listItem.add(s.getStudentId() + " " + s.getStudentName() + "\n");
        }
        return listItem;
    }

    private SinhVien findSinhVien (String studentId, ArrayList<SinhVien> lsStudent)
    {
        for (SinhVien s : lsStudent)
        {
            if (s.getStudentId()  == studentId)
            {
                return s;
            }
        }
        return null;
    }

}