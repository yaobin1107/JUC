package collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class ContainerNotSafe {
    public static void main(String[] args) {
        //List<String> list = new ArrayList<>();不安全
        List<String> list = Collections.synchronizedList(new ArrayList<>());
        List<String> list2 = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }
    }
    /**
     * 1.故障：java.util.ConcurrentModificationException
     * 2.原因：并发争抢修改
     * 3.解决：
     *  3.1：new Vector()
     *  3.2：Collections.synchronizedList(new ArrayList<>())
     *  3.3：new CopyOnWriteArrayList()
     * 4.优化（不犯同样的错）：
     */
}
