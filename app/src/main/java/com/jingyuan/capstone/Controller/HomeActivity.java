package com.jingyuan.capstone.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.jingyuan.capstone.DTO.Firebase.ProductFDTO;
import com.jingyuan.capstone.DTO.View.ProductItemDTO;
import com.jingyuan.capstone.R;
import com.jingyuan.capstone.RecyclerViewAdapter;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView homeBtn;
    TextView productsBtn;
    TextView storeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        homeBtn = findViewById(R.id.home);
        homeBtn.setOnClickListener(v -> {
            Toast.makeText(HomeActivity.this, "Home Button clicked",
                    Toast.LENGTH_SHORT).show();
        });
        productsBtn = findViewById(R.id.products);
        productsBtn.setOnClickListener(v -> {
            Toast.makeText(HomeActivity.this, "Product Button clicked",
                    Toast.LENGTH_SHORT).show();
        });
        storeBtn = findViewById(R.id.stores);
        storeBtn.setOnClickListener(v -> {
            Toast.makeText(HomeActivity.this, "Store Button clicked",
                    Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        ArrayList<ProductItemDTO> productItemsList = new ArrayList<>();
        db.collection("Product").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot docSnap : task.getResult()) {
                    ProductFDTO productFDTO = docSnap.toObject(ProductFDTO.class);
                    ProductItemDTO itemDTO = getProductItemDTO(docSnap, productFDTO);
                    productItemsList.add(itemDTO);
                }
                RecyclerView recyclerView = findViewById(R.id.recycler_view);
                RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, productItemsList);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            }
        });
    }

    private static ProductItemDTO getProductItemDTO(QueryDocumentSnapshot docSnap, ProductFDTO productFDTO) {
        ProductItemDTO itemDTO = new ProductItemDTO();
        itemDTO.setDoc(docSnap.getId());
        itemDTO.setCategory(productFDTO.getCategory());
        itemDTO.setName(productFDTO.getName());
        itemDTO.setPrice(productFDTO.getPrice());
        itemDTO.setThumbnail(productFDTO.getThumbnail());
        String status = "Available";
        if (productFDTO.getStock() == 0) status = "Out of stock";
        itemDTO.setStatus(status);
        return itemDTO;
    }

    protected void updateTabNav() {
        
    }
}