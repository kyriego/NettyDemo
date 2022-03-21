package nettyProtoStickAndUnpack;

import POJO.Person.PersonOuter;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class ClientPersonProtoHandler  extends SimpleChannelInboundHandler<PersonOuter.Person> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for(int i = 0 ; i < 1000; i++){
            PersonOuter.Person.Builder builder = PersonOuter.Person.newBuilder();
            builder.setName(UUID.randomUUID().toString());
            builder.setId(i + 1);
            PersonOuter.Person person = builder.build();
            ctx.writeAndFlush(person);
            System.out.println(person.toString());
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, PersonOuter.Person person) throws Exception {

    }
}
