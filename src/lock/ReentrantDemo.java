package lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantDemo {
    /**
     * 可重入锁：
     *  同一线程外层函数获得锁之后，内层递归函数仍然能获取该锁的代码
     *  在同一个线程再外层方法获取锁的时候，在进入内层方法时会自动获取锁
     *  即线程可以进入任何一个它已经拥有的锁所同步着的代码块
     * @param args
     */
    public static void main(String[] args) {
        Phone phone = new Phone();
        new Thread(() -> {
            phone.sendMsg();
        }, "T1").start();

        new Thread(() -> {
            phone.sendMsg();
        }, "T2").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("===========================");

        new Thread(phone, "T3").start();
        new Thread(phone, "T4").start();

    }
}

class Phone implements Runnable {

    public synchronized void sendMsg() {
        System.out.println(Thread.currentThread().getName() + "\t invoked sendMsg()");
        sendEmail();
    }

    public synchronized void sendEmail() {
        System.out.println(Thread.currentThread().getName() + "\t invoked sendEmail()");
    }

    //==================================ReentrantLock也是重入锁====================================//

    Lock lock = new ReentrantLock();

    @Override
    public void run() {
        get();
    }

    public void get() {
        lock.lock();
        try {
            //线程可以进入任何一个它已经拥有的锁
            //所同步着的代码块
            System.out.println(Thread.currentThread().getName() + "\t invoked get()");
            set();
        } finally {
            lock.unlock();
        }
    }

    public void set() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t invoked set()");
        } finally {
            lock.unlock();
        }
    }
}