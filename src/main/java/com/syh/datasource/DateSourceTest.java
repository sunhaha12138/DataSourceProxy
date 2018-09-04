package com.syh.datasource;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.syh.datasource.service.DataSourceProxyService;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DateSourceTest {
    private static String user = "root";
    private static String pwd = "000000";
    private static String url = "jdbc:mysql://localhost:3306/test";

    private static String selectSQL = "select * from people;";
    private static String insertSQL = "insert people (name, age) value (?, ?)";

    public static void main(String[] args) throws Throwable{
        DataSource ds = getDataSource();

        DataSource dsProxy = DataSourceProxyService.getDataSourceProxy(ds);
        Connection connProxy = dsProxy.getConnection();
        PreparedStatement psProxy = connProxy.prepareStatement(selectSQL);
        ResultSet resultSet = psProxy.executeQuery();

        PreparedStatement psProxy2 = connProxy.prepareStatement(insertSQL);
        psProxy2.setNString(1, "ll--");
        psProxy2.setInt(2, 21);
        int successNum = psProxy2.executeUpdate();
        System.out.println("successNum: " + successNum);

        ResultSet resultSet2 = psProxy.executeQuery();
    }

    public static ComboPooledDataSource getDataSource() {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        try {
            dataSource.setDriverClass("com.mysql.jdbc.Driver");
            dataSource.setJdbcUrl(url);
            dataSource.setUser(user);
            dataSource.setPassword(pwd);
            dataSource.setMaxPoolSize(20);
            dataSource.setMinPoolSize(5);
            dataSource.setInitialPoolSize(5);
            //设置连接池的缓存Statement的最大数
            dataSource.setMaxStatements(100);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        return dataSource;
    }
}
