package com.ddup.sys.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ddup.base.BaseDao;
import com.ddup.sys.dao.UserMapper;
import com.ddup.sys.model.User;

/**
 * Introduction this Type.
 * 
 * @author ZhangYaxu
 * @date 2015年8月18日
 */
@Repository
public class UserDaoImpl extends BaseDao implements UserMapper {

    /**
     * 命名空间
     */
    private static final String NAMESPACE="com.ddup.sys.model.User.";
    
    @Override
    public int deleteByPrimaryKey(Integer id) {
        return getSqlSession().delete(NAMESPACE+"deleteByPrimaryKey", id);
    }

    @Override
    public int insert(User record) {
        return getSqlSession().insert(NAMESPACE+"insert", record);
    }

    @Override
    public int insertSelective(User record) {
        return getSqlSession().insert(NAMESPACE+"insertSelective", record);
    }

    @Override
    public User selectByPrimaryKey(Integer id) {
        return getSqlSession().selectOne(NAMESPACE+"selectByPrimaryKey", id);
    }

    @Override
    public int updateByPrimaryKeySelective(User record) {
        return getSqlSession().update(NAMESPACE+"updateByPrimaryKeySelective", record);
    }

    @Override
    public int updateByPrimaryKey(User record) {
        return getSqlSession().update(NAMESPACE+"updateByPrimaryKey", record);
    }

    @Override
    public List<User> list(Map<String, Object> map) {
        return getSqlSession().selectList(NAMESPACE+"list", map);
    }

    @Override
    public List<User> listSelectedColumns(Map<String, Object> map) {
        return getSqlSession().selectList(NAMESPACE+"listSelectedColumns", map);
    }

    @Override
    public User getByUserNamePassword(User record) {
        return getSqlSession().selectOne(NAMESPACE+"getByUserNamePassword", record);
    }
}
