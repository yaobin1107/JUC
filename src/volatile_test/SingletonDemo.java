package volatile_test;

public class SingletonDemo {
    /**
     * 由于双端检索机制不能保证指令重排，依旧会出现问题，所以要加volatile禁止指令重排
     */
    private static volatile SingletonDemo instance = null;

    private SingletonDemo() {
        System.out.println(Thread.currentThread().getName() + "\t 我是构造方法！");
    }

    /**
     * 采用DCL模式（Double Check Lock 双端检索机制）
     * @return 对象
     */
    public static /*synchronized*/ SingletonDemo getInstance() {
        if (instance == null) {
            synchronized (SingletonDemo.class) {
                if (instance == null) {
                    instance = new SingletonDemo();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        /**
         * 单机版单例模式
         */
//        System.out.println(SingletonDemo.getInstance() == SingletonDemo.getInstance());
//        System.out.println(SingletonDemo.getInstance() == SingletonDemo.getInstance());
//        System.out.println(SingletonDemo.getInstance() == SingletonDemo.getInstance());
//        System.out.println("===============================");
        //并发多线程后，情况有变化,加锁可以解决
        for (int i = 1; i <= 10; i++) {
            new Thread(() -> {
                SingletonDemo.getInstance();
            }, String.valueOf(i)).start();
        }
    }
}
