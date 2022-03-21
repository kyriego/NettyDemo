package nettyGroupChat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

import java.util.concurrent.ConcurrentLinkedDeque;

public class Client {
    private String host;
    private int port;
    private static final String DEFAULT_HOST = "localhost";
    private static final int DEFAULT_PORT = 8001;

    public Client(){
        this(DEFAULT_HOST,DEFAULT_PORT);
    }

    public Client(String host, int port){
        this.host = host;
        this.port = port;
    }

    public void run() throws  Exception{
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventExecutors)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel channel) throws Exception {
                        ChannelPipeline pipeline = channel.pipeline();
                        pipeline//.addLast(new StringEncoder(CharsetUtil.UTF_8))
                                //.addLast(new StringDecoder(CharsetUtil.UTF_8))
                                //.addLast(new ClientStringMsgHandler())
                                .addLast("MyProtobufEncoder",new ProtobufEncoder())
                                .addLast("MyProtobufDecoder",new ProtobufDecoder(POJO.Student.StudentOuter.Student.getDefaultInstance()))
                                .addLast(new ClientProtoStudentHandler());
                    }
                });
        ChannelFuture channelFuture = bootstrap.connect(this.host, this.port).sync();
        channelFuture.channel().closeFuture().sync();
    }

    public static void main(String[] args) throws  Exception{
        Client client = new Client();
        client.run();
    }
}
