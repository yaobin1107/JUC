package callable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 创建线程第三种方式：实现Callable接口
 * 相较于实现Runnable接口方式，方法可以有返回值，并且可以抛出异常
 * 执行Callable的方式，需要FutureTask实现类的支持，用于接收运算结果
 * FutureTask时Future接口的实现类
 */
public class _Callable {
    public static void main(String[] args) {
        ThreadDemo threadDemo = new ThreadDemo();
        //执行Callable的方式，需要FutureTask实现类的支持，用于接收运算结果
        FutureTask<Integer> task = new FutureTask<>(threadDemo);
        new Thread(task).start();
        try {
            //接收线程运算结果
            Integer sum = task.get();//FutureTask也可用于闭锁
            System.out.println(sum);
            System.out.println("--------------------------");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    static class ThreadDemo implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            int sum = 0;
            for (int i = 0; i < 10000; i++) {
                sum += i;
            }
            return sum;
        }
    }
}