package com.github.common;

import junit.framework.TestCase;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

/**
 * @author hangs.zhang
 * @date 2020/05/05 21:20
 * *****************
 * function:
 */
public class URLTest extends TestCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Test
    public void testValueOf() {
        URL url = URL.valueOf("dubbo://192.168.1.3:20880/com.github.service.UserService?anyhost=true&application=provider&deprecated=false&dubbo=2.0.2&dynamic=true&generic=false&interface=com.github.service.UserService&methods=getUserByName,checkUserByName&pid=19631&release=2.7.6&revision=1.0.0&side=provider&timestamp=1588683541855&version=1.0.0");
        LOGGER.info("getAbsolutePath:{}", url.getAbsolutePath());
        LOGGER.info("getAddress:{}", url.getAddress());
        LOGGER.info("getAuthority:{}", url.getAuthority());
        LOGGER.info("getBackupAddress:{}", url.getBackupAddress());
        LOGGER.info("getHost:{}", url.getHost());
        LOGGER.info("getIp:{}", url.getIp());
        LOGGER.info("getServiceInterface:{}", url.getServiceInterface());
    }
}