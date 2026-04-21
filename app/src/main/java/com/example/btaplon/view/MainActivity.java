package com.example.btaplon.view;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.btaplon.R;
import com.example.btaplon.adapter.PhuKienAdapter;
import com.example.btaplon.dialog.ThemPhuKienDialog;
import com.example.btaplon.dialog.SuaXoaPhuKienDialog;
import com.example.btaplon.model.PhuKien;
import com.example.btaplon.model.PhuKienRepository;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private PhuKienRepository repository;
    private ListView listView;
    private PhuKienAdapter adapter;
    private String loaiHienTai = "all";
    private List<PhuKien> currentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        repository = new PhuKienRepository(this);
        listView = findViewById(R.id.listView);

        setupButtons();
        setupListViewLongClick();
        capNhatDanhSach();
    }

    private void setupButtons() {
        findViewById(R.id.btnAll).setOnClickListener(v -> {
            loaiHienTai = "all";
            capNhatDanhSach();
        });

        findViewById(R.id.btnMac).setOnClickListener(v -> {
            loaiHienTai = "macbook";
            capNhatDanhSach();
        });

        findViewById(R.id.btnChuot).setOnClickListener(v -> {
            loaiHienTai = "chuot";
            capNhatDanhSach();
        });

        findViewById(R.id.btnBanPhim).setOnClickListener(v -> {
            loaiHienTai = "banphim";
            capNhatDanhSach();
        });

        findViewById(R.id.btnOpLung).setOnClickListener(v -> {
            loaiHienTai = "oplung";
            capNhatDanhSach();
        });

        findViewById(R.id.btnThem).setOnClickListener(v -> {
            new ThemPhuKienDialog(this, repository, this::capNhatDanhSach).show();
        });
    }

    private void setupListViewLongClick() {
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            PhuKien selected = currentList.get(position);
            new SuaXoaPhuKienDialog(this, repository, selected, this::capNhatDanhSach).show();
            return true;
        });
    }

    private void capNhatDanhSach() {
        switch (loaiHienTai) {
            case "all":
                currentList = repository.getAllPhuKien();
                break;
            case "macbook":
                currentList = repository.timMacBookDuoi500k();
                break;
            case "chuot":
                currentList = repository.getPhuKienTheoLoai(1);
                break;
            case "banphim":
                currentList = repository.getPhuKienTheoLoai(2);
                break;
            case "oplung":
                currentList = repository.getPhuKienTheoLoai(3);
                break;
        }

        adapter = new PhuKienAdapter(this, currentList);
        listView.setAdapter(adapter);
    }
}