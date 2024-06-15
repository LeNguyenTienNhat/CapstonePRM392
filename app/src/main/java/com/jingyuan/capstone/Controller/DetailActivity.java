package com.jingyuan.capstone.Controller;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jingyuan.capstone.DTO.Firebase.ProductFDTO;
import com.jingyuan.capstone.R;

public class DetailActivity extends AppCompatActivity {
    static int PERMISSION_CODE = 100;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView label, des, store, phone;
    ImageButton backBtn, callBtn;
    String docData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (ContextCompat.checkSelfPermission(DetailActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(DetailActivity.this, new String[]{Manifest.permission.CALL_PHONE},PERMISSION_CODE);
        }
        label = findViewById(R.id.label);
        des = findViewById(R.id.des);
        store = findViewById(R.id.store);
        phone = findViewById(R.id.phone);
        backBtn = findViewById(R.id.back);
        callBtn = findViewById(R.id.call_btn);

        Intent i = getIntent();
        docData = i.getStringExtra("doc");

    }

    @Override
    protected void onStart() {
        super.onStart();
        DocumentReference docRef = db.collection("Product").document(docData);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    Log.d("GACHIMUCHI", "DocumentSnapshot data: " + document.getData());
                    ProductFDTO productFDTO = document.toObject(ProductFDTO.class);
                    assert productFDTO != null;
                    label.setText(productFDTO.getName());
                    des.setText(productFDTO.getDescription());
                }
            }
        });


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
                intent.setData(Uri.parse("tel:" + "phoneNumber"));
                startActivity(intent);
            }
        });
    }
}