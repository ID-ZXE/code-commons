package com.github.datasource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author zhanghang
 * @date 2020/12/16 11:29 上午
 * *****************
 * function:
 */
public class InitMan {

    public static void main(String[] args) {
        init();
    }

    public static void init() {
        Connection connection = HikariInstance.getConnection();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.executeUpdate("insert into user() values()");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                // do nothing
            }
        }
    }

}
