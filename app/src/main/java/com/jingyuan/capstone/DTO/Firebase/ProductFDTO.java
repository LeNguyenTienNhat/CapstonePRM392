package com.jingyuan.capstone.DTO.Firebase;

import com.google.firebase.firestore.DocumentReference;

public class ProductFDTO {
    private String category;
    private String thumbnail;
    private String name;
    private String description;
    private Long price;
    private int stock;
    private DocumentReference store;

    public ProductFDTO() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public DocumentReference getStore() {
        return store;
    }

    public void setStore(DocumentReference store) {
        this.store = store;
    }

    public String toString(ProductFDTO productFDTO) {
        return category + "\n " + name + "\n " + description + "\n " + price + "\n " + stock;
    }
}
