package com.example;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.ApplicationLoadBalancerRequestEvent;
import com.amazonaws.services.lambda.runtime.events.ApplicationLoadBalancerResponseEvent;
import com.example.entity.ProductDetails;
import com.example.entity.UserDetails;
import com.example.repo.DatabaseSchema;
import com.example.service.ProductService;
import com.example.service.UserService;
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

        ApplicationLoadBalancerResponseEvent responseEvent = new ApplicationLoadBalancerResponseEvent();

        switch (httpMethod) {

            case "POST": {

                Gson gson = new Gson();

                if (event.getPath().startsWith("/product")) {

                    ProductDetails productDetails = gson.fromJson(event.getBody(), ProductDetails.class);
                    responseEvent.setStatusCode(200);
                    responseEvent.setStatusDescription("200 OK");
                    responseEvent.setHeaders(Map.of("data", productService.insertProduct(productDetails)));
                    return responseEvent;

                } else if (event.getPath().startsWith("/user")) {

                    UserDetails user = gson.fromJson(event.getBody(), UserDetails.class);
                    responseEvent.setStatusCode(200);
                    responseEvent.setStatusDescription("200 OK");
                    responseEvent.setHeaders(Map.of("data", userService.insertData(user)));
                    return responseEvent;
                }
            }
            case "GET": {
                Gson gson = new Gson();
                if (event.getPath().startsWith("/product/all")) {
                    responseEvent.setStatusCode(200);
                    responseEvent.setStatusDescription("200 OK");
                    responseEvent.setHeaders(Map.of("Content-Type :","text/plain;"));
                    responseEvent.setBody(gson.toJson(productService.getProducts()));
                    return responseEvent;
                }

                if (event.getPath().startsWith("/user/all")) {
                    responseEvent.setStatusCode(200);
                    responseEvent.setStatusDescription("200 OK");
                    responseEvent.setHeaders(Map.of("Content-Type :","text/plain;"));
                    responseEvent.setBody(gson.toJson(userService.getData()));
                    return responseEvent;
                }

                if (event.getPath().startsWith("/response/product")) {
                    responseEvent.setStatusCode(200);
                    responseEvent.setStatusDescription("200 OK ");
                    responseEvent.setHeaders(Map.of("Content-Type :","text/plain;"));
                    responseEvent.setBody(gson.toJson(userService.allResponse()));
                    return responseEvent;
                }
            }
            case "PUT": {

                String path = event.getPath();
                if (path.startsWith("/user/")) {

                    int id = Integer.parseInt(path.substring("/user/".length()));
                    Gson gson = new Gson();
                    UserDetails userDetails = gson.fromJson(event.getBody(), UserDetails.class);
                    responseEvent.setStatusCode(200);
                    responseEvent.setStatusDescription("200 OK");
                    responseEvent.setHeaders(Map.of("data", userService.putData(id, userDetails.getName())));
                    return responseEvent;
                }
            }
        }

        return responseEvent;
    }
}
