package nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Date;
import java.util.Iterator;

public class NonBlockingNIO {
    @Test
    public void client() throws IOException {
        //获取通道
        SocketChannel sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));
        //切换为非阻塞模式
        sChannel.configureBlocking(false);
        //分配指代那个大小的缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //发送数据给服务端
        buffer.put(new Date().toString().getBytes());
        buffer.flip();
        sChannel.write(buffer);
        buffer.clear();
        //关
        sChannel.close();
    }

    @Test
    public void server() throws IOException {
        //获取通道
        ServerSocketChannel ssChannel = ServerSocketChannel.open();
        //设置为非阻塞
        ssChannel.configureBlocking(false);
        //绑定连接
        ssChannel.bind(new InetSocketAddress(9898));
        //获取选择器
        Selector selector = Selector.open();
        //将通道注册到选择器,并且指定“监听接收事件”
        ssChannel.register(selector, SelectionKey.OP_ACCEPT);
        //轮询式的获取选择器上已经“准备就绪”的事件
        while (selector.select() > 0) {
            //获取当前选择器中所有注册的“选择键（已就绪的监听事件）”
            Iterator<SelectionKey> selectionKeyIterator = selector.selectedKeys().iterator();
            while (selectionKeyIterator.hasNext()) {
                //获取准备“就绪”的事件
                SelectionKey key = selectionKeyIterator.next();
                //判断具体是什么事件准备就绪
                if (key.isAcceptable()) {
                    //若”接受就绪“，获取客户端连接
                    SocketChannel sChannel = ssChannel.accept();
                    //切换为非阻塞模式
                    sChannel.configureBlocking(false);
                    //将该通道注册到选择器
                    sChannel.register(selector, SelectionKey.OP_READ);
                } else if (key.isReadable()) {
                    //获取当前选择器上“读就绪状态”的通道
                    SocketChannel isReadableChannel = (SocketChannel) key.channel();
                    //读数据
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    int len = 0;
                    while ((len = isReadableChannel.read(buffer)) != -1) {
                        buffer.flip();
                        System.out.println(new String(buffer.array(), 0, len));
                        buffer.clear();
                    }
                }
                //取消选择键 SelectionKey
                selectionKeyIterator.remove();
            }
        }
    }
}
