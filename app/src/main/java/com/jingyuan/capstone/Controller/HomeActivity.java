package com.jingyuan.capstone.Controller;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.messaging.FirebaseMessaging;
import com.jingyuan.capstone.DTO.Firebase.CategoryFDTO;
import com.jingyuan.capstone.DTO.Firebase.ProductFDTO;
import com.jingyuan.capstone.DTO.View.ProductItem;
import com.jingyuan.capstone.R;
import com.jingyuan.capstone.Adapter.RecyclerViewAdapter;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static final String CHANNEL_ID = "NoticeChannelID";
    public static final String CHANNEL_NAME = "Notice name";
    public static final String CHANNEL_DESC = "Description";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
        channel.setDescription(CHANNEL_DESC);
        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(channel);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        ImageButton shopBtn = findViewById(R.id.shop);
        shopBtn.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ShopActivity.class);
            startActivity(intent);
        });

        ImageButton cartBtn = findViewById(R.id.cart);
        cartBtn.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, CartActivity.class);
            startActivity(intent);
        });

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,
                toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getFCMToken();
    }

    @Override
    protected void onStart() {
        super.onStart();
        ArrayList<ProductItem> productItemsList = new ArrayList<>();
        db.collection("Product").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot docSnap : task.getResult()) {
                    ProductFDTO productFDTO = docSnap.toObject(ProductFDTO.class);
                    ProductItem itemDTO = getProductItemDTO(docSnap, productFDTO);
                    productItemsList.add(itemDTO);
                }
                RecyclerView recyclerView = findViewById(R.id.recycler_view);
                RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, productItemsList);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            }
        });

        NavigationView nv = findViewById(R.id.nav_view);
        View header = nv.getHeaderView(0);
        TextView navUsername = header.findViewById(R.id.username);
        TextView navEmail = header.findViewById(R.id.email);
        ImageView pfp = header.findViewById(R.id.pfp);

        Intent i = getIntent();
        navUsername.setText(i.getStringExtra("username"));
        navEmail.setText(i.getStringExtra("email"));
        Glide.with(HomeActivity.this).load(i.getStringExtra("pfp")).into(pfp);
        Log.d("GACHI", i.getStringExtra("pfp") + "\n");
    }

    private static ProductItem getProductItemDTO(QueryDocumentSnapshot docSnap, ProductFDTO productFDTO) {
        ProductItem itemDTO = new ProductItem();
        itemDTO.setDoc(docSnap.getId());
        CategoryFDTO cat = productFDTO.getCategory();
        itemDTO.setCategory(cat.getName());
        itemDTO.setName(productFDTO.getName());
        itemDTO.setPrice(productFDTO.getPrice());
        itemDTO.setThumbnail(productFDTO.getThumbnail());
        String status = "Available";
        if (productFDTO.getStock() == 0) status = "Out of stock";
        itemDTO.setStatus(status);
        return itemDTO;
    }

    protected void getFCMToken() {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                String token = task.getResult();
            }
        });
    }
}