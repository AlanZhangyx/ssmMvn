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
    
    int countSelectedProperty(Privilege record);
    
    List<Privilege> listModels(Privilege record);
    List<Privilege> listModels(Map<String,Object> map);
    List<Map<String,Object>> listMaps(Privilege record);
    List<Map<String,Object>> listMaps(Map<String,Object> map);
}