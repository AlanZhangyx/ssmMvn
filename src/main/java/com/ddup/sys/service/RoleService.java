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
    
    void deleteByPrimaryKeys(Integer... ids);

    int insertSelective(Role record);

    int updateByPrimaryKeySelective(Role record);

    /**
     * @param record
     * @return 是唯一返回true
     */
    boolean checkUnique(Role record);
    
}
