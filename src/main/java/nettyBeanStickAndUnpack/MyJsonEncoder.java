package nettyBeanStickAndUnpack;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.StandardCharsets;

public class MyJsonEncoder extends MessageToByteEncoder<User> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, User user, ByteBuf byteBuf) throws Exception {
        String s = JSON.toJSONString(user);
        System.out.println(s);
        byteBuf.writeBytes(s.getBytes(StandardCharsets.UTF_8));
    }
}
