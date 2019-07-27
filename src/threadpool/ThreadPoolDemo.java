package threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ①线程池：提供了一个线程队列，队列中保存着所有等待状态的线程，避免了创建与销毁的额外开销，提高了响应的速度
 * ②体系结构：
 *  java,util.concurrent.Executor：负责线程的使用与调度的跟接口
 *      |--*ExecutorService 子接口：线程池的主要接口
 *          |--ThreadPoolExecutor 线程池的实现类
 *          |--ScheduleExecutorService 子接口：用于线程调度
 *              |--ScheduleThreadPoolExecutor：实现类，继承ThreadPoolExecutor，实现了ScheduleThreadPoolExecutor
 * ③工具类：Executors
 *  ExecutorService newFixedThreadPool()：创建固定大小的线程池
 *  ExecutorService newCachedThreadPool()：缓存线程池，线程池的数量不固定，可以根据需求自动的更改数量
 *  ExecutorService newSingleThreadExecutor()：创建单个线程池，池中只有一个线程
 *
 *  ScheduleExecutorService new ScheduleThreadPool()：创建固定大小的线程池，可以延迟或定时执行任务
 */
public class ThreadPoolDemo {
    public static void main(String[] args) {
        //1.创建线程池
        ExecutorService pool = Executors.newFixedThreadPool(5);
        ThreadPoolTest demo = new ThreadPoolTest();
        //2.为线程池中的线程池分配任务
        for (int i = 0; i < 10; i++) {
            pool.submit(demo);
        }
        //3.关闭线程池
        pool.shutdown();
    }
}

class ThreadPoolTest implements Runnable {

    private int number = 0;

    @Override
    public void run() {
        while (number <= 100) {
            System.out.println(Thread.currentThread().getName() + ":" + number++);
        }
    }
}