package nettyCodec;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.List;
import java.util.Scanner;

public class ServerLongHandler extends SimpleChannelInboundHandler<Long> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Scanner scanner = new Scanner(System.in);
                while(scanner.hasNextLong()){
                    long l = scanner.nextLong();
                    ctx.channel().writeAndFlush(l);
                    System.out.println("l=" + l + "     client=" + ctx.channel().remoteAddress());
                }
            }
        }).start();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Long aLong) throws Exception {
        System.out.println("client:" + aLong);
    }
}
