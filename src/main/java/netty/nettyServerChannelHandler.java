package netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.EventExecutor;

import java.net.SocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class nettyServerChannelHandler extends ChannelInboundHandlerAdapter {
    public static  volatile Map<SocketAddress,NioSocketChannel> nioSocketChannels;
    static {
        nioSocketChannels = new HashMap<>();
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        super.handlerAdded(ctx);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        nioSocketChannels.put(ctx.channel().remoteAddress(),(NioSocketChannel)ctx.channel());
        String regimsg = "(" + ctx.channel().remoteAddress() + ")" + "registered!";
        System.out.println(regimsg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String activemsg = "(" + ctx.channel().remoteAddress() + ")"+ "actived!";
        System.out.println(activemsg);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String cmsg = "("+ ctx.channel().remoteAddress() + ")"+":" + ((ByteBuf)msg).toString(CharsetUtil.UTF_8);

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        nioSocketChannels.remove(ctx.channel().remoteAddress());
        cause.printStackTrace();
        String dmsg = ctx.channel().remoteAddress() + "disconnected!";
        System.out.println(dmsg);
        ctx.close();
    }
}
