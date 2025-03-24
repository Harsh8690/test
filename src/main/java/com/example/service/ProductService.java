package com.example.service;

import com.example.entity.ProductDetails;
import com.example.repo.DatabaseSchema;

import java.util.List;

public class ProductService {
    private DatabaseSchema repo;

    public ProductService(DatabaseSchema repo) {
        this.repo = repo;
    }

    public String insertProduct(ProductDetails productDetails) {
        repo.insertInProductData(productDetails);
        return "inserted data into products";
    }

    public List<ProductDetails> getProducts() {

        return repo.getAllProducts();

    }
}
