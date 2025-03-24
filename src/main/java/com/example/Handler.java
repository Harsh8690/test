package com.example;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.ApplicationLoadBalancerRequestEvent;
import com.example.entity.ProductDetails;
import com.example.entity.UserDetails;
import com.example.repo.DatabaseSchema;
import com.example.service.ProductService;
import com.example.service.UserService;
import com.google.gson.Gson;

public class Handler implements RequestHandler<ApplicationLoadBalancerRequestEvent, String> {
    private UserService userService;
    private ProductService productService;

    public Handler(ProductService productService) {
        this.productService = productService;
    }

    public Handler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String handleRequest(ApplicationLoadBalancerRequestEvent event, Context context) {
        String httpMethod = event.getHttpMethod();
        DatabaseSchema repo = new DatabaseSchema();
        userService = new UserService(repo);
        productService = new ProductService(repo);

        switch (httpMethod) {

            case "POST": {

                Gson gson = new Gson();

                if (event.getPath().startsWith("/product")) {

                    ProductDetails productDetails = gson.fromJson(event.getBody(), ProductDetails.class);
                    return productService.insertProduct(productDetails);

                } else if (event.getPath().startsWith("/user")) {

                    UserDetails user = gson.fromJson(event.getBody(), UserDetails.class);
                    userService.insertData(user);

                }

            }
            case "GET": {
                Gson gson = new Gson();
                if (event.getPath().startsWith("/product/all")) {

                    return gson.toJson(productService.getProducts());

                }

                if (event.getPath().startsWith("/user/all")) {

                    return gson.toJson(userService.getData());

                }
            }
            case "PUT": {

                String path = event.getPath();
                if (path.startsWith("/user/")) {

                    int id = Integer.parseInt(path.substring("/user/".length()));
                    Gson gson = new Gson();
                    UserDetails userDetails = gson.fromJson(event.getBody(), UserDetails.class);

                    return userService.putData(id, userDetails.getName());

                }
            }
        }

        return "method not found";

    }
}
