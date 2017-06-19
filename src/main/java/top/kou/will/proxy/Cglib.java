package top.kou.will.proxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by ZXL on 2017/6/1.
 */
public class Cglib {
    static class Requestable {
        public String request() {
            return "Requestable";
        }
    }

    static class RequestCtrlCallback implements MethodInterceptor {

        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            return methodProxy.invokeSuper(o, objects);
        }
    }

    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Requestable.class);
        enhancer.setCallback(new RequestCtrlCallback());

        Requestable proxy = (Requestable) enhancer.create();
        String response = proxy.request();

        System.out.println(response);
    }
}
