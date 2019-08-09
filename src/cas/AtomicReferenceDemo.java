package cas;

import java.util.concurrent.atomic.AtomicReference;

class User {
    String name;
    int age;

    public User(String name, int age) {
        this.age = age;
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}

public class AtomicReferenceDemo {
    public static void main(String[] args) {

        User zs = new User("zs", 14);
        User ls = new User("ls", 25);

        AtomicReference<User> userAtomicReference = new AtomicReference<>();
        userAtomicReference.set(zs);

        System.out.println(userAtomicReference.compareAndSet(zs, ls) + "\t" + userAtomicReference.get().toString());
        System.out.println(userAtomicReference.compareAndSet(zs, ls) + "\t" + userAtomicReference.get().toString());
    }
}
