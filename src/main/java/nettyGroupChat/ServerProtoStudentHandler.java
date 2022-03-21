package nettyGroupChat;

import POJO.Student.StudentOuter;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;

public class ServerProtoStudentHandler extends SimpleChannelInboundHandler<StudentOuter.Student> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("ProtoHandler" + "("+ctx.channel().remoteAddress() +")" + "Active");
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, StudentOuter.Student student) throws Exception {
        System.out.println(student.toString());
        ctx.writeAndFlush(Unpooled.copiedBuffer("copy that!".getBytes(StandardCharsets.UTF_8)));
    }
}
