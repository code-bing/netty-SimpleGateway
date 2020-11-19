package io.github.yibing.gateway.nettygateway.outbound.netty4;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

public class NettyOutboundHandler {

    private String proxyServer;
    private NettyOutboundClient client;

    public NettyOutboundHandler(String proxyServer) {
        this.proxyServer = proxyServer;
        client = new NettyOutboundClient(proxyServer);
    }

    public void handle(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        // 调用后端服务
        client.connect(ctx,fullRequest);
    }
}