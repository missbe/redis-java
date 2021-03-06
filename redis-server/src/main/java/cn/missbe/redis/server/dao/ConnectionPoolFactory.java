package cn.missbe.redis.server.dao;

import cn.missbe.redis.server.App;
import cn.missbe.redis.server.util.PropertiesUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

/**
 *   Description:java_code
 *   mail: love1208tt@foxmail.com
 *   Copyright (c) 2018. missbe
 *   This program is protected by copyright laws.
 *   Program Name:redisjava
 *   @Date:18-8-31 下午5:28
 *   @author lyg
 *   @version 1.0
 *   @Description 数据库连接池工厂
 **/

public class ConnectionPoolFactory {
    private static final ConnectionPool connectionPool;

    static {
        PropertiesUtil.reloadPropes();
        HashMap<String, String> propes = PropertiesUtil.getDbProps();

        String jdbcDriver = propes.get(App.JDBC_DRIVER);
        String dbUrl = propes.get(App.DB_URL);
        String userName = propes.get(App.DB_USERNAME);
        String password = propes.get(App.DB_PASSWORD);

        connectionPool = new ConnectionPool(jdbcDriver, dbUrl, userName, password);
    }


    static Connection getConnection() throws SQLException {
        return connectionPool.getConnection();
    }

    public static void returnConnection(Connection connection){
        connectionPool.returnConnection(connection);
    }

    public static void refreshConnection() throws SQLException {
        connectionPool.refreshConnection();
    }

    public static void closeConnectionPool() throws SQLException {
        connectionPool.closeConnectionPool();;
    }

    public static void setMaxConnections(int maxConnections) {
        connectionPool.setMaxConnections(maxConnections);
    }

    public void setIncrementalConnections(int incrementalConnections) {
        connectionPool.setIncrementalConnections(incrementalConnections);
    }

}
