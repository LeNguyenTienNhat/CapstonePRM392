package com.jingyuan.capstone.Controller;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.jingyuan.capstone.R;
import com.jingyuan.capstone.RecyclerViewAdapter;
import com.jingyuan.capstone.Model.ProductModel;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        ArrayList<ProductModel> productList = setUpProductModels();
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, productList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }

    private ArrayList<ProductModel> setUpProductModels() {
        ArrayList<ProductModel> productList = new ArrayList<>();
        String[] productLabels = getResources().getStringArray(R.array.label);
        String[] productDes = getResources().getStringArray(R.array.description);
        String[] productStore = getResources().getStringArray(R.array.store);
        String[] productPhoneNumber = getResources().getStringArray(R.array.phoneNumber);
        int[] productThumbnail = {
                R.drawable.blh, R.drawable.gog, R.drawable.grn,
                R.drawable.hol, R.drawable.med, R.drawable.plc,
                R.drawable.sme, R.drawable.stv, R.drawable.ths
        };
        for (int i = 0; i < productLabels.length; i++) {
            productList.add(new ProductModel(productThumbnail[i], productLabels[i],
                    productDes[i], productStore[i], productPhoneNumber[i]));
        }
        return productList;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.search_option) {
            Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
            return true;
        } else if (itemId == R.id.cart_option) {
            Toast.makeText(this, "Cart", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}