package com.syh.datasource.handler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;

public class DataSourceHandler implements InvocationHandler {
    private Object ds;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().equals("getConnection")) {

            Connection coon = (Connection) method.invoke(ds, args);
            Connection connProxy =
                    (Connection) Proxy.newProxyInstance(
                            DataSourceHandler.class.getClassLoader(),
                            new Class[]{Connection.class},
                            new ConnectionHandler(coon));
            return connProxy;
        } else {
            return method.invoke(ds, args);
        }
    }

    public DataSourceHandler() { }
    public DataSourceHandler(Object ds) {
        this.ds = ds;
    }
}
