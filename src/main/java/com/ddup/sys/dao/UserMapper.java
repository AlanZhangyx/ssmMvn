package com.ddup.sys.dao;

import java.util.List;
import java.util.Map;

import com.ddup.sys.model.User;

public interface UserMapper {

    int insert(User record);
    int insertSelective(User record);
    int insertUserRole(Map<String, Object> map);//在关联表sys_user_role中插入关系
    
    int deleteByPrimaryKey(Integer id);
    int deleteByPrimaryKeys(Map<String, Object> map);
    int deleteUserRole(Integer id);

    int updateByPrimaryKeySelective(User record);
    int updateByPrimaryKey(User record);
    
    int countSelectedProperty(User record);
    User selectByPrimaryKey(Integer id);
    User getByUserNamePassword(User record);
    
    @Deprecated//并未实现，需要时自己实现并去掉@
    List<User> listModels(User record);
    List<User> listModels(Map<String,Object> map);
    @Deprecated//并未实现，需要时自己实现并去掉@
    List<Map<String,Object>> listMaps(User record);
    @Deprecated//并未实现，需要时自己实现并去掉@
    List<Map<String,Object>> listMaps(Map<String,Object> map);
    
    Integer listModelsCount(Map<String,Object> map);
}