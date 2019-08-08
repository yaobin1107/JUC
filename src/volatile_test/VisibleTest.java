package volatile_test;


class ChangeData {

    volatile int number = 0;

    public void addTo60() {
        this.number = 60;
    }
}

/**
 * 验证volatile的可见性
 * 1.假如 int number = 0；number变量之间没有添加 volatile 关键字修饰，没有可见性
 */
public class VisibleTest {
    public static void main(String[] args) {
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
