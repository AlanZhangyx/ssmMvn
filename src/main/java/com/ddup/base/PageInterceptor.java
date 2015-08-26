package com.ddup.base;

import java.sql.Connection;
import java.util.Properties;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;

/**
 * @Description: 数据库分页拦截器
 * @author zyx
 * @date 2015年8月19日 上午11:59:47
 * 
 * 使用了第三方的mybatis分页插件，自定义的先不用了
 */
@Intercepts(@Signature(type = StatementHandler.class, method="prepare", args = { Connection.class }))
public class PageInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object plugin(Object target) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setProperties(Properties properties) {
        // TODO Auto-generated method stub

    }

}
