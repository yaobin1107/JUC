package nio;

import org.junit.Test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

public class PipeDemo {
    @Test
    public void test1() throws IOException {
        //1.获取管道
        Pipe pipe = Pipe.open();
        //2.将缓冲区中的数据写入管道
        Pipe.SinkChannel sinkChannel = pipe.sink();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put("通过单向管道发送数据！".getBytes());
        buffer.flip();
        sinkChannel.write(buffer);

        //3.读取缓冲区中的数据
        Pipe.SourceChannel sourceChannel = pipe.source();
        buffer.flip();

        int len = sourceChannel.read(buffer);
        System.out.println(new String(buffer.array(),0,len));

        sourceChannel.close();
        sinkChannel.close();
    }
}
