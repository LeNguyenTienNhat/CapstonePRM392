package com.jingyuan.capstone.Model;
public class ProductModel {
    int thumbnail;
    String label;
    String des;
    String store;
    String phoneNumber;

    public ProductModel(int thumbnail, String label, String des, String store, String phoneNumber) {
        this.thumbnail = thumbnail;
        this.label = label;
        this.des = des;
        this.store = store;
        this.phoneNumber = phoneNumber;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public String getLabel() {
        return label;
    }

    public String getDes() {
        return des;
    }
    public String getStore() { return store; }
    public String getPhoneNumber() { return phoneNumber; }
}
