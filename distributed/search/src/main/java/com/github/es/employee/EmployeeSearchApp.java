package com.github.es.employee;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * @author zhanghang
 * @date 2021/2/17 3:17 下午
 * *****************
 * function:
 */
public class EmployeeSearchApp {

    public static void main(String[] args) {
        init();
    }

    public static RestHighLevelClient init() {
        String[] ips = {"127.0.0.1:9200"};
        HttpHost[] httpHosts = new HttpHost[ips.length];
        for (int i = 0; i < ips.length; i++) {
            httpHosts[i] = HttpHost.create(ips[i]);
        }
        RestClientBuilder builder = RestClient.builder(httpHosts);
        return new RestHighLevelClient(builder);
    }

}
