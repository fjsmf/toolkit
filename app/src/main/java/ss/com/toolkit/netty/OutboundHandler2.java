package ss.com.toolkit.netty;

import com.orhanobut.logger.Logger;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;



public class OutboundHandler2 extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        Logger.i("OutboundHandler2.write");
        System.out.println("OutboundHandler2.write"+ ", msg:"+msg);
        // 执行下一个OutboundHandler
        String response = "I am ok 2!";
        ByteBuf encoded = ctx.alloc().buffer(4 * response.length());
        encoded.writeBytes(response.getBytes());
        ctx.write(encoded);
        ctx.flush();
//        super.write(ctx, msg, promise);
    }
}

