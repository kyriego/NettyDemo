package AIO;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.Future;

public class AIOClient {
    private AsynchronousSocketChannel clientChannel;
    private String host;
    private int port;
    private static final String DEFAULT_HOST = "127.0.0.1";
    private static final int DEFAULT_PORT = 8888;

    public AIOClient(){
        this(DEFAULT_HOST, DEFAULT_PORT);
    }

    public AIOClient(String host, int port){
        this.host = host;
        this.port = port;
    }

    public void connect(){
        try{
            this.clientChannel = AsynchronousSocketChannel.open();
            Future<Void> connect = this.clientChannel.connect(new InetSocketAddress(this.host, this.port));
            while(!connect.isDone()){
                Thread.sleep(2000);
            }
            System.out.println("connection has been created!");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void sendMsg(String msg){
        ByteBuffer sendbuffer = ByteBuffer.allocate(512);
        sendbuffer.clear();
        sendbuffer.put(msg.getBytes());
        sendbuffer.flip();
        this.clientChannel.write(sendbuffer);
    }

    public static void main(String[] args) {
        for(int i = 0; i < 10; i++){
            AIOClient aioClient = new AIOClient();
            aioClient.connect();
            aioClient.sendMsg("this is a message from client" + i);
        }
    }
}
