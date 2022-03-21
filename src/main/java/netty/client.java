package netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

public class client {
    public static void main(String[] args) throws Exception{
        EventLoopGroup eventExecutors = new NioEventLoopGroup();//一.创建client的EvenlLoopGroup
        Bootstrap bootstrap = new Bootstrap(); //二.配置group，channel类型（客户端就配置NioSocketChannel）以及对应参数（不懂）
        bootstrap.group(eventExecutors)
                .channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<Channel>() {
                    @Override//三.为注册在client的workGroup上的channel配置pipeline即handler们
                    //ChannelInbound/OutboundHnadler  👉 各种生命周期执行的handler
                    protected void initChannel(Channel channel) throws Exception {
                        ChannelPipeline pipeline = channel.pipeline();
                        pipeline.addLast(new nettyClientChannelHandler());
                    }
                });
        ChannelFuture cf = bootstrap.connect(new InetSocketAddress("localhost", 8000)).sync();//四.异步建立和关闭连接
        cf.channel().closeFuture().sync();
    }
}
