package com.ddup.sys.dao.impl;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.ddup.sys.dao.RoleMapper;
import com.ddup.sys.model.Role;

/**
 * Introduction this Type.
 * 
 * @author ZhangYaxu
 * @date 2015年8月18日
 */
@Repository
public class RoleDaoImpl extends SqlSessionDaoSupport implements RoleMapper {

    /**
     * 命名空间
     */
    private static final String NAMESPACE="com.ddup.sys.model.Role.";
    
    @Override
    public int deleteByPrimaryKey(Integer id) {
        return getSqlSession().delete(NAMESPACE+"deleteByPrimaryKey", id);
    }

    @Override
    public int insert(Role record) {
        return getSqlSession().insert(NAMESPACE+"insert", record);
    }

    @Override
    public int insertSelective(Role record) {
        return getSqlSession().insert(NAMESPACE+"insertSelective", record);
    }

    @Override
    public Role selectByPrimaryKey(Integer id) {
        return getSqlSession().selectOne(NAMESPACE+"selectByPrimaryKey", id);
    }

    @Override
    public int updateByPrimaryKeySelective(Role record) {
        return getSqlSession().update(NAMESPACE+"updateByPrimaryKeySelective", record);
    }

    @Override
    public int updateByPrimaryKey(Role record) {
        return getSqlSession().update(NAMESPACE+"updateByPrimaryKey", record);
    }

}
