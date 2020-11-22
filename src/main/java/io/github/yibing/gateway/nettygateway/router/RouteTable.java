package io.github.yibing.gateway.nettygateway.router;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Map;

/**
 * 路由规则类
 */
@Slf4j
public class RouteTable {

    // 路由列表
    private static List<Map<String, String>> route;
    // 后端服务地址列表
    private static Map<String, List<String>> server;
    // 目标地址
    public static String targetUrl;

    private RouteTable() {
    }

    private static class ROUTE {
        public static RouteTable instance = new RouteTable();
    }

    public static RouteTable getInstance() {
        return ROUTE.instance;
    }

    public void initRoute() {
        Gson gson = new Gson();
        JsonReader reader = null;
        try {
            reader = new JsonReader(new FileReader("D:\\gitHub项目\\netty-SimpleGateway\\src\\main\\resources\\route.json"));
            Map<String, Object> config = gson.fromJson(reader, Map.class);
            server = (Map<String, List<String>>) config.get("server");
            route = (List<Map<String, String>>) config.get("route");
            log.info("serverList :" + server + "\n" + "routeList :" + route);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getTargetUrl(String uri) {
        String target = "";
        try {
            for (Map<String, String> table : route) {
                if (uri.indexOf(table.get("source")) == 0) {
                    target = RoundRibbon.route(server, table.get("target"));
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        targetUrl = target + uri;
        return targetUrl;
    }

    public static void main(String[] args) {

    }
}
