package nettyBeanStickAndUnpack;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientBeanHandler  extends SimpleChannelInboundHandler<User> {
    protected ClientBeanHandler() {
        super();
    }
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, User user) throws Exception {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for(int i = 0 ; i < 10000 ; i ++){
            User user = new User(i + 1, "abcd", "usa");
            ctx.channel().writeAndFlush(user);
        }
    }
}
