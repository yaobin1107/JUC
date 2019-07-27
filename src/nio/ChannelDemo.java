package nio;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.Set;

/**
 * ①通道（channel）：用于源节点与目标节点的连接
 * 在Java NIO中负责缓冲区中的数据传输，Channel本身不存储数据，因此需要配合缓冲区进行传输
 * ②通道的主要实现类：
 *  java.nio.channels.Channel 接口：
 *      |--FileChannel
 *      |--SocketChannel
 *      |--ServerSocketChannel
 *      |--DatagramChannel（UDP）
 * ③获取通道
 *      1.Java 针对支持通道的类提供了getChannel() 方法
 *          本地IO：
 *              FileInputStream/FileOutputStream
 *              RandomAccessFile
 *          网络IO：
 *              Socket
 *              ServerSocket
 *              DatagramSocket
 *      2.在JDK 1.7 中的NIO.2 针对各个通道提供了静态方法 open()
 *      3.在JDK 1.7 中的NIO.2 的Files 工具类的 newByteChannel()
 * ④通道之间的数据传输
 *      transferFrom()
 *      transferTo()
 * ⑤分散(Scatter)与聚集(Gather)
 *      分散读取（Scattering Reads）：将通道中的数据分散到多个缓冲区中
 *      聚集写入（Gathering Write）：将多个缓存区中的数据聚集写入到通道中
 * ⑥字符集：Charset
 *      编码：字符串 -> 字节数组
 *      解码：字节数组 -> 字符串
 */
public class ChannelDemo {
    @Test
    public void test1() throws IOException {
        //利用通道完成文件的复制
        FileInputStream fis = new FileInputStream("1.jpg");
        FileOutputStream fos = new FileOutputStream("2.jpg");
        //获取通道
        FileChannel inChannel = fis.getChannel();
        FileChannel outChannel = fos.getChannel();
        //分配缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //将通道中的数据存入缓冲区
        while (inChannel.read(buffer) != -1) {
            buffer.flip();//切换成读取模式
            //将缓冲区的数据写入通道中
            outChannel.write(buffer);
            buffer.clear();//清空
        }
        outChannel.close();
        inChannel.close();
        fis.close();
        fos.close();
    }

    /**
     * 直接缓冲区完成文件复制
     * @throws IOException
     */
    @Test
    public void test2() throws IOException {
        //使用直接缓冲区完成文件的复制（内存映射文件）
        FileChannel inChannel = FileChannel.open(Paths.get("1.jpg"), StandardOpenOption.READ);

        //StandardOpenOption.CREATE_NEW 不存在则创建，存在则报错
        FileChannel outChannel = FileChannel.open(Paths.get("2.jpg"), StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE_NEW);

        //内存映射文件
        MappedByteBuffer inMappedBuf = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
        MappedByteBuffer outMappedBuf = outChannel.map(FileChannel.MapMode.READ_WRITE, 0, inChannel.size());

        //直接对缓冲区进行数据的读写操作
        byte[] dst = new byte[inMappedBuf.limit()];
        inMappedBuf.get(dst);
        outMappedBuf.put(dst);

        inChannel.close();
        outChannel.close();
    }

    /**
     * 通道间的传输
     * @throws IOException
     */
    @Test
    public void test3() throws IOException {
        //使用直接缓冲区完成文件的复制（内存映射文件）
        FileChannel inChannel = FileChannel.open(Paths.get("1.jpg"), StandardOpenOption.READ);
        //StandardOpenOption.CREATE_NEW 不存在则创建，存在则报错
        FileChannel outChannel = FileChannel.open(Paths.get("2.jpg"), StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE_NEW);

        //inChannel.transferTo(0, inChannel.size(), outChannel);
        outChannel.transferFrom(inChannel, 0, inChannel.size());

        inChannel.close();
        outChannel.close();
    }

    /**
     * 分散读取及聚集写入
     * @throws IOException
     */
    @Test
    public void test4() throws IOException {
        RandomAccessFile raf1 = new RandomAccessFile("1.txt", "rw");
        //获取通道
        FileChannel channel = raf1.getChannel();
        //分配指定大小的缓冲区
        ByteBuffer buffer1 = ByteBuffer.allocate(200);
        ByteBuffer buffer2 = ByteBuffer.allocate(1024);
        //分散读取
        ByteBuffer[] buffers = {buffer1, buffer2};
        channel.read(buffers);
        for (ByteBuffer buffer : buffers) {
            buffer.flip();
        }
        System.out.println(new String(buffers[0].array(), 0, buffers[0].limit()));
        System.out.println("---------------------------------");
        System.out.println(new String(buffers[1].array(), 0, buffers[1].limit()));
        //聚集写入
        RandomAccessFile raf2 = new RandomAccessFile("2.txt", "rw");
        //获取通道
        FileChannel channel1 = raf2.getChannel();
        channel1.write(buffers);
    }

    /**
     * 字符集
     */
    @Test
    public void test5() {
        Map<String, Charset> map = Charset.availableCharsets();
        Set<Map.Entry<String, Charset>> set = map.entrySet();
        for (Map.Entry<String, Charset> entry : set) {
            System.out.println(entry.getKey() + "=" + entry.getValue());
        }
    }

    @Test
    public void test6() throws CharacterCodingException {
        Charset charset = Charset.forName("GBK");
        //获取编码器
        CharsetEncoder ce = charset.newEncoder();
        //获取解码器
        CharsetDecoder cd = charset.newDecoder();
        CharBuffer buffer = CharBuffer.allocate(1024);
        buffer.put("姚斌你好啊！");
        buffer.flip();
        //编码
        ByteBuffer encodedBuf = ce.encode(buffer);
        for (int i = 0; i < encodedBuf.limit(); i++) {
            System.out.println(encodedBuf.get());
        }
        //解码
        encodedBuf.flip();
        CharBuffer decodedBuf = cd.decode(encodedBuf);
        System.out.println(decodedBuf.toString());
    }
}
