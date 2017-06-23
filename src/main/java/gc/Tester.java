package gc;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.concurrent.*;

/**
 * Created by ZXL on 2017/6/22.
 */
public class Tester {
    private static final String THREAD_SIZE_LABEL = "thread.size";
    private static final String TASK_SIZE_LABEL = "task.size";

    static class Configuration {
        int threadSize = Runtime.getRuntime().availableProcessors();
        int taskSize = 1000;
    }

    static class CpuIntensiveTask implements Callable<CpuIntensiveTask> {
        private BigDecimal parameter;
        private Integer times;
        private Integer index;
        private long startTime;
        private long endTime;

        CpuIntensiveTask(BigDecimal parameter, Integer times, Integer index) {
            this.index = index;
            this.times = times;
            this.parameter = parameter;
        }

        @Override
        public CpuIntensiveTask call() throws Exception {
            startTime = System.currentTimeMillis();
            for (int i = 0; i < times; i++) {
                parameter = parameter.multiply(parameter);
            }
            endTime = System.currentTimeMillis();
            String.format("  -- Task [thread=%d, index=%d, taskTime=%d]", Thread.currentThread().getId(), index, endTime - startTime);
            return this;
        }
    }

    private static Collection<Callable<CpuIntensiveTask>> createTasks(int count) {
        Collection<Callable<CpuIntensiveTask>> tasks = new LinkedHashSet<>();
        for (int i = 0; i < count; i++) {
            tasks.add(new CpuIntensiveTask(BigDecimal.valueOf(1.1), 22, i));
            System.out.println(String.format(" -- Initialized Task [index=%d]", i));
        }
        return tasks;
    }

    private static Collection<Future<CpuIntensiveTask>> submitTasks(ThreadPoolExecutor executor, Collection<Callable<CpuIntensiveTask>> tasks) {
        Collection<Future<CpuIntensiveTask>> futures = new LinkedHashSet<>();
        for (Callable<CpuIntensiveTask> task : tasks) {
            futures.add(executor.submit(task));
        }
        return futures;
    }

    private static void cleanup(Collection<Future<CpuIntensiveTask>> futures) throws Exception {
        for (Future<CpuIntensiveTask> future : futures) {
            future.get();
        }
    }

    private static Configuration parseArgs(String... args) {
        Configuration configuration = new Configuration();
        for (int i = 0; i < args.length; i++) {
            String s = args[i].trim();
            if (s.contains("=") && s.startsWith(TASK_SIZE_LABEL)) {
                configuration.taskSize = Integer.valueOf(s.substring(s.indexOf("=") + 1).trim());
            }
            if (s.contains("=") && s.startsWith(THREAD_SIZE_LABEL)) {
                configuration.threadSize = Integer.valueOf(s.substring(s.indexOf("=") + 1).trim());
            }
        }
        return configuration;
    }

    private static ThreadPoolExecutor createThreadPoolExecutor(int threadSize) {
        return new ThreadPoolExecutor(
                threadSize, threadSize, 0L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(), new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                System.out.println(String.format("  --  Reject " + r + "  --  "));
            }
        });
    }

    public static void main(String[] args) throws Exception {
        Configuration configuration = parseArgs(args);
        ThreadPoolExecutor executor = createThreadPoolExecutor(configuration.threadSize);
        Collection<Callable<CpuIntensiveTask>> tasks = createTasks(configuration.taskSize);

        long x = System.currentTimeMillis();
        cleanup(submitTasks(executor, tasks));
        long y = System.currentTimeMillis();
        System.out.println(String.format(" -- Finished - [taskSize=%d, totalTime=%d]", configuration.taskSize, y-x));

        executor.shutdown();
    }

}
