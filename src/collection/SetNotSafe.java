package collection;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;

public class SetNotSafe {
    public static void main(String[] args) {
        //HashSet<Object> set = new HashSet<>();不安全
        Set<Object> set = Collections.synchronizedSet(new HashSet<>());
        Set<Object> set2 = new CopyOnWriteArraySet<>();
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                set.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(set);
            }, String.valueOf(i)).start();
        }
    }
}
