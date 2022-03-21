package NIO;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class NIOClient {
    private SocketChannel socketChannel;
    private Selector selector;
    private String host;
    private int port;
    private static final String DEFAULT_HOST = "localhost";
    private static final int DEFAULT_PORT = 8888;

    public NIOClient(){
        this.host = DEFAULT_HOST;
        this.port = DEFAULT_PORT;
    }

    public NIOClient(String host ,int port){
        this.host = host;
        this.port = port;
    }
    //一.客户端初始化一个SocketChannel 和 Selector
    //，将SocketChannel连接服务器
    // 再把SocketChannel注册到Selector上
    public void connect(){
        try{
            System.out.println("connecting");
            this.socketChannel = SocketChannel.open();
            this.socketChannel.configureBlocking(false);
            this.selector = Selector.open();
            this.socketChannel.connect(new InetSocketAddress(this.host, this.port));
            this.socketChannel.register(this.selector, SelectionKey.OP_CONNECT);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void run(){
        while(true){
            try{
                this.selector.select();
                Set<SelectionKey> selectionKeySet = this.selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeySet.iterator();
                while(iterator.hasNext()){
                    SelectionKey selectionKey = iterator.next();
                    iterator.remove();
                    handle(selectionKey);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void handle(SelectionKey selectionKey){
        SocketChannel socketChannel;
        if(selectionKey.isConnectable()){//一.服务器处理客户端的连接请求
            try{
                socketChannel =(SocketChannel) selectionKey.channel();
                if(socketChannel.isConnectionPending()){//1.获取服务器SocketChannel并完成连接过程
                    socketChannel.finishConnect() ;
                }
                socketChannel.configureBlocking(false);//2.将服务器的SocketChannel注册到客户端中，注册Read事件
                socketChannel.register(this.selector, SelectionKey.OP_READ);

                ByteBuffer sendBuffer = ByteBuffer.allocate(1024);
                sendBuffer.clear();
                sendBuffer.put((new Date().toString()).getBytes());
                sendBuffer.flip();
                socketChannel.write(sendBuffer);
            }catch (Exception e){
                e.printStackTrace();
            }
        }else if(selectionKey.isReadable()){//二.服务端读取客户端发过来的数据/remain to be solved:如何给客户端写入数据
            socketChannel = (SocketChannel) selectionKey.channel();
            ByteBuffer recvBuffer = ByteBuffer.allocate(4096);
            recvBuffer.clear();
            int count = 0;
            try{
                while((count = socketChannel.read(recvBuffer)) != 0 ){
                    System.out.println("Server:" + new String(recvBuffer.array(), 0, count));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }else if(selectionKey.isWritable()){

        }
    }

    public static void main(String[] args) {
        NIOClient nioClient = new NIOClient();
        nioClient.connect();
        nioClient.run();
    }
}
