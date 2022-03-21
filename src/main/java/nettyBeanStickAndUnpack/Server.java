package nettyBeanStickAndUnpack;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import nettyStringStickAndUnpack.ServerStringHandler;

import java.nio.charset.StandardCharsets;

public class Server {
    private String host;
    private int port;
    private static final String DEFAULT_HOST = "localhost";
    private static final int DEFAULT_PORT = 8005;

    public Server(){
        this(DEFAULT_HOST, DEFAULT_PORT);
    }

    public Server(String host, int port){
        this.host = host;
        this.port = port;
    }

    public void run() throws  Exception{
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup,workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(new LengthFieldBasedFrameDecoder(64, 0, 8, 0, 8));
                        pipeline.addLast(new MyJsonDecoder());
                        pipeline.addLast(new ServerBeanHandler());
                    }
                });
        ChannelFuture channelFuture = serverBootstrap.bind(this.host, this.port).sync();
        channelFuture.channel().closeFuture().sync();
    }

    public static void main(String[] args) {
        Server server = new Server();
        try{
            server.run();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
