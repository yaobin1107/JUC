package cas;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * CAS是什么？compare and swap
 */
public class CASDemo {
    public static void main(String[] args) {
        AtomicInteger integer = new AtomicInteger(5);

        System.out.println(integer.compareAndSet(5, 2019) + "\t current data：" + integer.get());
        System.out.println(integer.compareAndSet(5, 2014) + "\t current data：" + integer.get());
    }
}
