package volatile_test;


class ChangeData {

    volatile int number = 0;

    public void addTo60() {
        this.number = 60;
    }

    //请注意，此时的number有volatile关键字，不保证原子性
    public void addPlus() {
        number++;
    }
}

/**
 * 1.验证volatile的可见性
 *  1.1 假如 int number = 0；number变量之间没有添加 volatile 关键字修饰，没有可见性
 *  1.2 添加了 volatile 可以解决可见性问题
 * 2.验证volatile不保证原子性
 *  2.1 原子性是什么意思？
 *      不可分割，完整性，也即某个线程正在做某个具体业务时，中间不可以被加塞或者分割，需整体完整
 *      要么同时成功，要么同时失败
 *  2.2 why? 看案例
 *  2.3 如何解决？
 *      2.3.1 加锁
 *      2.3.2 原子类
 */
public class VisibleTest {
    public static void main(String[] args) {
        atomicTest();
    }

    /**
     * volatile 不保证原子性的案例
     */
    public static void atomicTest() {
        ChangeData data = new ChangeData();

        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    data.addPlus();
                }
            }, String.valueOf(i)).start();
        }
        //需要等待线程执行完成，取得number值
        while (Thread.activeCount() > 2) {
            Thread.yield();
        }
        System.out.println(Thread.currentThread().getName() + "\t finally number value：" + data.number);
    }

    /**
     * volatile 可以保证可见性的的案例
     */
    public static void visibleTest() {
        ChangeData data = new ChangeData();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t begin");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            data.addTo60();
            System.out.println(Thread.currentThread().getName() + "\t updated number value：" + data.number);
        }, "AAA").start();

        //第二个线程是main线程
        while (data.number == 0) {
            //等待循环
        }

        System.out.println(Thread.currentThread().getName() + "\t over,main thread get number：" + data.number);
    }
}
