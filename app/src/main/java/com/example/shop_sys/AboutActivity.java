package com.example.shop_sys;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import io.paperdb.Paper;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_about);

        ImageView imageView = findViewById(R.id.imageView);
        TextView titleTextView = findViewById(R.id.titleText);
        TextView descriptionTextView = findViewById(R.id.descriptionText);

        String id = getIntent().getStringExtra("id");
        Clothing cloth = Paper.book().read(id);

        String imageUrl = cloth.getPicture();
        Glide.with(this)
                .load(imageUrl)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .error("https://via.placeholder.com/600x400")
                .into(imageView);

        String title = cloth.getName();
        String description = cloth.getDescription();
        titleTextView.setText(title);
        descriptionTextView.setText(description);
    }
}