package queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 阻塞队列：
 *  当队列是空的，从队列取元素的操作将被阻塞
 *  当队列是满的，往队列添元素的操作将被阻塞
 */
public class BlockingQueueDemo {
    public static void main(String[] args) {

        /**=======================第一组 add/remove==============================**/
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
        System.out.println(blockingQueue.add("a"));
        System.out.println(blockingQueue.add("b"));
        System.out.println(blockingQueue.add("c"));
        //java.lang.IllegalStateException: Queue full
        //System.out.println(blockingQueue.add("d"));队列已满异常

        System.out.println(blockingQueue.element());//队首元素

        blockingQueue.remove();//a
        blockingQueue.remove();//b
        blockingQueue.remove();//c
        //java.util.NoSuchElementException
        //blockingQueue.remove();没有此元素异常

        System.out.println();
        System.out.println();
        System.out.println();

        /**=======================第二组 offer/poll==============================**/

        System.out.println(blockingQueue.offer("a"));
        System.out.println(blockingQueue.offer("b"));
        System.out.println(blockingQueue.offer("c"));
        System.out.println(blockingQueue.offer("d"));//返回false

        System.out.println(blockingQueue.peek());//返回队首元素

        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());//返回空

    }
}
