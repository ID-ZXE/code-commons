package com.github.remote.zookeeper;

import com.github.common.Constants;
import com.github.common.URL;
import com.github.common.extension.Adaptive;
import com.github.common.extension.SPI;

@SPI("zkclient")
public interface ZookeeperTransporter {

    @Adaptive({Constants.CLIENT_KEY, Constants.TRANSPORTER_KEY})
    ZookeeperClient connect(URL url);

}
