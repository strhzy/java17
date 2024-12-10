package com.example.shop_sys;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import io.paperdb.Paper;

public class AdminActivity extends AppCompatActivity {
    private EditText nameText, descriptionText, pictureText;
    private Button addBtn, delBtn, updateBtn;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private String selectedClotheName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin);
        Paper.init(AdminActivity.this);

        descriptionText = findViewById(R.id.description);
        nameText = findViewById(R.id.name);
        pictureText = findViewById(R.id.picture);
        addBtn = findViewById(R.id.addButton);
        delBtn = findViewById(R.id.deleteButton);
        updateBtn = findViewById(R.id.updateButton);
        listView = findViewById(R.id.listView);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getClothesNames());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedClotheName = adapter.getItem(position);
                Clothing cloth = Paper.book().read(selectedClotheName, null);
                if (cloth != null) {
                    nameText.setText(cloth.getName());
                    descriptionText.setText(cloth.getDescription());
                    pictureText.setText(cloth.getPicture());
                }
            }
        });

        addBtn.setOnClickListener(v -> {
            String name = nameText.getText().toString();
            String description = descriptionText.getText().toString();
            String url = pictureText.getText().toString();
            if (!name.isEmpty() && !description.isEmpty() && !url.isEmpty()) {
                Clothing book = new Clothing(name, description, url);
                Paper.book().write(name, book);
                updateClothList();
                clearInputs();
            }
        });

        updateBtn.setOnClickListener(v -> {
            if (selectedClotheName.isEmpty()) {
                Toast.makeText(AdminActivity.this, "Выберите книгу", Toast.LENGTH_SHORT).show();
            }
            String name = nameText.getText().toString();
            String description = descriptionText.getText().toString();
            String picture = pictureText.getText().toString();
            if (!name.isEmpty() && !description.isEmpty() && !picture.isEmpty()) {
                Clothing updateBook = new Clothing(name, description, picture);
                Paper.book().write(selectedClotheName, updateBook);
                updateClothList();
                clearInputs();
            }
        });

        delBtn.setOnClickListener(v -> {
            if (selectedClotheName.isEmpty()) {
                Toast.makeText(AdminActivity.this, "Выберите книгу", Toast.LENGTH_SHORT).show();
            }

            Paper.book().delete(selectedClotheName);
            updateClothList();
            clearInputs();
        });
    }

    private ArrayList<String> getClothesNames() {

        return new ArrayList<>(Paper.book().getAllKeys());
    }

    private void updateClothList() {
        adapter.clear();
        adapter.addAll(getClothesNames());
        adapter.notifyDataSetChanged();
    }

    private void clearInputs() {
        nameText.setText("");
        descriptionText.setText("");
        pictureText.setText("");
        selectedClotheName = null;
    }
}