package nio;

import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * ①缓冲区(buffer)：在Java NIO中负责数据的存储，缓冲区就是数组，用于存储不同数据类型的数据
 * ②根据数据类型不同（boolean）除外，提供了相应的缓冲区：
 *      ByteBuffer
 *      CharBuffer
 *      ShortBuffer
 *      IntBuffer
 *      LongBuffer
 *      FloatBuffer
 *      DoubleBuffer
 *  上述缓冲区管理方式几乎一致，通过allocate() 获取缓冲区
 * ③缓冲区的两个核心方法：
 *      put()存入数据到缓冲区
 *      get()获取缓存中的数据
 * ④缓冲区中的四个核心属性
 *      capacity：容量，缓冲区中最大存储数据容量，一旦声明不能改变
 *      limit：界限，表示缓冲区中可以操作数据的大小（limit后数据不能进行读写）
 *      position：位置，表示缓冲区中正在操作数据的位置
 *      mark：标记，表示记录当前的 position 的位置，可以通过 reset 恢复到 mark 的位置
 *  0 <= mark <= position <= limit <= capacity
 * ⑤直接缓冲区和非直接缓存区
 *      非直接缓冲区：通过allocate() 方法分配缓冲区，将缓冲区建立在 JVM 的内存中
 *      直接缓冲区：通过allocateDirect() 方法分配直接缓冲区，将缓冲区建立在物理内存中，可以提高效率
 *          直接缓冲区只有 ByteBuffer 支持
 */
public class BufferDemo {
    @Test
    public void test1() {
        String str = "abcde";
        //分配一个指定大小的缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        System.out.println(buffer.position());//0
        System.out.println(buffer.limit());//1024
        System.out.println(buffer.capacity());//1024
        //向缓冲区存入数据
        buffer.put(str.getBytes());
        System.out.println(buffer.position());//5
        System.out.println(buffer.limit());//1024
        System.out.println(buffer.capacity());//1024
        //切换到读数据模式
        buffer.flip();
        System.out.println(buffer.position());//0
        System.out.println(buffer.limit());//5
        System.out.println(buffer.capacity());//1024
        //取出数据
        byte[] strs = new byte[buffer.limit()];
        buffer.get(strs);
        System.out.println(new String(strs, 0, strs.length));
        System.out.println(buffer.position());//5
        System.out.println(buffer.limit());//5
        System.out.println(buffer.capacity());//1024
        //rewind() 可重复读数据
        buffer.rewind();
        System.out.println(buffer.position());//0
        System.out.println(buffer.limit());//5
        System.out.println(buffer.capacity());//1024
        //clear() 清空缓冲区,但是缓冲区中的数据依然存在，只是处于”被遗忘状态“
        buffer.clear();
        System.out.println(buffer.position());//0
        System.out.println(buffer.limit());//1024
        System.out.println(buffer.capacity());//1024
        System.out.println((char) buffer.get());
    }

    @Test
    public void test2() {
        String str = "abcde";
        //分配一个指定大小的缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        buffer.put(str.getBytes());

        buffer.flip();

        byte[] strs = new byte[buffer.limit()];
        buffer.get(strs, 0, 2);
        System.out.println(new String(strs, 0, 2));
        System.out.println(buffer.position());//2

        //mark标记
        buffer.mark();

        buffer.get(strs, 2, 2);
        System.out.println(new String(strs, 2, 2));
        System.out.println(buffer.position());//4

        //reset() 恢复到mark位置
        buffer.reset();
        System.out.println(buffer.position());//2

        //判断缓冲区中是否还有剩余数据
        if (buffer.hasRemaining()) {
            //如果有还有几个
            System.out.println(buffer.remaining());
        }
    }

    @Test
    public void test3() {
        //分配直接缓冲区
        ByteBuffer direct = ByteBuffer.allocateDirect(1024);
        //判断是否是直接缓冲区
        System.out.println(direct.isDirect());
    }
}
