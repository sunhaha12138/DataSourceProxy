package com.syh.datasource.handler;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.List;
import java.util.Map;

public class StatementHandler implements InvocationHandler {
    private Object pps;
    private List<Object> paramterList = Lists.newArrayList();
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().startsWith("set")) {
            paramterList.add(args[1]);
            return method.invoke(pps, args);
        } else if (method.getName().equals("executeQuery")) {
            // 输出结果列名
            ResultSetMetaData rsmd =
                    (ResultSetMetaData) PreparedStatement.class.
                        getMethod("getMetaData").invoke(pps, null);
            int colCount = rsmd.getColumnCount();
            System.out.println("-----  result  -----");
            for (int i = 1; i < colCount + 1; i++) {
                System.out.print(rsmd.getColumnName(i) + "     ");
            }
            System.out.println("");

            // 打印结果
            ResultSet result = (ResultSet) method.invoke(pps, args);
            while (result.next()) {
                for (int i = 1; i < colCount + 1; i++) {
                    System.out.print(result.getObject(i) + "     ");
                }
                System.out.println("");
            }

            return method.invoke(pps, args);
        } else if (method.getName().startsWith("execute")) {
            if (!CollectionUtils.isEmpty(paramterList)) {
                System.out.println("-----  params  -----");
                paramterList.forEach(v -> System.out.print(v + "     "));
                System.out.println();
                paramterList.clear();
            }
            return method.invoke(pps, args);
        }
        return method.invoke(pps, args);
    }

    public StatementHandler() { }
    public StatementHandler(Object pps) {
        this.pps = pps;
    }
}
