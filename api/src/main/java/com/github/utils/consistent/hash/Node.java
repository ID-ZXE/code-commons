package com.github.utils.consistent.hash;

import lombok.Data;

/**
 * @author zhanghang
 * @date 2022/3/23 10:01 下午
 * *****************
 * function:
 */
@Data
public class Node {

    private String name;

    private String host;

    private Integer port;

    public Node(String name, String host, int port) {
        this.name = name;
        this.host = host;
        this.port = port;
    }
}
