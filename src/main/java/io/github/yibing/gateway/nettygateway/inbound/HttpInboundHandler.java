package io.github.yibing.gateway.nettygateway.inbound;

import io.github.yibing.gateway.nettygateway.outbound.httpclient4.HttpOutboundHandler;
import io.github.yibing.gateway.nettygateway.outbound.netty4.NettyOutboundHandler;
import io.github.yibing.gateway.nettygateway.outbound.okhttp.OkhttpOutboundHandler;
import io.github.yibing.gateway.nettygateway.router.RouteTable;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
@Slf4j
@ChannelHandler.Sharable
public class HttpInboundHandler extends ChannelInboundHandlerAdapter {

    private String proxyServer;
    private OkhttpOutboundHandler handler;
    private NettyOutboundHandler nettyHandler;

    @Autowired
    private HttpOutboundHandler httpHandler;

    public HttpInboundHandler() {

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        Lock lock = new ReentrantLock();
        try {
            lock.lock();
            FullHttpRequest fullRequest = (FullHttpRequest) msg;
            if (fullRequest.uri().equals("/favicon.ico")) {
                return;
            }
            httpHandler.handle(fullRequest, ctx);
            lock.unlock();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }
}
