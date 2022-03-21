package nettyStringStickAndUnpack;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientStringHandler extends SimpleChannelInboundHandler<String> {
    protected ClientStringHandler() {
        super();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for(int i = 0 ; i < 10000 ; i ++){
            String msg = "this is a message from client";
            System.out.println(msg);
            ctx.channel().writeAndFlush(msg);
        }
    }
}
