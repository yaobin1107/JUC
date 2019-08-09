package callable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

class MyThread implements Callable<Integer> {


    @Override
    public Integer call() throws Exception {
        System.out.println("Callable Come in!");
        return 1024;
    }
}

public class CallableDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        FutureTask<Integer> futureTask = new FutureTask<>(new MyThread());

        Thread thread = new Thread(futureTask, "A");
        thread.start();

        System.out.println("Callable retValue:" + futureTask.get());//建议放在最后
    }
}
