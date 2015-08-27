package com.ddup.sys.dao;

import java.util.List;
import java.util.Map;

import com.ddup.sys.model.Role;

public interface RoleMapper {
    
    int insert(Role record);
    int insertSelective(Role record);
    int insertRolePrivilege(List<Map<String, Object>> list);//在关联表sys_role_privilege中插入关系
    
    int deleteByPrimaryKey(Integer id);
    int deleteByPrimaryKeys(Map<String, Object> map);
    int deleteRolePrivilege(Integer id);

    int updateByPrimaryKeySelective(Role record);
    int updateByPrimaryKey(Role record);
    
    int countSelectedProperty(Role record);
    Role selectByPrimaryKey(Integer id);
    
    List<Role> listModels(Role record);
    List<Role> listModels(Map<String,Object> map);
    List<Map<String,Object>> listMaps(Role record);
    List<Map<String,Object>> listMaps(Map<String,Object> map);
}