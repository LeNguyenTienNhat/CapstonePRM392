package com.jingyuan.capstone.DTO.Firebase;

public class ProductFDTO {
    private String category;
    private String thumbnail;
    private String name;
    private String description;
    private Long price;
    private int stock;
    private StoreFDTO store;

    public ProductFDTO() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getThumbnail() {
        return "https://firebasestorage.googleapis.com/v0/b/capstone-c62ee.appspot.com/o/" + thumbnail + "?alt=media";
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

    public StoreFDTO getStore() {
        return store;
    }

    public void setStore(StoreFDTO store) {
        this.store = store;
    }

    public String toString(ProductFDTO productFDTO) {
        return category + "\n " + name + "\n " + description + "\n " + price + "\n " + stock;
    }
}
