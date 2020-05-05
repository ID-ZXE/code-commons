package com.github.register.api;


import com.github.common.URL;
import com.github.common.extension.Adaptive;
import com.github.common.extension.SPI;

@SPI("dubbo")
public interface RegistryFactory {

    @Adaptive({"protocol"})
    Registry getRegistry(URL url);

}