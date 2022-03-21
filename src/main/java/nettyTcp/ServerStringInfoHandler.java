package nettyTcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ServerStringInfoHandler extends SimpleChannelInboundHandler<StringInfo> {

    private int count;

    protected ServerStringInfoHandler() {
        super();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, StringInfo s) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + ":" + s.getContent());
        System.out.println("count=" + (++this.count));
    }
}
