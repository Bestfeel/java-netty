package com.gizwits.hello;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimeServerHandler extends ChannelHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(TimeServerHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        String body = (String) msg;

        logger.info("服务端收到客户端的请求" + body);


        // 根据客户端的请求 做出判断
        String dateStr = body.equalsIgnoreCase("request") ? DateTime.now().toString() : "error  date time  request";

        //  因为 我们使用 StringDecoder  解码器

        //  数据 写入的时候 需要  ByteBuf  类型.  数据读取的时候 ,直接 强转 string 类型即可
        //  如果我们 有string  解码器 ,及不需要ByteBuf  类型. 直接 回写string 类型即可
        ByteBuf byteBuf = Unpooled.copiedBuffer((dateStr + System.getProperty("line.separator")).getBytes());

        //  有了string  编码器 , 直接回写  string 类型即可
        // ctx.writeAndFlush(byteBuf);

        ChannelFuture f = ctx.writeAndFlush(dateStr + System.getProperty("line.separator"));

        //
        f.addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {

                //assert f == future;

                logger.info("发送完毕！，收到监听，关闭系");
                ctx.close();
            }
        });

        //
        f.addListener(ChannelFutureListener.CLOSE);

        ReferenceCountUtil.release(msg);

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

        logger.info("....服务端  channelReadComplete ......");

        ctx.flush();

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {


        logger.info("....服务端  exceptionCaught ......");

        ctx.close();
    }
}


