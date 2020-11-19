package io.github.yibing.gateway.nettygateway.inbound;

import io.github.yibing.gateway.nettygateway.filter.HttpRequestFilter;
import io.github.yibing.gateway.nettygateway.filter.HttpRequestFilterImpl;
import io.github.yibing.gateway.nettygateway.outbound.netty4.NettyOutboundHandler;
import io.github.yibing.gateway.nettygateway.outbound.okhttp.OkhttpOutboundHandler;
import io.github.yibing.gateway.nettygateway.router.RouteTable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpInboundHandler extends ChannelInboundHandlerAdapter {

    private static Logger logger = LoggerFactory.getLogger(HttpInboundHandler.class);
    private String proxyServer;
    private OkhttpOutboundHandler handler;
    private NettyOutboundHandler nettyHandler;
    private HttpRequestFilter filter;

    public HttpInboundHandler() {
//        this.proxyServer = proxyServer;
//        OkHttp客户端请求
//        handler = new OkhttpOutboundHandler(this.proxyServer);
        //netty 作为客户端请求
//        nettyHandler = new NettyOutboundHandler(proxyServer);
        filter = new HttpRequestFilterImpl();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            FullHttpRequest fullRequest = (FullHttpRequest) msg;
            filter.filter(fullRequest, ctx);
            this.proxyServer = RouteTable.getInstance().getTargetUrl(fullRequest.uri());
            nettyHandler = new NettyOutboundHandler(proxyServer);
            nettyHandler.handle(fullRequest, ctx);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }
}
