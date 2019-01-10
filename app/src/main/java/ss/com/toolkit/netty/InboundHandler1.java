package ss.com.toolkit.netty;


import com.orhanobut.logger.Logger;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;


public class InboundHandler1 extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Logger.i("InboundHandler1.channelRead: ctx :" + ctx);
        System.out.println("InboundHandler1.channelRead: ctx :" + ctx+ ", msg:"+msg);
        // 通知执行下一个InboundHandler
        ctx.fireChannelRead(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        Logger.i("InboundHandler1.channelReadComplete");
        System.out.println("InboundHandler1.channelReadComplete");
        ctx.flush();
    }
}