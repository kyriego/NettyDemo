package nettyStringStickAndUnpack;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.internal.ObjectUtil;

import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class MyStringEncoder extends MessageToByteEncoder<String> {
    private String delimiter;

    public MyStringEncoder(String delimiter) {
        this.delimiter = delimiter;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, String s, ByteBuf byteBuf) throws Exception {
        s = s + delimiter;
        byteBuf.writeBytes(s.getBytes(StandardCharsets.UTF_8));
    }
}

//public class StringEncoder extends MessageToMessageEncoder<CharSequence> {
//    private final Charset charset;
//
//    public StringEncoder() {
//        this(Charset.defaultCharset());
//    }
//
//    public StringEncoder(Charset charset) {
//        this.charset = (Charset) ObjectUtil.checkNotNull(charset, "charset");
//    }
//
//    protected void encode(ChannelHandlerContext ctx, CharSequence msg, List<Object> out) throws Exception {
//        if (msg.length() != 0) {
//            out.add(ByteBufUtil.encodeString(ctx.alloc(), CharBuffer.wrap(msg), this.charset));
//        }
//    }
//}
