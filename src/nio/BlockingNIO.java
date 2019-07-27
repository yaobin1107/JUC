package nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * ①使用NIO完成网络通信的三个核心：
 *      1.通道(Channel)：负责链接
 *          java.nio.channels.Channel 接口：
 *              |--SelectableChannel 抽象类：
 *                  |--SocketChannel
 *                  |--ServerSocketChannel
 *                  |--DatagramChannel
 *
 *                  |--Pipe.SinkChannel
 *                  |--Pipe.SourceChannel
 *
 *      2.缓冲区(Buffer)：负责数据的存取
 *      3.选择器(Selector)：是 SelectableChannel 的多路复用器，用于监控 SelectableChannel 的IO状况
 */
public class BlockingNIO {
    @Test
    public void client() throws IOException {
        //获取通道
        SocketChannel sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));
        FileChannel inChannel = FileChannel.open(Paths.get("1.jpg"), StandardOpenOption.READ);
        //分配指定大小的缓冲器
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //读取本地文件,发送到服务端
        while (inChannel.read(buffer) != -1) {
            buffer.flip();//切换到读取模式
            sChannel.write(buffer);
            buffer.clear();
        }
        //关闭通道
        inChannel.close();
        sChannel.close();
    }

    @Test
    public void server() throws IOException {
        //获取通道
        ServerSocketChannel ssChannel = ServerSocketChannel.open();
        FileChannel outChannel = FileChannel.open(Paths.get("2.jpg"), StandardOpenOption.WRITE, StandardOpenOption.CREATE);
        //绑定链接
        ssChannel.bind(new InetSocketAddress(9898));
        //获取客户端连接的通道
        SocketChannel sChannel = ssChannel.accept();
        //分配指定大小的缓冲器
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //读取客户端数据并保存到本地
        while (sChannel.read(buffer) != -1) {
            buffer.flip();
            outChannel.write(buffer);
            buffer.clear();
        }
        //关闭通道
        sChannel.close();
        outChannel.close();
        ssChannel.close();
    }
}
