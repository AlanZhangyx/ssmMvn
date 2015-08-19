package com.ddup.sys.dao;

import java.util.List;
import java.util.Map;

import com.ddup.sys.model.Privilege;

public interface PrivilegeMapper {
    
    int deleteByPrimaryKey(Integer id);

    int insert(Privilege record);

    int insertSelective(Privilege record);

    Privilege selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Privilege record);

    int updateByPrimaryKey(Privilege record);
    
    List<Privilege> list(Map<String,Object> map);
    
    List<Map<String,Object>> listSelectedColumns(Map<String,Object> map);
}