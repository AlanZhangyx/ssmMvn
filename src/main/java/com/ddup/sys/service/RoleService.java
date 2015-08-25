package com.ddup.sys.service;

import java.util.List;
import java.util.Map;

import com.ddup.sys.model.Privilege;
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
    Privilege selectByPrimaryKey(Integer id);
    boolean checkUnique(Privilege record);//唯一返回true
    
    List<Map<String,Object>> listForCRUD(Map<String,Object> map);//专为CRUD列表时服务
    List<Map<String,Object>> listPrivilegesByUserId(Integer userId);//用户的权限列表
    List<Map<String,Object>> listMenusByUserId(Integer userId);//用户的(权限菜单)权限列表
    List<Map<String,Object>> listPrivilegesByRoldId(Integer roldId);//角色的权限列表，checked角色所属
    List<Map<String,Object>> listForZtree();//权限列表，给ZTREE用，字段少
    
    /**1
     * @Title: listRolesByUserId
     * @Description: 获取用户的角色
     * @param userId
     * @return
     * @throws
     */
    List<Map<String,Object>> listRolesByUserId(Integer userId);
    
    /**
     * @Title: list 
     * @Description: 
     * @return
     * @throws
     */
    List<Map<String,Object>> listForCRUD(Map<String,Object> map);
    
    /**
     * @Title: selectByPrimaryKey
     * @Description 
     * @param id
     * @return
     */
    Role selectByPrimaryKey(Integer id);
    
    /**
     * @param record
     * @return 是唯一返回true
     */
    boolean checkUnique(Role record);
    
}
