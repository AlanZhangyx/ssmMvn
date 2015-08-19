package com.ddup.base;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;

/**
 * @Description: 引入SqlSession
 * mybatis-spring-1.2.0之后取消在SqlSessionDaoSupport中
 * 自动注入SqlSession或SqlSessionFactory的操作，所以我们需要自己来
 * @author dznzyx
 * @date 2015年8月19日 下午7:42:25
 */
public class BaseDao {
    @Resource
    private SqlSession sqlSession;
    
    /**
     * Users should use this method to get a SqlSession to call its statement methods
     * This is SqlSession is managed by spring. Users should not commit/rollback/close it
     * because it will be automatically done.
     *
     * @return Spring managed thread safe SqlSession
     */
    public SqlSession getSqlSession() {
      return this.sqlSession;
    }
}
