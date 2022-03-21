package nettyProtoStickAndUnpack;

import POJO.Person.PersonOuter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ServerPersonProtoHandler extends SimpleChannelInboundHandler<PersonOuter.Person> {
    private int count;
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, PersonOuter.Person person) throws Exception {
        System.out.println("id=" + person.getId() + "   " + "name=" + person.getName()+ "     count=" + (++count));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        System.out.println(cause.getMessage());
    }
}
