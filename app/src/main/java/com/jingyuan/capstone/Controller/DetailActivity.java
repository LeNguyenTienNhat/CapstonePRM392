package com.jingyuan.capstone.Controller;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.jingyuan.capstone.R;

public class DetailActivity extends AppCompatActivity {
    static int PERMISSION_CODE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (ContextCompat.checkSelfPermission(DetailActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(DetailActivity.this, new String[]{Manifest.permission.CALL_PHONE},PERMISSION_CODE);
        }

        Intent i = getIntent();
        String labelData = i.getStringExtra("label");
        String desData = i.getStringExtra("des");
        String storeData = i.getStringExtra("store");
        String phoneData = i.getStringExtra("phoneNumber");
        int thumbnailData = i.getIntExtra("thumbnailID",0);

        TextView label = findViewById(R.id.label);
        TextView des = findViewById(R.id.des);
        TextView store = findViewById(R.id.store);
        TextView phone = findViewById(R.id.phone);
        ImageView thumbnail = findViewById(R.id.thumbnail);
        ImageButton backBtn = findViewById(R.id.back);
        ImageButton callBtn = findViewById(R.id.call_btn);

        label.setText(labelData);
        des.setText(desData);
        store.setText(storeData);
        phone.setText(phoneData);
        thumbnail.setImageResource(thumbnailData);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailActivity.this.finish();
            }
        });

        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phoneData));
                startActivity(intent);
            }
        });
    }
}