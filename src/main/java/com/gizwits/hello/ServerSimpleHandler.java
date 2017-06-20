package com.gizwits.hello;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Created by feel on 16/5/8.
 * 消息处理 handle
 */
public class ServerSimpleHandler extends SimpleChannelInboundHandler<String> {

    private static final Logger logger = LoggerFactory.getLogger(ServerSimpleHandler.class);

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, String msg) throws Exception {


        logger.info("服务端收到消息.." + msg);

        ChannelFuture future = ctx.writeAndFlush("ServerSimpleHandler 处理.." + new Date().toString() + "\n");


        //  发送消息完毕后 ，关闭和client 的连接
        future.addListener(ChannelFutureListener.CLOSE);

    }
}
