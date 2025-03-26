package com.example;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.ApplicationLoadBalancerRequestEvent;
import com.amazonaws.services.lambda.runtime.events.ApplicationLoadBalancerResponseEvent;
import com.example.entity.requset.ProductDetails;
import com.example.entity.requset.UserDetails;
import com.example.repo.DatabaseSchema;
import com.example.service.ProductService;
import com.example.service.UserService;
import com.example.utils.ResponseUtil;
import com.google.gson.Gson;

import java.util.Map;

public class Handler implements RequestHandler<ApplicationLoadBalancerRequestEvent, ApplicationLoadBalancerResponseEvent> {
    private UserService userService;
    private ProductService productService;

    public Handler(ProductService productService) {
        this.productService = productService;
    }

    public Handler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ApplicationLoadBalancerResponseEvent handleRequest(ApplicationLoadBalancerRequestEvent event, Context context) {
        String httpMethod = event.getHttpMethod();
        DatabaseSchema repo = new DatabaseSchema();
        userService = new UserService(repo);
        productService = new ProductService(repo);

        switch (httpMethod) {

            case "POST": {

                Gson gson = new Gson();

                if (event.getPath().startsWith("/product")) {

                    ProductDetails productDetails = gson.fromJson(event.getBody(), ProductDetails.class);
                    return ResponseUtil.response("", 200, "200 OK", Map.of("data ", productService.insertProduct(productDetails)));

                } else if (event.getPath().startsWith("/user")) {

                    UserDetails user = gson.fromJson(event.getBody(), UserDetails.class);
                    return ResponseUtil.response("", 200, "200 OK", Map.of("data ", userService.insertData(user)));

                }
            }
            case "GET": {
                Gson gson = new Gson();
                if (event.getPath().startsWith("/product/all")) {
                    return ResponseUtil.response(gson.toJson(productService.getProducts()), 200, "200 OK", Map.of("Content-Type ", " text/plain;"));
                } else if (event.getPath().startsWith("/user/all")) {

                    return ResponseUtil.response(gson.toJson(userService.getData()), 200, "200 OK", Map.of("Content-Type ", " text/plain;"));

                } else if (event.getPath().startsWith("/response/product")) {

                    return ResponseUtil.response(gson.toJson(userService.allResponse()), 200, "200 OK", Map.of("Content-Type ", " text/plain;"));
                }
            }
            case "PUT": {

                String path = event.getPath();
                if (path.startsWith("/user/")) {

                    int id = Integer.parseInt(path.substring("/user/".length()));
                    Gson gson = new Gson();
                    UserDetails userDetails = gson.fromJson(event.getBody(), UserDetails.class);
                    return ResponseUtil.response("", 200, "200 OK", Map.of("data ", userService.putData(id, userDetails.getName())));

                }
            }
        }

        return ResponseUtil.notFoundResponse("Request not found", 404, "404, NOT FOUND");
    }

}