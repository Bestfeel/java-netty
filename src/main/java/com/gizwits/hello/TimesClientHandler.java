package com.gizwits.hello;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimesClientHandler extends ChannelHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(TimesClientHandler.class);

    private final ByteBuf firstMessage;

    public TimesClientHandler() {

        byte[] req = ("request" + System.getProperty("line.separator")).getBytes();


        firstMessage = Unpooled.buffer(req.length);

        firstMessage.writeBytes(req);


        logger.info("...客户端发起请求...");

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {


        logger.error("----exceptionCaught---");
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {


        logger.info("----channelActive---");

        //  ctx.writeAndFlush(firstMessage);
        //  有了string  编码器, 直接写string 类型即可
        ctx.writeAndFlush("request" + System.getProperty("line.separator"));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        logger.info("----channelReadComplete---");

        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {


        try {
            //   使用  StringDecoder  直接 强转即可
            String body = (String) msg;


            logger.info("客户端 收到服务端的时间..." + body);


            //  客户端 收到请求,就断开连接
            // ctx.close();
        } finally {
            ReferenceCountUtil.release(msg);
        }


    }
}
