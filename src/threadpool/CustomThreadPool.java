package threadpool;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 手写一个线程池
 */
public class CustomThreadPool {
    public static void main(String[] args) {
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(2,
                5,
                1,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(3),
                Executors.defaultThreadFactory(),
                /*new ThreadPoolExecutor.AbortPolicy() 最大值 = maximumPoolSize + BlockingQueueCapacity 超过报异常*/
                /*new ThreadPoolExecutor.CallerRunsPolicy() 超过的任务由调用者执行（此时的调用者为main）*/
                /*new ThreadPoolExecutor.DiscardOldestPolicy() 丢弃等待最久的任务*/
                new ThreadPoolExecutor.DiscardPolicy() /*多出来的任务直接丢弃*/);

        try {
            for (int i = 1; i <= 10; i++) {
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "\t处理业务！");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }
}
