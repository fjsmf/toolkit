package ss.com.toolkit.netty;


import com.orhanobut.logger.Logger;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class InboundHandler2 extends ChannelInboundHandlerAdapter {

    @Override
    // 读取Client发送的信息，并打印出来
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Logger.i("InboundHandler2.channelRead: ctx :" + ctx);
        System.out.println("InboundHandler2.channelRead: ctx :" + ctx+ ", msg:"+msg);
        ByteBuf result = (ByteBuf) msg;
        byte[] result1 = new byte[result.readableBytes()];
        result.readBytes(result1);
        String resultStr = new String(result1);
        System.out.println("Client said:" + resultStr);
        result.release();

        ctx.write(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        Logger.i("InboundHandler2.channelReadComplete");
        System.out.println("InboundHandler2.channelReadComplete");
        ctx.flush();
    }

}

