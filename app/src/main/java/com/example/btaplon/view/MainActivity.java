package com.example.btaplon.view;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.btaplon.R;
import com.example.btaplon.adapter.NongSanAdapter;
import com.example.btaplon.dialog.ThemNongSanDialog;
import com.example.btaplon.dialog.SuaXoaNongSanDialog;
import com.example.btaplon.model.NongSan;
import com.example.btaplon.model.NongSanRepository;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NongSanRepository repository;
    private ListView listView;
    private NongSanAdapter adapter;
    private String loaiHienTai = "all";
    private List<NongSan> currentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        repository = new NongSanRepository(this);
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

        findViewById(R.id.btnRecent).setOnClickListener(v -> {
            loaiHienTai = "recent";
            capNhatDanhSach();
        });

        findViewById(R.id.btnRau).setOnClickListener(v -> {
            loaiHienTai = "rau";
            capNhatDanhSach();
        });

        findViewById(R.id.btnCu).setOnClickListener(v -> {
            loaiHienTai = "cu";
            capNhatDanhSach();
        });

        findViewById(R.id.btnTraiCay).setOnClickListener(v -> {
            loaiHienTai = "traicay";
            capNhatDanhSach();
        });

        findViewById(R.id.btnThem).setOnClickListener(v -> {
            new ThemNongSanDialog(this, repository, this::capNhatDanhSach).show();
        });
    }

    private void setupListViewLongClick() {
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            NongSan selected = currentList.get(position);
            new SuaXoaNongSanDialog(this, repository, selected, this::capNhatDanhSach).show();
            return true;
        });
    }

    private void capNhatDanhSach() {
        switch (loaiHienTai) {
            case "all":
                currentList = repository.getAllNongSan();
                break;
            case "recent":
                currentList = repository.getThuHoachGanDay();
                break;
            case "rau":
                currentList = repository.getNongSanTheoLoai(1);
                break;
            case "cu":
                currentList = repository.getNongSanTheoLoai(2);
                break;
            case "traicay":
                currentList = repository.getNongSanTheoLoai(3);
                break;
        }

        adapter = new NongSanAdapter(this, currentList);
        listView.setAdapter(adapter);
        
        if (currentList.isEmpty()) {
            Toast.makeText(this, "Không có dữ liệu", Toast.LENGTH_SHORT).show();
        }
    }
}