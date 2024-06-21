package com.jingyuan.capstone.Controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.jingyuan.capstone.Adapter.CartItemAdapter;
import com.jingyuan.capstone.DTO.View.Cart;
import com.jingyuan.capstone.DTO.View.CartItem;
import com.jingyuan.capstone.R;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    public static final String CART_EMPTY = "cart_status";
    TextView cartStatusNotice;
    SharedPreferences sf;
    SharedPreferences.Editor editor;
    RecyclerView cartItems;
    ImageButton backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        cartStatusNotice = findViewById(R.id.cart_status_display);
        cartItems = findViewById(R.id.cart_items_list);
        backBtn = findViewById(R.id.back);
    }

    @Override
    protected void onStart() {
        super.onStart();
        sf = getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        editor = sf.edit();
        editor.putBoolean(CART_EMPTY, false);
        editor.apply();
        Log.d("GACHIMUCHI", "" + sf.getBoolean(CART_EMPTY, true));
        if (sf.getBoolean(CART_EMPTY, true)) {
            cartStatusNotice.setVisibility(View.VISIBLE);
        } else {
            cartItems.setVisibility(View.VISIBLE);
            showCartItems();
        }
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CartActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });
    }

    public void showCartItems() {
        Gson gson = new Gson();
        String plainJsonString = sf.getString("Cart", "");
        Cart cart = gson.fromJson(plainJsonString, Cart.class);
        ArrayList<CartItem> cartList = cart.getItems();
        CartItemAdapter adapter = new CartItemAdapter(this, cartList);
        cartItems.setAdapter(adapter);
        cartItems.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }
}