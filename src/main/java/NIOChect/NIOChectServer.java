package NIOChect;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class NIOChectServer {
    private Selector selector;
    private int port;
    private static final int DEFAULT_PORT = 8888;

    public NIOChectServer(){
        this(DEFAULT_PORT);
    }

    public NIOChectServer(int port){
        this.port = port;
    }

    public void init(){
        try{
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(this.port));
            this.selector = Selector.open();
            serverSocketChannel.register(this.selector, SelectionKey.OP_ACCEPT);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void run(){
        try{
            while(true){
                this.selector.select();
                Set<SelectionKey> selectionKeySet = this.selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeySet.iterator();
                while(iterator.hasNext()){
                    SelectionKey selectionKey = (SelectionKey) iterator.next();
                    iterator.remove();
                    if(selectionKey.isAcceptable()){
                        accept(selectionKey);
                    }
                    else if(selectionKey.isReadable()){
                        readMsg(selectionKey);
                    }
                    else if(selectionKey.isWritable()){

                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void accept(SelectionKey selectionKey){
        try{
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
            SocketChannel socketChannel = serverSocketChannel.accept();
            if(socketChannel == null){
                return;
            }
            socketChannel.configureBlocking(false);
            socketChannel.register(this.selector, SelectionKey.OP_READ,ByteBuffer.allocate(1024));
            System.out.println(socketChannel.getRemoteAddress() + "已上线");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void readMsg(SelectionKey selectionKey){
        SocketChannel socketChannel = null;
        try{
            socketChannel = (SocketChannel) selectionKey.channel();
            ByteBuffer recvbuffer = (ByteBuffer) selectionKey.attachment();
            //ByteBuffer recvbuffer = ByteBuffer.allocate(1024);
            int count = 0;
            while((count = socketChannel.read(recvbuffer)) != 0){
                String msg = new String(recvbuffer.array(), 0, count);
                msg = socketChannel.getRemoteAddress() + ":" + msg;
                System.out.println(msg);
                System.out.println("正在进行消息转发......");
                Set<SelectionKey> keys = this.selector.keys();
                for(SelectionKey key : keys){
                    SelectableChannel channel = key.channel();
                    if(channel instanceof SocketChannel){
                        SocketChannel targetchannel = (SocketChannel) channel;
                        targetchannel.write(ByteBuffer.wrap(msg.getBytes()));
                    }
                }
                System.out.println("消息已转发......");
                recvbuffer.clear();
            }
        }catch (IOException e){
            try{
                System.out.println(socketChannel.getRemoteAddress() + "已离线......");
                selectionKey.channel().close();
                socketChannel.close();
            }catch (Exception exception){
                exception.printStackTrace();
            }
            e.printStackTrace();
        }
    }



    public static void  main(String[] args) throws  Exception{
        NIOChectServer nioChectServer = new NIOChectServer();
        nioChectServer.init();
        nioChectServer.run();
    }
}
