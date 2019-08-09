package threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 执行很多短期异步的小程序或者负载较轻的服务器
 */
public class CachedThredDemo {
    public static void main(String[] args) {

        ExecutorService executor = Executors.newCachedThreadPool();

        try {
            for (int i = 1; i < 10; i++) {
                executor.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "处理业务！");
                });
                //TimeUnit.SECONDS.sleep(1);加入延迟则只会有一个线程处理业务
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }
}
