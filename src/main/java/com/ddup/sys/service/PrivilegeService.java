package com.ddup.sys.service;

import java.util.List;
import java.util.Map;

/**
 * @Description: TODO
 * @author dznzyx
 * @date 2015年8月19日 下午5:11:15
 */
public interface PrivilegeService {
    
    /**1
     * @Title: listPrivilegesByUserId 
     * @Description: 获取用户的菜单
     * @param userId
     * @return
     * @throws
     */
    List<Map<String,Object>> listPrivilegesByUserId(Integer userId);

    /**2
     * @Title: listPrivilegesByRoldId 
     * @Description: 获取所有权限，并checked输入roleId的权限
     * @param roldId
     * @return
     * @throws
     */
    List<Map<String,Object>> listPrivilegesByRoldId(Integer roldId);

}
