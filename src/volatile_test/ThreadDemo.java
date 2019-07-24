package volatile_test;

/**
 * @Author: yaobin
 * @Date: 2019/7/24 23:09
 */
public class ThreadDemo implements Runnable {

    public static void main(String[] args) {
        ThreadDemo demo = new ThreadDemo();
        new Thread(demo).start();

        while (true) {
            if (demo.isFlag()) {
                System.out.println("-----------------------");
                break;
            }
        }
    }

    /**
     * volatile关键字：当多个线程操作共享数据时，可以保证内存中数据的可见
     * 相较于synchronized是一种轻量级的同步策略
     * 注意：
     * 1.volatile 不具备“互斥性”（多线程抢锁，一个线程进来后，其他线程就进不来了）
     * 2.volatile 不能保证变量的“原子性”
     */
    private volatile boolean flag = false;

    @Override
    public void run() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        flag = true;
        System.out.println("flag=" + isFlag());
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
