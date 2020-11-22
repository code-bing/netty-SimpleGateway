package io.github.yibing.gateway.nettygateway.aspect;

import io.github.yibing.gateway.nettygateway.filter.HttpRequestFilter;
import io.github.yibing.gateway.nettygateway.router.RouteTable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class FilterAspect {

    @Autowired
    HttpRequestFilter httpRequestFilter;

    @Pointcut("execution(* io.github.yibing.gateway.nettygateway.outbound.httpclient4.HttpOutboundHandler.handle(..))")
    public void handle(){

    }

    @Before(value = "handle()&&args(fullRequest,ctx)", argNames = "fullRequest,ctx")
    public void before(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx){
        httpRequestFilter.filter(fullRequest,ctx);
    }
}
