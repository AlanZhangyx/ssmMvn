package com.ddup.sys.dao;

import java.util.List;
import java.util.Map;

import com.ddup.sys.model.Role;

public interface RoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
    
    List<Role> list(Map<String,Object> map);
    
    List<Role> listSelectedColumns(Map<String,Object> map);
}