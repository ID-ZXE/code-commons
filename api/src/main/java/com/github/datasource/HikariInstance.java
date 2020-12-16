package com.github.datasource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author zhanghang
 * @date 2020/12/16 11:18 上午
 * *****************
 * function:
 */
public final class HikariInstance {

    private static volatile DataSource dataSource;

    private HikariInstance() {
    }

    private static void initDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/foobar");
        config.setUsername("root");
        config.setPassword("root");
        // 连接超时: 1秒
        config.addDataSourceProperty("connectionTimeout", "1000");
        // 空闲超时: 60秒
        config.addDataSourceProperty("idleTimeout", "60000");
        // 最大连接数: 10
        config.addDataSourceProperty("maximumPoolSize", "10");
        dataSource = new HikariDataSource(config);
    }

    private static DataSource getDatasourceInstance() {
        if (dataSource == null) {
            synchronized (HikariInstance.class) {
                if (dataSource == null) {
                    initDataSource();
                }
            }
        }
        return dataSource;
    }

    public static Connection getConnection() {
        DataSource instance = getDatasourceInstance();
        try {
            return instance.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
