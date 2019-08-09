package threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 一个池子一个线程
 */
public class SingleThreadDemo {
    public static void main(String[] args) {

        ExecutorService executor = Executors.newSingleThreadExecutor();

        try {
            for (int i = 1; i < 10; i++) {
                executor.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "处理业务！");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }
}
