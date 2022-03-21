package nettyGroupChat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Scanner;
public class ClientStringMsgHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println(s);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Hello " + ctx.channel().localAddress());
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        Scanner scanner = new Scanner(System.in);
                        while(scanner.hasNextLine()){
                            String s = scanner.nextLine();
                            ctx.writeAndFlush(s);
                        }
                    }
                }
        ).start();
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("GoodBye " + ctx.channel().localAddress());
    }
}
