package com.example.lap9_nguyencattuong_2001216298.View;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lap9_nguyencattuong_2001216298.Controller.KhoaHandler;
import com.example.lap9_nguyencattuong_2001216298.Model.Khoa;
import com.example.lap9_nguyencattuong_2001216298.R;

import java.util.ArrayList;

public class ViewKhoaActivity extends AppCompatActivity {

    EditText edtMaKhoa;
    EditText edtTenKhoa;
    Button btnAdd, btnUpdate;
    ListView lvKhoa;
    KhoaHandler khoaHandler;
    ArrayList<Khoa> lsKhoa = new ArrayList<>();
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_item_layout_khoa);

        addControl();
        khoaHandler  = new KhoaHandler(getApplicationContext(),"qlsv.db", null,1);
        khoaHandler.initData();
        lsKhoa = khoaHandler.loadKhoa();
        adapter = new ArrayAdapter<>(getApplicationContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,toArrStringLsKhoa());
        lvKhoa.setAdapter(adapter);
        addEvent();
    }

    void addControl()
    {
        edtMaKhoa = (EditText) findViewById(R.id.edtMaKhoa);
        edtTenKhoa = (EditText) findViewById(R.id.edtTenKhoa);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        lvKhoa = (ListView) findViewById(R.id.lvKhoa);
    }

    void addEvent()
    {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Khoa k = new Khoa((Integer.parseInt(edtMaKhoa.getText().toString())),
                        edtTenKhoa.getText().toString());
                khoaHandler.insertNewKhoa(k);
                reloadListView();
            }
        });

        lvKhoa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Khoa k = lsKhoa.get(position);
                edtMaKhoa.setText(String.valueOf(k.getMaKhoa()));
                edtMaKhoa.setEnabled(false);
                edtTenKhoa.setText(k.getTenKhoa());
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtMaKhoa.getText().toString() == null)
                {
                    return;
                }

                else
                {
                    Khoa newKhoa = new Khoa(Integer.parseInt(edtMaKhoa.getText().toString()),
                            edtTenKhoa.getText().toString());
                    Khoa oldKhoa = timKhoa(Integer.parseInt(edtMaKhoa.getText().toString()),lsKhoa);
                    khoaHandler.updateKhoa(oldKhoa,newKhoa);
                    reloadListView();
                }
            }
        });

        lvKhoa.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return false;
            }
        });
    }

    private void reloadListView()
    {
        khoaHandler = new KhoaHandler(getApplicationContext(), "qlsv.db", null, 1);
        lsKhoa = khoaHandler.loadKhoa();
        adapter = new ArrayAdapter<>(getApplicationContext(),
                androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item,toArrStringLsKhoa());
        lvKhoa.setAdapter(adapter);
    }

    private Khoa timKhoa (int maKhoa, ArrayList<Khoa> lsKhoa)
    {
        for(Khoa k : lsKhoa)
        {
            if (k.getMaKhoa()  == maKhoa)
                return k;
        }
        return null;
    }

    ArrayList<String> toArrStringLsKhoa()
    {
        ArrayList<String> kq = new ArrayList<>();
        for (Khoa k : lsKhoa)
        {
            kq.add(String.valueOf(k.getMaKhoa()) + " " + k.getTenKhoa() + "\n");
        }
        return kq;
    }

}
