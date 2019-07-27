package fork_join;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class ForkJoinPoolDemo {
    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask<Long> task = new ForkJoinPoolSumCalculate(0L, 100000000L);

        Long sum = pool.invoke(task);
        System.out.println(sum);
    }
}

class ForkJoinPoolSumCalculate extends RecursiveTask<Long> {

    private long start;
    private long end;

    private static final long BoundVal = 10000L;//临界值

    public ForkJoinPoolSumCalculate(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        long length = end - start;
        if (length <= BoundVal) {
            long sum = 0L;
            for (long i = start; i <= end; i++) {
                sum += i;
            }
            return sum;
        } else {
            long mid = (start + end) / 2;
            ForkJoinPoolSumCalculate left = new ForkJoinPoolSumCalculate(start, mid);
            left.fork();//进行拆分，同时压入线程队列

            ForkJoinPoolSumCalculate right = new ForkJoinPoolSumCalculate(mid + 1, end);
            right.fork();

            return left.join() + right.join();
        }
    }
}