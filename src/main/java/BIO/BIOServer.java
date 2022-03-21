package BIO;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BIOServer {
    private ServerSocket socket;
    private int port;
    private static final int DEFAULT_PORT = 8888;

    public BIOServer(){
        this.port = DEFAULT_PORT;
    }

    public BIOServer(int port){
        this.port = port;
    }

    public void init(){
        try{
            this.socket = new ServerSocket(this.port);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void run(){
        while(true){
            try{
                Socket socket = this.socket.accept();
                if(socket != null){
                    new Thread(() -> {
                        try{
                            byte[] bytes = new byte[4096];
                            InputStream inputStream = (InputStream) socket.getInputStream();
                            while(true){
                                int len ;
                                while((len = inputStream.read(bytes)) !=  -1){
                                    Date date = new Date();
                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
                                    String time  = simpleDateFormat.format(date);
                                    System.out.println(time + " from" + socket.getLocalAddress() + ":" + new String(bytes, 0, len));
                                }
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }).start();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args){
        BIOServer server = new BIOServer();
        server.init();
        server.run();
    }
}
