package pc_case;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 通过Lock解决
 */
public class ProductAndConsumer_useLock {
    public static void main(String[] args) {
        Clerk1 clerk = new Clerk1();
        Producer1 producer = new Producer1(clerk);
        Consumer1 consumer = new Consumer1(clerk);

        new Thread(producer, "生产者A").start();
        new Thread(consumer, "消费者B").start();
    }
}

class Clerk1 {
    private int product = 10;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    //进货
    public void get() {
        lock.lock();
        try {
            while (product >= 10) {//为了避免虚假唤醒，应该总是使用在循环中
                System.out.println("产品已满！");
                try {
                    condition.await();//满了等
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            lock.unlock();
        }
        System.out.println(Thread.currentThread().getName() + ":" + ++product);
        condition.signalAll();
    }

    //卖货
    public void sale() {
        lock.lock();
        try {
            while (product <= 0) {
                System.out.println("缺货！");
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            lock.unlock();
        }
        System.out.println(Thread.currentThread().getName() + ":" + --product);
        condition.signalAll();
    }
}

class Producer1 implements Runnable {
    private Clerk1 clerk;

    public Producer1(Clerk1 clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            clerk.get();
        }
    }
}

class Consumer1 implements Runnable {
    private Clerk1 clerk;

    public Consumer1(Clerk1 clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            clerk.sale();
        }
    }
}