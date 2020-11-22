package io.github.yibing.gateway.nettygateway.config;

import io.github.yibing.gateway.nettygateway.outbound.httpclient4.NamedThreadFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class TheadPoolUtil {

    @Autowired
    private ThreadPoolConfig config;

    public ThreadPoolExecutor getThreadPool() {
        RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();//.DiscardPolicy();
        // 创建线程池
        ThreadPoolExecutor executor = new ThreadPoolExecutor(config.getCorePoolSize(), config.getMaximumPoolSize(),
                config.getKeepAliveMillisecond(), TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(config.getQueueSize()),
                new NamedThreadFactory("proxyService"), handler);
        return executor;
    }
}
