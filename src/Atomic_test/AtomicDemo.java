package Atomic_test;

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

    private int serialNumber = 0;

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
        return serialNumber++;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }
}
