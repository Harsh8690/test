import com.amazonaws.services.lambda.runtime.events.ApplicationLoadBalancerRequestEvent;
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
                "  \"id\": 7,\n" +
                "  \"name\": \"anuja\"\n" +
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
        String s = handler.handleRequest(requestEvent, null);

        System.out.println((s));
    }

    @Test
    public void put() {

        ApplicationLoadBalancerRequestEvent requestEvent = new ApplicationLoadBalancerRequestEvent();
        requestEvent.setHttpMethod("PUT");
        requestEvent.setPath("/user/2");
        requestEvent.setBody("{\n" +
                "  \"name\": \"harry\"\n" +
                "}");

        Handler handler = new Handler(userService);
        String s = handler.handleRequest(requestEvent, null);
        System.out.println(s);

    }

    @Test
    public void postProduct() {
        ApplicationLoadBalancerRequestEvent requestEvent = new ApplicationLoadBalancerRequestEvent();
        requestEvent.setPath("/product");
        requestEvent.setHttpMethod("POST");
        requestEvent.setBody("{\n" +
                "  \"productId\": 6,\n" +
                "  \"productName\": \"dell laptop\"\n" +
                "}");
        Handler handler = new Handler(productService);
        String s = handler.handleRequest(requestEvent, null);
        System.out.println(s);

    }

    @Test
    public void getAllProduct() {
        ApplicationLoadBalancerRequestEvent requestEvent = new ApplicationLoadBalancerRequestEvent();
        requestEvent.setHttpMethod("GET");
        requestEvent.setPath("/product/all");
        Handler handler = new Handler(productService);
        String s = handler.handleRequest(requestEvent, null);
        System.out.println(s);
    }

}
