package netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.Set;

public class server {
    public static void main(String[] args) throws  Exception{
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(boss,worker)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG,128)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        ChannelPipeline pipeline = channel.pipeline();
                        //pipeline.addLast(new HttpServerCodec());
                        //pipeline.addLast("httpAggregator",new HttpObjectAggregator(512*1024));
                        //pipeline.addLast(new nettyServerHttpHandler());
                        pipeline.addLast(new nettyServerChannelHandler());
                    }
                });
        ChannelFuture cf = serverBootstrap.bind(new InetSocketAddress("localhost", 8000)).sync();
        cf.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()){
                    System.out.println("bind port success!");
                }else{
                    System.out.println("bind port failed!");
                }
            }
        });
        cf.channel().closeFuture().sync();
    }
}
