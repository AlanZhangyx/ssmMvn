package com.ddup.sys.service;

import java.util.List;
import java.util.Map;

import com.ddup.sys.model.Role;

/**
 * 
 * Introduction this Type.
 * 
 * @author ZhangYaxu
 * @date 2015年8月24日
 */
public interface RoleService {
    
    int insert(Role record,Integer... pIds);
    void deleteByPrimaryKeys(Integer... ids);
    int updateByPrimaryKeySelective(Role record,Integer... pIds);
    Role selectByPrimaryKey(Integer id);
    boolean checkUnique(Role record);//唯一返回true
    
    List<Role> listForCRUD(Map<String,Object> map);//专为CRUD列表时服务
    List<Map<String,Object>> listForZtree();//角色列表，给用户管理时ZTREE用，字段少
    List<Map<String,Object>> listRolesByUserId(Integer userId);//用户的角色列表
}