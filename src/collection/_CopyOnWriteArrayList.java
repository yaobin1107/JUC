package collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Author: yaobin
 * @Date: 2019/7/26 15:36
 */
public class _CopyOnWriteArrayList {
    public static void main(String[] args) {

        _HelloThread helloThread = new _HelloThread();
        _HiThread hiThread = new _HiThread();

        for (int i = 0; i < 10; i++) {
            //new Thread(helloThread).start();
            new Thread(hiThread).start();
        }
    }
}

class _HelloThread implements Runnable {

    private static List<String> list = Collections.synchronizedList(new ArrayList<String>());

    static {
        list.add("AA");
        list.add("BB");
        list.add("CC");
    }

    @Override
    public void run() {
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
            //使用安全的ArrayList也会发生java.util.ConcurrentModificationException异常
            list.add("AA");
        }
    }
}

/**
 * CopyOnWriteArrayList()
 * CopyOnWriteSet()
 * “写入并复制”
 * 注意：
 * 添加操作多时，效率低，因为每次添加时都会进行复制，开销大
 * 并发迭代操作时可以选择，可以提高效率
 */
class _HiThread implements Runnable {

    private static CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<String>();

    static {
        list.add("AA");
        list.add("BB");
        list.add("CC");
    }

    @Override
    public void run() {
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
            list.add("AA");
        }
    }
}