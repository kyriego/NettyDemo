package nettyCodec;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ClientLongHandler extends SimpleChannelInboundHandler<Long> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Scanner scanner = new Scanner(System.in);
//                while(scanner.hasNextLong()){
//                    Long l = scanner.nextLong();
//                    ctx.channel().writeAndFlush(l);
//                }
//            }
//        }).start();
        ctx.channel().writeAndFlush(Unpooled.copiedBuffer("abcdabcdabcdabcda".getBytes(StandardCharsets.UTF_8)));
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Long aLong) throws Exception {
        System.out.println("Server" + aLong);
    }
}
