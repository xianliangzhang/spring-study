package top.kou.will.test;

import org.springframework.aop.ThrowsAdvice;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by ZXL on 2017/6/21.
 */
public class Test1 {

    static class CalculatingTask implements Callable<Integer> {
        private BigDecimal parameter;
        private int times;
        private int index;

        CalculatingTask(BigDecimal parameter, int times, int index) {
            this.times = times;
            this.parameter = parameter;
            this.index = index;
        }

        @Override
        public Integer call() throws Exception {
            for (int i = 0; i < times; i++) {
                parameter = parameter.multiply(parameter);
            }
            return index;
        }
    }

    public static void main(String[] args) throws Exception {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 2, 1, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(2), new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                System.out.println(String.format("  -- Reject --  " + r));
            }
        });

        List<Future<Integer>> tasks = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            CalculatingTask task = new CalculatingTask(BigDecimal.valueOf(1.1), 15, i);
            tasks.add(executor.submit(task));
            System.out.println(executor);
        }

        for (Future<Integer> future : tasks) {
            System.out.println(future.get());
        }

        Object object = new LinkedList<>();
        int size = ((List)object).size();

        executor.shutdownNow();
    }
}
