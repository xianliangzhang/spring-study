package top.kou.will.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by ZXL on 2017/6/1.
 */
public class Main {
    static class RequestProxy implements IRequest {
        IRequest request;

        RequestProxy(IRequest request) {
            this.request = request;
        }

        public String request() {
            return request.request();
        }
    }

    static class SubjectProxy implements ISubject {
        ISubject subject;

        SubjectProxy(ISubject subject) {
            this.subject = subject;
        }

        public String request() {
            return subject.request();
        }
    }

    static class RequestProxyImpl implements InvocationHandler {
        Object object;

        RequestProxyImpl(Object obj) {
            this.object = obj;
        }

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            return method.invoke(object, args);
        }
    }

    public static void main(String... args) {
        IRequest iRequest = (IRequest) Proxy.newProxyInstance(Main.class.getClassLoader(), new Class[]{IRequest.class},
                new RequestProxyImpl(new RequestImpl()));
        ISubject iSubject = (ISubject) Proxy.newProxyInstance(Main.class.getClassLoader(), new Class[]{ISubject.class},
                new RequestProxyImpl(new SubjectImpl()));

        System.out.println(iRequest.request());
        System.out.println(iSubject.request());
    }
}
