package top.kou.will;

import org.springframework.beans.factory.InitializingBean;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

public class App {

    static class CalculatingCallable implements Callable<BigDecimal> {
        public BigDecimal call() throws Exception {
            BigDecimal result = new BigDecimal("1.1");
            for (int i = 0; i < 20; i++) {
                result = result.multiply(result);
            }
            return result;
        }
    }

    public static void main(String[] args) throws Exception {
        ExecutorService es = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(1), new RejectedExecutionHandler() {
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                System.out.println("REJECT --- ");
            }
        });

        List<Future<BigDecimal>> tasks = new LinkedList<Future<BigDecimal>>();

        tasks.add(es.submit(new CalculatingCallable()));
        System.out.println(es);

        tasks.add(es.submit(new CalculatingCallable()));
        System.out.println(es);

        for (Future future : tasks) {
            System.out.println(future.get().toString().substring(0, 10));
        }

        es.shutdown();
    }
}
