package com.example.shop_sys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import io.paperdb.Paper;


public class MainActivity extends AppCompatActivity {

    private Button adminBtn;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private String selectedClothName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Paper.init(MainActivity.this);

        listView = findViewById(R.id.listView);
        adminBtn = findViewById(R.id.btn_admin);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getClothName());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedClothName = adapter.getItem(position);
                Clothing cloth = Paper.book().read(selectedClothName, null);
                if (cloth != null) {
                    Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                    intent.putExtra("id", selectedClothName);
                    startActivity(intent);
                }
            }
        });

        adminBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AdminActivity.class);
            startActivity(intent);
        });
    }

    private ArrayList<String> getClothName() {
        return new ArrayList<>(Paper.book().getAllKeys());
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.clear();
        adapter.addAll(getClothName());
        adapter.notifyDataSetChanged();
    }


}