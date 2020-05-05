package com.github.common;

public interface Node {

    URL getUrl();

    boolean isAvailable();

    void destroy();

}