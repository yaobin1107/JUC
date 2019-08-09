package lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 一：用于解决多线程安全问题的方式；
 * synchronized：隐式锁
     * 1.同步代码块
     * 2.同步方式
 * jdk1.5以后：显示锁
     * 3.同步 Lock：需要通过lock()方法上锁，unlock()
 */
public class _Sync {
    public static void main(String[] args) {
        Ticket ticket = new Ticket();
        new Thread(ticket,"窗口1：").start();
        new Thread(ticket,"窗口2：").start();
        new Thread(ticket,"窗口3：").start();
    }
}

class Ticket implements Runnable {

    private int ticket = 100;
    private Lock lock = new ReentrantLock();
    @Override
    public void run() {
        while (true) {
            lock.lock();//上锁
            try {
                if (ticket > 0){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "完成售票，余票为：" + --ticket);
                }
            } finally {
                lock.unlock();//释放锁
            }
        }
    }
}
