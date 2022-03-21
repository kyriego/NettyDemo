package NIOChect;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NIOClient02 {
    private Selector selector;
    private volatile SocketChannel socketChannel;
    private SelectionKey selectionKey;

    private ExecutorService executorService;

    private String host;
    private int port;
    private static final String DEFAULT_HOST = "127.0.0.1";
    private static final int DEFAULT_PORT = 8888;

    public NIOClient02(){
        this(DEFAULT_HOST, DEFAULT_PORT);
    }

    public NIOClient02(String host, int port){
        this.host = host;
        this.port = port;
        executorService = Executors.newSingleThreadExecutor();
    }

    public void sendMsgToServer(String msg){
        while(!socketChannel.isConnected()){
        }
        try{
            ByteBuffer sendbuffer = ByteBuffer.wrap(msg.getBytes());
            socketChannel.write(sendbuffer);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void init(){
        try{
            selector = Selector.open();
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            selectionKey = socketChannel.register(selector, 0);
            boolean connect = socketChannel.connect(new InetSocketAddress(host, port));
            if(connect == false){
                selectionKey.interestOps(SelectionKey.OP_CONNECT);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void run(){
        try{
            while(true){
                selector.select();
                Set<SelectionKey> selectionKeySet = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeySet.iterator();
                while(iterator.hasNext()){
                    SelectionKey selectionKey = iterator.next();
                    int ops = selectionKey.readyOps();
                    iterator.remove();
                    if((ops & SelectionKey.OP_CONNECT) != 0){
                        handleconnect(selectionKey);
                    }else if((ops & SelectionKey.OP_READ) != 0){
                        recvMsg(selectionKey);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void handleconnect(SelectionKey selectionKey){
        try{
            SocketChannel socketChannel = (SocketChannel)selectionKey.channel();
            if(socketChannel.isConnectionPending()){
                socketChannel.finishConnect();
            }
            selectionKey.interestOps(SelectionKey.OP_READ);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void recvMsg(SelectionKey selectionKey){
        try{
            ByteBuffer recvbuffer = ByteBuffer.allocate(1024);
            int count = 0;
            while((count = this.socketChannel.read(recvbuffer)) != 0){
                System.out.println(new String(recvbuffer.array(), 0, count));
                recvbuffer.clear();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        NIOClient02 nioClient02 = new NIOClient02();
        nioClient02.init();
        nioClient02.executorService.execute(nioClient02 ::run);

        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()){
            String msg = scanner.nextLine();
            nioClient02.sendMsgToServer(msg);
        }
    }
}
