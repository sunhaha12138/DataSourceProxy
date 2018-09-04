package com.syh.datasource.handler;

import com.google.common.collect.Maps;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Map;

public class ConnectionHandler implements InvocationHandler {
    private Object conn;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().equals("prepareStatement")) {
            Map<String, Object> parameterMap = Maps.newHashMap();
            Parameter[] parameters = method.getParameters();
            for (int i = 0; i < parameters.length; i++) {
                parameterMap.put(parameters[i].getName(), args[i]);
            }

            System.out.println("-----print sql and params-----");
            parameterMap.forEach((k, v) -> System.out.println(k + " : " + v));
            PreparedStatement pps = (PreparedStatement) method.invoke(conn, args);
            Statement ppsProxy =
                    (Statement) Proxy.newProxyInstance(
                            ConnectionHandler.class.getClassLoader(),
                            // Statement.class 做参数会导致失败
                            new Class[]{PreparedStatement.class},
                            new StatementHandler(pps));
            return ppsProxy;
        }
        return method.invoke(conn, args);

    }

    public ConnectionHandler() { }
    public ConnectionHandler(Object conn) {
        this.conn = conn;
    }
}
