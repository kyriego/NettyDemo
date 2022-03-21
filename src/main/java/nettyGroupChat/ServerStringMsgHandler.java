package nettyGroupChat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.Iterator;

public class ServerStringMsgHandler extends SimpleChannelInboundHandler<String> {
    private static ChannelGroup channels;
    static {
       channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    }

    private static void distributeMsgToPublic(ChannelHandlerContext ctx, String msg){
        Iterator<Channel> iterator = channels.iterator();
        while(iterator.hasNext()){
            SocketChannel next = (SocketChannel)iterator.next();
            if(next != ctx.channel()){
                next.writeAndFlush(msg);
            }else{
                next.writeAndFlush("【你】" + msg);
            }
        }

    }
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception {
        String msg = "(" + ctx.channel().remoteAddress() + ")" + ":" + s + "\n";
        //distributeMsgToPublic(ctx, msg);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("StringHandler" + "("+ctx.channel().remoteAddress() +")" + "Registered");
    }


//    @Override
//    public void channelActive(ChannelHandlerContext ctx) throws Exception {//tips:先进来再告诉所有人
////        channels.add(ctx.channel());
////        String msg = "(" + ctx.channel().remoteAddress() + ")" + ":" + "已上线\n";
////        System.out.println(msg);
////        distributeMsgToPublic(ctx,msg);
////        System.out.println("StringHandler" + "("+ctx.channel().remoteAddress() +")" + "Active");
//    }

//    @Override
//    public void channelInactive(ChannelHandlerContext ctx) throws Exception {//tips:先告诉所有人再离开
////        channels.add(ctx.channel());
////        String msg = "(" + ctx.channel().remoteAddress() + ")" + ":" + "已下线\n";
////        System.out.println(msg);
////        distributeMsgToPublic(ctx,msg);
//    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
