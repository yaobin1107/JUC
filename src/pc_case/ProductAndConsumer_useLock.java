package pc_case;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 通过Lock解决
 */
public class ProductAndConsumer_useLock {
    public static void main(String[] args) {
        ShareDate date = new ShareDate();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    try {
                        date.increment();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "AA").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    try {
                        date.decrement();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "BB").start();
    }
}

class ShareDate {
    private int number = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void increment() throws InterruptedException {
        try {
            lock.lock();
            while (number != 0) {//判断
                condition.await();//等待
            }
            number++;//干活
            System.out.println(Thread.currentThread().getName() + "\t " + number);
            condition.signalAll();//唤醒
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void decrement() throws InterruptedException {
        try {
            lock.lock();
            while (number == 0) {//判断
                condition.await();//等待
            }
            number--;//干活
            System.out.println(Thread.currentThread().getName() + "\t " + number);
            condition.signalAll();//唤醒
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
