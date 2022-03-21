package NIO;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class NioClientS {
    private SocketChannel socketChannel;
    private String host;
    private int port;
    private static final String DEFAULT_HOST = "localhost";
    private static final int DEFAULT_PORT = 8888;

    public NioClientS(){
        this.host = DEFAULT_HOST;
        this.port = DEFAULT_PORT;
    }

    public NioClientS(String host ,int port){
        this.host = host;
        this.port = port;
    }

    public void init(){
        try{
            this.socketChannel = SocketChannel.open();
            this.socketChannel.configureBlocking(false);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void connect() {
        try{
            this.socketChannel.connect(new InetSocketAddress(this.host, this.port));
            if (this.socketChannel.isConnectionPending()){
                this.socketChannel.finishConnect();
            }
            System.out.println("connteced successfully!");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void sendMessage(){
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNextLine()){
            String next = scanner.nextLine();
            System.out.println("client:" + next);
            try{
                this.socketChannel.write(ByteBuffer.wrap(next.getBytes()));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }



    public static void main(String[] args) {
        NioClientS client = new NioClientS();
        client.init();
        client.connect();
        client.sendMessage();
    }
}
