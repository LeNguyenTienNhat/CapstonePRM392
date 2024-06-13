package com.jingyuan.capstone.Service;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jingyuan.capstone.DTO.Firebase.ProductFDTO;
import com.jingyuan.capstone.DTO.View.ProductItemDTO;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HomeService {
    private final FirebaseFirestore db;

    public HomeService() {
        this.db = FirebaseFirestore.getInstance();
    }

    public ArrayList<ProductItemDTO> setUpProducts() {
        ArrayList<ProductItemDTO> productItemsList = new ArrayList<>();
        List<DocumentSnapshot> productListSnap = db.collection("Product").get().getResult().getDocuments();
        for (DocumentSnapshot docSnap : productListSnap) {
            ProductFDTO productFDTO = docSnap.toObject(ProductFDTO.class);
            assert productFDTO != null;
            ProductItemDTO itemDTO = new ProductItemDTO();
            itemDTO.setDoc(docSnap.getId());
            itemDTO.setCategory(productFDTO.getCategory());
            itemDTO.setThumbnail(getBitmapFromURL(productFDTO.getThumbnail()));
            itemDTO.setName(productFDTO.getName());
            itemDTO.setPrice(productFDTO.getPrice());
            String status = "Available";
            if (productFDTO.getStock() == 0) status = "Out of stock";
            itemDTO.setStatus(status);
            productItemsList.add(itemDTO);
        }
        return productItemsList;
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            return null;
        }
    }
}
