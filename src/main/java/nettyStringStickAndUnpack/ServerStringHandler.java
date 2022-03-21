package nettyStringStickAndUnpack;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ServerStringHandler extends SimpleChannelInboundHandler<String> {
    private int count = 0;
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println("client:" + s + "   " + "count=" + (++count));
    }
}
