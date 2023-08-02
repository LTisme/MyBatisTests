package org.example.plugins;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.Properties;

/**
 * @Date: 2023/8/2
 * @Author: Administrator
 * @ClassName: ExamplePlugin
 * @Description: comment here
 */

@Intercepts({@Signature(
        type= Executor.class,
        method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class ExamplePlugin implements Interceptor {
    private Properties properties = new Properties();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // implement pre processing if need
        System.out.println("插件拦截之前");
        Object returnObject = invocation.proceed();
        // implement post processing if need
        System.out.println("插件拦截之后");
        // 这个属性是在配置文件中配置的
        Object someProperty = properties.get("someProperty");
        System.out.println("someProperty = " + someProperty);
        return returnObject;
    }

    @Override
    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
