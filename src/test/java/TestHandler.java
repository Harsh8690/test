import com.amazonaws.services.lambda.runtime.events.ApplicationLoadBalancerRequestEvent;
import com.amazonaws.services.lambda.runtime.events.ApplicationLoadBalancerResponseEvent;
import com.example.Handler;
import com.example.service.ProductService;
import com.example.service.UserService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

public class TestHandler {
    private UserService userService;
    private ProductService productService;

    @Test
    public void post() {
        ApplicationLoadBalancerRequestEvent requestEvent = new ApplicationLoadBalancerRequestEvent();
        requestEvent.setHttpMethod("POST");
        requestEvent.setPath("/user");
        requestEvent.setBody("{\n" +
                "  \"id\": 8,\n" +
                "  \"name\": \"aman singh\"\n" +
                "}");
        Handler handler = new Handler(userService);
        handler.handleRequest(requestEvent, null);
    }

    @Test
    public void get() {
        ApplicationLoadBalancerRequestEvent requestEvent = new ApplicationLoadBalancerRequestEvent();
        requestEvent.setHttpMethod("GET");
        requestEvent.setPath("/user/all");
        Handler handler = new Handler(userService);
        ApplicationLoadBalancerResponseEvent responseEvent = handler.handleRequest(requestEvent, null);

        System.out.println(responseEvent);
    }

    @Test
    public void put() {

        ApplicationLoadBalancerRequestEvent requestEvent = new ApplicationLoadBalancerRequestEvent();
        requestEvent.setHttpMethod("PUT");
        requestEvent.setPath("/user/2");
        requestEvent.setBody("{\n" +
                "  \"name\": \"Raman\"\n" +
                "}");

        Handler handler = new Handler(userService);
        ApplicationLoadBalancerResponseEvent responseEvent = handler.handleRequest(requestEvent, null);
        System.out.println(responseEvent);
    }

    @Test
    public void postProduct() {
        ApplicationLoadBalancerRequestEvent requestEvent = new ApplicationLoadBalancerRequestEvent();
        requestEvent.setPath("/product");
        requestEvent.setHttpMethod("POST");
        requestEvent.setBody("{\n" +
                "  \"productId\": 7,\n" +
                "  \"productName\": \"viva book laptop\"\n" +
                "}");
        Handler handler = new Handler(productService);
        ApplicationLoadBalancerResponseEvent responseEvent = handler.handleRequest(requestEvent, null);
        System.out.println(responseEvent);
    }

    @Test
    public void getAllProduct() {
        ApplicationLoadBalancerRequestEvent requestEvent = new ApplicationLoadBalancerRequestEvent();
        requestEvent.setHttpMethod("GET");
        requestEvent.setPath("/product/all");
        Handler handler = new Handler(productService);
        ApplicationLoadBalancerResponseEvent responseEvent = handler.handleRequest(requestEvent, null);
        System.out.println(responseEvent);
    }

    @Test
    public void getAllResponse() {
        ApplicationLoadBalancerRequestEvent requestEvent = new ApplicationLoadBalancerRequestEvent();
        requestEvent.setPath("/response/product");
        requestEvent.setHttpMethod("GET");
        Handler handler = new Handler(productService);
        ApplicationLoadBalancerResponseEvent responseEvent = handler.handleRequest(requestEvent, null);
        System.out.println(responseEvent);
    }
}