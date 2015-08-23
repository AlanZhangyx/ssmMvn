package com.ddup.sys.service;

import java.util.List;
import java.util.Map;

import com.ddup.sys.model.Privilege;

/**
 * @Description: TODO
 * @author dznzyx
 * @date 2015年8月19日 下午5:11:15
 */
public interface PrivilegeService {
    
    /**1
     * @Title: listPrivilegesByUserId
     * @Description: 获取用户的权限
     * @param userId
     * @return
     * @throws
     */
    List<Map<String,Object>> listPrivilegesByUserId(Integer userId);
    
    /**
     * @Title: listMenusByUserId
     * @Description: 获取用户的权限-菜单
     * @param userId
     * @return
     * @throws
     */
    List<Map<String,Object>> listMenusByUserId(Integer userId);

    /**
     * @Title: listPrivilegesByRoldId 
     * @Description: 获取所有权限，并checked输入roleId的权限
     * @param roldId
     * @return
     * @throws
     */
    List<Map<String,Object>> listPrivilegesByRoldId(Integer roldId);
    
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
    Privilege selectByPrimaryKey(Integer id);
    
    void deleteByPrimaryKeys(Integer... ids);

    int insertSelective(Privilege record);

    int updateByPrimaryKeySelective(Privilege record);

    /**
     * @param record
     * @return 是唯一返回true
     */
    boolean checkUnique(Privilege record);
    
}
