package practice;

/**
 * 判断打印的是one还是two
 */
public class OneOrTwo {
    public static void main(String[] args) {
        Number number1 = new Number();
        Number number2 = new Number();
        new Thread(new Runnable() {
            @Override
            public void run() {
                number1.getOne();
            }
        }, "number1:").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                //number1.getTwo();
                number2.getTwo();
            }
        }, "number2:").start();
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                number.getThree();
            }
        }).start();*/
    }
}

/**
 * 两个普通同步方法，一个number对象，两个线程，标准打印，结果：one two
 * 新增Thread.sleep() 给getOne() ，一个number对象，结果：one two
 * 新增普通方法getThree() 结果：three one two
 * 两个普通同步方法，两个number对象，结果：two one
 * 修改getOne()为静态同步方法，一个number对象，结果：two one
 * 两个静态同步方法，一个number对象，结果：one two
 * 一个静态同步方法，一个非静态同步方法，两个number对象，结果：two one
 * 两个静态同步方法，两个number对象，结果：one two
 * 线程八锁的关键：
 * ①：非静态方法的锁默认为this，静态方法的锁默认为对象的Class实例
 * ②：某一个时刻内，只能有一个线程持有锁，不管几个方法
 */
class Number {
    public static synchronized void getOne() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("one");
    }

    public static synchronized void getTwo() {
        System.out.println("two");
    }

    public void getThree() {
        System.out.println("three");
    }
}