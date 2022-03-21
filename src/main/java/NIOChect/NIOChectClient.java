package NIOChect;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class NIOChectClient {
    private SocketChannel socketChannel;

    private String host;
    private int port;
    private static final String DEFAULT_HOST = "127.0.0.1";
    private static final int DEFAULT_PORT = 8888;

    public NIOChectClient(){
        this(DEFAULT_HOST, DEFAULT_PORT);
    }

    public NIOChectClient(String host, int port){
        this.host = host;
        this.port = port;
    }

    public void init() {
        try{
            this.socketChannel = SocketChannel.open();
            this.socketChannel.configureBlocking(false);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void connect(){
        try{
            this.socketChannel.configureBlocking(true);
            this.socketChannel.connect(new InetSocketAddress(this.host, this.port));//如何处理连接异常
            this.socketChannel.configureBlocking(false);
            System.out.println("connected successfully!");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void sendMsg(String msg){
        try{
            ByteBuffer sendbuffer = ByteBuffer.wrap(msg.getBytes());
            this.socketChannel.write(sendbuffer);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void recvMsg(){
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

    public static void main(String[] args) throws  Exception {
        NIOChectClient nioChectClient = new NIOChectClient();
        nioChectClient.init();
        nioChectClient.connect();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    nioChectClient.recvMsg();
                }
            }
        }).start();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()){
            String s = scanner.nextLine();
            nioChectClient.sendMsg(s);
        }
    }
}
