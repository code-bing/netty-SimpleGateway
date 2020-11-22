package io.github.yibing.gateway.nettygateway.aspect;

import io.github.yibing.gateway.nettygateway.router.RouteTable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class FilterAspect {
    @Pointcut("execution(* io.github.yibing.gateway.nettygateway.outbound.httpclient4.HttpOutboundHandler.handle(..))")
    public void handle(){

    }

    @Before(value = "handle()&&args(fullRequest,ctx)", argNames = "fullRequest,ctx")
    public void filter(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx){
        RouteTable.getInstance().getTargetUrl(fullRequest.uri());
    }
}
