package io.github.yibing.gateway.nettygateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "gateway.threadpool")
public class ThreadPoolConfig {
    private int corePoolSize;
    private int maximumPoolSize;
    private int keepAliveMillisecond;
    private int queueSize;

}
