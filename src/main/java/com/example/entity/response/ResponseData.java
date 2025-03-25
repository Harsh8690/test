package com.example.entity.response;

public class ResponseData {
    private  int id;
    private String name;
    private String productName;

    public ResponseData(int id, String name, String productName) {
        this.id = id;
        this.name = name;
        this.productName = productName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
