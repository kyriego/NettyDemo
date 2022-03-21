package nettyGroupChat;

import POJO.Student.StudentOuter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientProtoStudentHandler extends SimpleChannelInboundHandler<StudentOuter.Student> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        StudentOuter.Student.Builder builder = StudentOuter.Student.newBuilder();
        builder.setName("kyriego");
        builder.setAge(28);
        StudentOuter.Student student = builder.build();
        ctx.writeAndFlush(student);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, StudentOuter.Student student) throws Exception {

    }
}
