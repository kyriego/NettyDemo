package nettyCodec;

import POJO.Student.StudentOuter;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

public class Server {
    private String host;
    private int port;
    private static final String DEFAULT_HOST = "localhost";
    private static final int DEFAULT_PORT = 8001;

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
                        pipeline.addLast(new ByteToLongDecoder());//inbound:把字节流数据转换为Long对应的List<Object>
                        pipeline.addLast(new ServerLongHandler());//inbound:把上面的List<Object>进行Read打印
                        pipeline.addLast(new LongToByteEncoder());
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
