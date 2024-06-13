package com.jingyuan.capstone.Controller;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.jingyuan.capstone.DTO.Firebase.ProductFDTO;
import com.jingyuan.capstone.R;

public class TestActivity extends AppCompatActivity {
    ProductFDTO productFDTO = new ProductFDTO();
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    TextView nameView;
    TextView priceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        nameView = findViewById(R.id.name);
        priceView = findViewById(R.id.price);
        Log.d("GACHIMUCHI", "On create");
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Product")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int counter = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                counter++;
                                Log.d("GACHIMUCHI", "Get ProductDTO:"+ counter + "\n");
                                productFDTO = document.toObject(ProductFDTO.class);
                                Log.d("GACHIMUCHI", "Set name:"+ counter + productFDTO.getName() + "\n");
                                nameView.setText(productFDTO.getName());
                                priceView.setText(productFDTO.getDescription());
                            }
                        }
                    }
                });
    }
}