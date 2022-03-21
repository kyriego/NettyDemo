package nettyTcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

public class ClientStringInfoHandler extends SimpleChannelInboundHandler<StringInfo> {
    private int count;
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for(int i = 0 ; i < 1000 ; i++){
            String s = UUID.randomUUID().toString();
            StringInfo stringInfo = new StringInfo(s.length(), s);
            System.out.println(stringInfo.getContent());
            System.out.println("count=" + (++this.count));
            ctx.channel().writeAndFlush(stringInfo);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, StringInfo s) throws Exception {

    }
}
