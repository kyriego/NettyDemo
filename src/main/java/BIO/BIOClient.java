package BIO;

import java.io.OutputStream;
import java.net.Socket;

public class BIOClient {
    private Socket socket;
    private String host;
    private int port;
    private static final String DEFAULT_HOST = "127.0.0.2";
    private static final int DEFAULT_INT = 8888;

    public BIOClient(){
        this.host = DEFAULT_HOST;
        this.port = DEFAULT_INT;
    }

    public BIOClient(String host, int port){
        this.host = host;
        this.port = port;
    }

    public void connect(){
        try{
            this.socket = new Socket(this.host, this.port);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void run(){
        try {
            if (this.socket == null) {
                this.socket = new Socket(this.host, this.port);
            }
            OutputStream outputStream = this.socket.getOutputStream();
            while (true) {
                outputStream.write("this is a message from client".getBytes());
                Thread.sleep(2000);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        BIOClient client = new BIOClient();
        client.connect();
        client.run();
    }
}
