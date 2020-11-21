package io.github.yibing.gateway.nettygateway.router;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 轮询算法
 */
public class RoundRibbon implements HttpEndpointRouter {
    // 当前路由的地址编号
    private static Map<String, Integer> routeFlag;
    // 匹配的路由列表总数
    private static Map<String, Integer> routeMount;

    static {
        routeFlag = new HashMap<>();
        routeMount = new HashMap<>();
    }

    static String route(Map<String, List<String>> server, String source) {
        List<String> serverGroups = null;
        Integer gFlag = 0;
        if (routeFlag.get(source) == null) {
            routeFlag.put(source, gFlag);
        } else {
            gFlag = routeFlag.get(source);
        }
        int newFlag = gFlag + 1;
        serverGroups = server.get(source);
        if (newFlag >= serverGroups.size()) {
            routeFlag.put(source, 0);
        } else {
            routeFlag.put(source, newFlag);
        }
        routeMount.put(source, serverGroups.size());
        return serverGroups.get(gFlag);
    }

    @Override
    public String route(List<String> endpoints) {
        return null;
    }
}
