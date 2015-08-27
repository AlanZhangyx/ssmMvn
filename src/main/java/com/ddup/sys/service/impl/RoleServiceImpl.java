package com.ddup.sys.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ddup.sys.dao.RoleMapper;
import com.ddup.sys.model.Role;
import com.ddup.sys.service.PrivilegeService;
import com.ddup.sys.service.RoleService;

/**
 * @Description: TODO
 * @author zyx
 * @date 2015年8月26日 下午3:34:16
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private PrivilegeService privilegeService;
    
    /*** 定义需要动态查询的列的key和value ****/
    /** key **/
    //列名原始：是放在第一个select部分的
    private static final String COLUMNS_KEY1="columns_select";
    
    
    /** value **/
    private static final String COLUMNS1="id,name";
    private static final String COLUMNS2="id,name,description,create_time as createTime,update_time as updateTime";
    
    /*********************** PROCESS ***********************/
    
    /**
     * 增加
     */
    @Transactional
    @Override
    public int insert(Role record, Integer... pIds) {
        int effectCount=roleMapper.insertSelective(record);
        if (pIds.length>0) {
            Integer id=record.getId();
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("roleId", id);
            map.put("pIds", pIds);
            roleMapper.insertRolePrivilege(map);
        }
        return effectCount;
    }

    /**
     * 删除
     */
    @Override
    public void deleteByPrimaryKeys(Integer... ids) {
        if (ids.length>0) {
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("ids", ids);
            roleMapper.deleteByPrimaryKeys(map);
        }
    }

    /**
     * 修改
     */
    @Transactional
    @Override
    public int updateByPrimaryKeySelective(Role record, Integer... pIds) {
        int effectCount=roleMapper.updateByPrimaryKeySelective(record);
        if (pIds.length>0) {
            //先清空原记录
            roleMapper.deleteRolePrivilege(record.getId());
            //再插入现在关系
            Integer id=record.getId();
            
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("roleId", id);
            map.put("pIds", pIds);
            roleMapper.insertRolePrivilege(map);
        }
        return effectCount;
    }

    /**
     * 单个查询
     */
    @Override
    public Role selectByPrimaryKey(Integer id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    /**
     * 唯一性查询
     */
    @Override
    public boolean checkUnique(Role record) {
        return roleMapper.countSelectedProperty(record)<1;
    }
    
    /**************************各种列表********************/

    /**
     * 专为CRUD列表时服务
     */
    @Override
    public List<Map<String,Object>> listForCRUD(Map<String, Object> map) {
        map.put(COLUMNS_KEY1, COLUMNS2);
        //下面这三个查询，可以考虑缓存问题来提高效率
        List<Map<String,Object>> roleList=roleMapper.listMaps(map);//获取所有角色列表
        List<Map<String, Object>> privilegeList=privilegeService.listIdsAndNames();//所有权限
        List<Map<String, Object>> rpList=roleMapper.listRPMapsByMap(map);//所有角色和权限的关联关系
        
        
        for (int i = 0; i < roleList.size(); i++) {
            //用角色Id在关联表中找出对应记录,并记录其id值为list
            Map<String, Object> item=roleList.get(i);
            Integer roleId=(Integer)item.get("id");
            List<Integer> rpPIdList=new ArrayList<Integer>();
            for (int j = 0; j < rpList.size(); j++) {
                Map<String, Object> rpItem=rpList.get(j);
                Integer rpItemRoleId=(Integer)rpItem.get("roleId");
                if (roleId.equals(rpItemRoleId)) {
                    rpPIdList.add((Integer)rpItem.get("privilegeId"));
                }
            }
            //将rpPIdList
        }
    }

    /**
     * 角色列表，给用户管理时ZTREE用，字段少
     */
    @Override
    public List<Map<String, Object>> listForZtree() {
        Map<String,Object> map=new HashMap<String,Object>();
        map.put(COLUMNS_KEY1, COLUMNS1);
        return roleMapper.listMaps(map);
    }

    /**
     * 用户的角色列表
     */
    @Override
    public List<Map<String, Object>> listRolesByUserId(Integer userId) {
        Map<String,Object> map=new HashMap<String,Object>();
        map.put(COLUMNS_KEY1, COLUMNS1);
        map.put("userId", userId);
        return roleMapper.listMaps(map);
    }

}
