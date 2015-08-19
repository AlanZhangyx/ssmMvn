package com.ddup.sys.dao;

import java.util.List;
import java.util.Map;

import com.ddup.sys.model.User;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    List<User> list(Map<String,Object> map);
    
    List<User> listSelectedColumns(Map<String,Object> map);
    
    User getByUserNamePassword(User record);
}