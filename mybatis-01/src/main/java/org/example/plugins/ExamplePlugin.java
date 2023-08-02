package org.example.plugins;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
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
        /**
         * 使用拦截器为每一个sql语句尾部添加limit分页功能
         */
        // TODO: 2023/8/2 拿到原始 sql 语句，这就需要反射的功能了
        /* 要知道sql语句是哪个参数，所有sql语句都在mapperStatement中被封装成一个类了，而这个类已经通过上述注解放进了invocation中 */
        Object[] args = invocation.getArgs();
        MappedStatement ms =  (MappedStatement)args[0];
        /* 拿到了原始sql */
        BoundSql boundSql = ms.getBoundSql(args[1]);
        String sql = boundSql.getSql();

        // TODO: 2023/8/2 开始改造 sql 语句
        String newSql = sql + " limit 1, 10;";
        /* 难点来了，ms没有set方法，那该如何将修改后的sql语句替换掉原来的sql语句呢？答案是再构建一个ms */
        Executor executor = (Executor) invocation.getTarget();

        SqlSource sqlSource = o -> new BoundSql(ms.getConfiguration(), newSql, null, args[1]);
        MappedStatement newMappedStatement = new MappedStatement.Builder(
                ms.getConfiguration(),
                ms.getId() + "byPage",
                sqlSource,
                ms.getSqlCommandType())
                .cache(ms.getCache())
                .resultMaps(ms.getResultMaps())
                .build();

        List<Object> queryResult = executor.query(newMappedStatement, args[1], (RowBounds) args[2], (ResultHandler) args[3]);

        return queryResult;
    }

    @Override
    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
