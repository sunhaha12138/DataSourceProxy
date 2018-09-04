package com.syh.datasource.service;

import com.syh.datasource.handler.DataSourceHandler;

import javax.sql.DataSource;
import java.lang.reflect.Proxy;

public class DataSourceProxyService {
    public static DataSource getDataSourceProxy(DataSource ds) {
        DataSource dsProxy =
                (DataSource) Proxy.newProxyInstance(
                        DataSourceProxyService.class.getClassLoader(),
                        new Class[] {DataSource.class},
                        new DataSourceHandler(ds));
        return dsProxy;
    }
}
