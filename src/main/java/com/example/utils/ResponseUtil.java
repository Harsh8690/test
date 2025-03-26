package com.example.utils;

import com.amazonaws.services.lambda.runtime.events.ApplicationLoadBalancerResponseEvent;

import java.util.Map;

public class ResponseUtil {

    public static ApplicationLoadBalancerResponseEvent response(String response, int statusCode, String statusCodeInfo, Map<String, String> header) {
        ApplicationLoadBalancerResponseEvent responseEvent = new ApplicationLoadBalancerResponseEvent();
        responseEvent.setBody(response);
        responseEvent.setStatusCode(statusCode);
        responseEvent.setStatusDescription(statusCodeInfo);
        responseEvent.setHeaders(header);
        return responseEvent;
    }

    public static ApplicationLoadBalancerResponseEvent notFoundResponse(String message, int statusCode, String statusCodeInfo) {

        ApplicationLoadBalancerResponseEvent responseEvent = new ApplicationLoadBalancerResponseEvent();
        responseEvent.setBody(message);
        responseEvent.setStatusCode(statusCode);
        responseEvent.setStatusDescription(statusCodeInfo);
        return responseEvent;
    }

}
