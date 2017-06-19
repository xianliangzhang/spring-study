package top.kou.will.aop.spring;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.NameMatchMethodPointcutAdvisor;

/**
 * Created by ZXL on 2017/6/5.
 */
public class AOP {

    static class PerformanceMethodInterceptor implements MethodInterceptor {

        public Object invoke(MethodInvocation methodInvocation) throws Throwable {
            long startTime = System.currentTimeMillis();
            Object result = methodInvocation.proceed();
            long endTime = System.currentTimeMillis();

            System.out.println(String.format("PerformanceMethodInterceptor - %dms", endTime - startTime));
            return result;
        }
    }

    public static void main(String[] args) {
        ProxyFactory weaver = new ProxyFactory(new Executable());

        NameMatchMethodPointcutAdvisor advisor = new NameMatchMethodPointcutAdvisor(new PerformanceMethodInterceptor());
        advisor.setMappedName("execute");

        weaver.addAdvisor(advisor);

        Executable executable = (Executable) weaver.getProxy();
        executable.execute();
    }
}
