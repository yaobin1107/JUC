package atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: yaobin
 * @Date: 2019/7/25 0:04
 */
public class AtomicDemo {

    public static void main(String[] args) {
        Atomic atomic = new Atomic();
        for (int i = 0; i < 10; i++) {
            new Thread(atomic, "T" + i).start();
        }
    }
}

class Atomic implements Runnable {

    //private int serialNumber = 0;
    //详见src/note中i++的原子性问题
    private AtomicInteger serialNumber = new AtomicInteger();

    @Override
    public void run() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + ":" + getSerialNumber());
    }

    public int getSerialNumber() {
        //return serialNumber++;
        return serialNumber.getAndIncrement();
    }
}
