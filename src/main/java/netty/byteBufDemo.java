package netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class byteBufDemo {
    public static void main(String[] args) {
        ByteBuf byteBuf = Unpooled.copiedBuffer("this is a message from client".getBytes());
        while(byteBuf.isReadable()){
            System.out.print((char)byteBuf.readByte());
        }
    }
}
