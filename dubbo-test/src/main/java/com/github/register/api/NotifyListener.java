package com.github.register.api;


import com.github.common.URL;

import java.util.List;

public interface NotifyListener {

    void notify(List<URL> urls);

}