package NIO;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
//笔记1： 在创建ByteBuffer时，需要通过clear()进行初始化 / 往ByteBuffer中put入数据后，需要执行flip()操作
//笔记2：

public class NIOServer {
    private ServerSocketChannel serverSocketChannel;
    private Selector selector;
    private int port;
    private static final int DEFAULT_PORT = 8888;

    public NIOServer(){
        this.port = DEFAULT_PORT;
    }

    public NIOServer(int port){
        this.port = port;
    }
    //一.服务器这边的初始化：创建一个ServerSocketChannel  和一个Selector
    //在ServerSocketChannel上绑定端口号
    //再把ServerSocketChannel注册到Selector上
    public void init(){
        try{
            this.serverSocketChannel = ServerSocketChannel.open();
            this.serverSocketChannel.configureBlocking(false);
            this.serverSocketChannel.socket().bind(new InetSocketAddress(this.port));
            this.selector = Selector.open();
            this.serverSocketChannel.register(this.selector, SelectionKey.OP_ACCEPT);
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
                    SelectionKey selectionKey = (SelectionKey) iterator.next();
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
        if(selectionKey.isAcceptable()){//一.服务器处理客户端的连接请求
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
            try{
                socketChannel = (SocketChannel) serverSocketChannel.accept();
                if(socketChannel == null){//1.ServerSocketChannel调用Accept获取SocketChannel
                    return;
                }
                socketChannel.configureBlocking(false);
                socketChannel.register(this.selector, selectionKey.OP_READ,ByteBuffer.allocate(1024));
            }catch (Exception e){
                e.printStackTrace();
            }
        }else if(selectionKey.isReadable()){//二.服务端读取客户端发过来的数据/remain to be solved:如何给客户端写入数据
            socketChannel = (SocketChannel) selectionKey.channel();
            ByteBuffer byteBuffer  = (ByteBuffer)selectionKey.attachment();
            //ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            try{
                int len = 0;
                while((len = socketChannel.read(byteBuffer))!= 0){
                    System.out.println("Client:" + new String(byteBuffer.array()));
                    byteBuffer.clear();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }else if(selectionKey.isWritable()){

        }
    }

    public static void main(String[] args) {
        NIOServer server = new NIOServer();
        server.init();
        server.run();
    }
}
