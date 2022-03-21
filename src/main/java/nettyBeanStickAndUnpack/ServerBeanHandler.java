package nettyBeanStickAndUnpack;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ServerBeanHandler extends SimpleChannelInboundHandler<User> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, User user) throws Exception {
        System.out.println(user.toString());
    }
}
