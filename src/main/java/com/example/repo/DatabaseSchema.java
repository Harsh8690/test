package com.example.repo;

import com.example.confi.DB;
import com.example.entity.ProductDetails;
import com.example.entity.ResponseData;
import com.example.entity.UserDetails;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class DatabaseSchema {
    Connection con;

    {
        try {

            con = DB.getCon();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void post(UserDetails userDetails) {
        String q = "insert into user(id,name) values(?,?)";
        try {

            PreparedStatement ps = con.prepareStatement(q);
            ps.setInt(1, userDetails.getId());
            ps.setString(2, userDetails.getName());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<UserDetails> get() {
        String q = "select * from user";
        List<UserDetails> list = new ArrayList<>();
        try {

            PreparedStatement ps = con.prepareStatement(q);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                list.add(new UserDetails(rs.getInt(1), rs.getString(2)));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void put(int id, String name) {
        String q = "update user set name=? where id=?";
        try {

            PreparedStatement ps = con.prepareStatement(q);
            ps.setString(1, name);
            ps.setInt(2, id);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertInProductData(ProductDetails productDetails) {
        String q = "insert into products(product_id,product_name) values(?,?)";
        try {

            PreparedStatement ps = con.prepareStatement(q);
            ps.setInt(1, productDetails.getProductId());
            ps.setString(2, productDetails.getProductName());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<ProductDetails> getAllProducts() {
        List<ProductDetails> getAllProduct = new ArrayList<>();
        String q = "select * from products";
        try {

            PreparedStatement ps = con.prepareStatement(q);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                getAllProduct.add(new ProductDetails(rs.getInt(1), rs.getString(2)));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return getAllProduct;
    }

    public List<ResponseData> responseData() {
        List<ResponseData> response = new ArrayList<>();
        String q = "select id, name, product_name from user left join products on user.id=products.product_id";
        try {
            PreparedStatement ps = con.prepareStatement(q);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                response.add(new ResponseData(rs.getInt(1), rs.getString(2), rs.getString(3)));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

}
