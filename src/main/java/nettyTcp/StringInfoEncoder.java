package nettyTcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.StandardCharsets;

public class StringInfoEncoder extends MessageToByteEncoder<StringInfo> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, StringInfo stringInfo, ByteBuf byteBuf) throws Exception {
        byteBuf.writeInt(stringInfo.getLen());
        byteBuf.writeBytes(stringInfo.getContent().getBytes(StandardCharsets.UTF_8));
    }
}
