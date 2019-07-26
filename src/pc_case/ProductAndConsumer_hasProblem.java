package pc_case;

/**
 * 存在问题的生产者消费者案例
 */
public class ProductAndConsumer_hasProblem {
    public static void main(String[] args) {
        Clerk clerk = new Clerk();
        Producer producer = new Producer(clerk);
        Consumer consumer = new Consumer(clerk);

        new Thread(producer, "生产者A").start();
        new Thread(consumer, "消费者B").start();
    }
}

class Clerk {
    private int product = 10;

    //进货
    public synchronized void get() {
        if (product >= 10) {
            System.out.println("产品已满！");
        } else {
            System.out.println(Thread.currentThread().getName() + ":" + ++product);
        }
    }

    //卖货
    public synchronized void sale() {
        if (product <= 0) {
            System.out.println("缺货！");
        } else {
            System.out.println(Thread.currentThread().getName() + ":" + --product);
        }
    }
}

class Producer implements Runnable {
    private Clerk clerk;

    public Producer(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            clerk.get();
        }
    }
}

class Consumer implements Runnable {
    private Clerk clerk;

    public Consumer(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            clerk.sale();
        }
    }
}