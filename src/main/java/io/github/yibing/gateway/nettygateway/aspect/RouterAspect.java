package io.github.yibing.gateway.nettygateway.aspect;

import io.github.yibing.gateway.nettygateway.router.RouteTable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class RouterAspect {
    @Pointcut("execution(* io.github.yibing.gateway.nettygateway.outbound.httpclient4.HttpOutboundHandler.handle(..))")
    public void handle() {

    }

    @Before(value = "handle()&&args(fullRequest,ctx)", argNames = "fullRequest,ctx")
    public void filter(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx) {
        String targetUrl = RouteTable.getInstance().getTargetUrl(fullRequest.uri());
        log.info("路由到目标地址：" + targetUrl);
    }
}
