package AIO;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.Future;

public class AIOServer {
    private AsynchronousServerSocketChannel asynchronousServerSocketChannel;
    private int port;
    private static final int DEFAULT_PORT = 8888;

    public AIOServer(){
        this(DEFAULT_PORT);
    }

    public AIOServer(int port){
        this.port = port;
    }

    public void init(){//一.服务端初始化socketChannel并绑定端口
        try{
            this.asynchronousServerSocketChannel = AsynchronousServerSocketChannel.open();
            this.asynchronousServerSocketChannel.bind(new InetSocketAddress(this.port));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void start(){//二.服务端接收客户端的连接并Read客户端发送过来的信息
        Future<AsynchronousSocketChannel> accept;
        while(true){
            accept = this.asynchronousServerSocketChannel.accept();
            try{
                AsynchronousSocketChannel asynchronousSocketChannel = accept.get();
                ByteBuffer recvbuffer = ByteBuffer.allocate(256);
                Future<Integer> readres = asynchronousSocketChannel.read(recvbuffer);
                while(!readres.isDone()){
                    Thread.sleep(2000);
                }
                System.out.println("Client:" + new String(recvbuffer.array(), 0, readres.get()));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        AIOServer aioServer = new AIOServer();
        aioServer.init();
        aioServer.start();
    }
}
