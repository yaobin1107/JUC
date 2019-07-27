package threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ThreadPoolDemo_Callable {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        List<Future<Integer>> list = new ArrayList<>();
        //1.创建线程池
        ExecutorService pool = Executors.newFixedThreadPool(5);
        //2.为线程池中的线程池分配任务
        for (int i = 0; i <= 10; i++) {
            Future<Integer> future = pool.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    int sum = 0;
                    for (int j = 0; j <= 100; j++) {
                        sum += j;
                    }
                    return sum;
                }
            });
            list.add(future);
        }
        for (Future<Integer> future : list) {
            System.out.println(future.get());
        }
        //3.关闭线程池
        pool.shutdown();
    }
}
