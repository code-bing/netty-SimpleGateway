package io.github.yibing.gateway.nettygateway.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;

public class HttpRequestFilterImpl implements HttpRequestFilter {
    @Override
    public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        String uri = fullRequest.uri();
        System.out.println("filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx)接收到的请求，url:" + uri);
        HttpHeaders headers = fullRequest.headers();
        if (headers == null) {
            headers = new DefaultHttpHeaders();
        }
        headers.add("nio", "wangyibing");
        fullRequest.headers().set(headers);
    }
}
