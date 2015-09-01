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
    
    
    /*** 增加 ***/
    
    @Override
    public int insert(User record) {
        return getSqlSession().insert(NAMESPACE+"insert", record);
    }
    @Override
    public int insertSelective(User record) {
        return getSqlSession().insert(NAMESPACE+"insertSelective", record);
    }
    @Override
    public int insertUserRole(Map<String, Object> map) {
        return getSqlSession().insert(NAMESPACE+"insertUserRole", map);
    }
    
    
    /*** 删除 ***/
    
    @Override
    public int deleteByPrimaryKey(Integer id) {
        return getSqlSession().delete(NAMESPACE+"deleteByPrimaryKey", id);
    }
    @Override
    public int deleteByPrimaryKeys(Map<String, Object> map) {
        return getSqlSession().delete(NAMESPACE+"deleteByPrimaryKeys", map);
    }
    @Override
    public int deleteUserRole(Integer id) {
        return getSqlSession().delete(NAMESPACE+"deleteUserRole", id);
    }
    
    
    /*** 修改 ***/
    
    @Override
    public int updateByPrimaryKeySelective(User record) {
        return getSqlSession().update(NAMESPACE+"updateByPrimaryKeySelective", record);
    }
    
    /*** 单个查询 ***/
    
    @Override
    public User getByUserNamePassword(User record) {
        return getSqlSession().selectOne(NAMESPACE+"getByUserNamePassword", record);
    }
    @Override
    public User selectByPrimaryKey(Integer id) {
        return getSqlSession().selectOne(NAMESPACE+"selectByPrimaryKey", id);
    }
    @Override
    public int countSelectedProperty(User record) {
        return getSqlSession().selectOne(NAMESPACE+"countSelectedProperty", record);
    }
    @Override
    public Integer listModelsCount(Map<String, Object> map) {
        return getSqlSession().selectOne(NAMESPACE+"listModelsByMap_count", map);
    }

    
    /*** 列表查询 ***/
    
    @Override//没有实现，自己写
    public List<User> listModels(User record) {
        return getSqlSession().selectList(NAMESPACE+"listModelsByModel", record);
    }
    @Override
    public List<User> listModels(Map<String, Object> map) {
        return getSqlSession().selectList(NAMESPACE+"listModelsByMap", map);
    }
    @Override//没有实现，自己写
    public List<Map<String, Object>> listMaps(User record) {
        return getSqlSession().selectList(NAMESPACE+"listMapsByModel", record);
    }
    @Override//没有实现，自己写
    public List<Map<String, Object>> listMaps(Map<String, Object> map) {
        return getSqlSession().selectList(NAMESPACE+"listMapsByMap", map);
    }

}