package com.ddup.sys.service;

import java.util.List;
import java.util.Map;

import com.ddup.sys.model.Privilege;

/**
 * @Description: TODO
 * @author zyx
 * @date 2015年8月19日 下午5:11:15
 */
public interface PrivilegeService {
    
    int insertSelective(Privilege record);
    void deleteByPrimaryKeys(Integer... ids);
    int updateByPrimaryKeySelective(Privilege record);
    Privilege selectByPrimaryKey(Integer id);
    boolean checkUnique(Privilege record);//唯一返回true
    
    List<Map<String,Object>> listForCRUD(Map<String,Object> map);//专为CRUD列表时服务
    List<Map<String,Object>> listPrivilegesByUserId(Integer userId);//用户的权限列表
    List<Map<String,Object>> listMenusByUserId(Integer userId);//用户的(权限菜单)权限列表
    Map<String,Object> listPrivilegesByRoleId(Integer roleId);//角色的权限列表，checked角色所属
    List<Map<String,Object>> listForZtree();//权限列表，给ZTREE用，字段少
    List<Map<String,Object>> listIdsAndNames();//只取id和name,减少数据量
    
    
}
