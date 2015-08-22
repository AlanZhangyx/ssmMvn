package com.ddup.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ddup.sys.dao.PrivilegeMapper;
import com.ddup.sys.model.Privilege;
import com.ddup.sys.service.PrivilegeService;

@Service
public class PrivilegeServiceImpl implements PrivilegeService {
    
    /**
     * PrivilegeMapper
     */
    @Resource
    private PrivilegeMapper privilegeMapper;
    
    /*** 定义需要动态查询的列的key和value ****/
    /** key **/
    //列名原始：是放在第一个select部分的
    private static final String COLUMNS_KEY1="columns";
    //用来group消除重复
    private static final String COLUMNS_KEY2="columns_2";
    
    
    /** value **/
    //有userId的情况下，groupby所用列
    private static final String COLUMNS1="id,name,action_url,parent_id,icon";
    //多一个父Id
    private static final String COLUMNS2="s.id,s.name,s.action_url as actionUrl,s.parent_id as parentId,rp.name as parentName,s.is_menu as isMenu,s.icon,s.description,s.create_time as createTime,s.update_time as updateTime";
    //菜单json为目的的列名
    private static final String COLUMNS3="id,name,action_url as actionUrl,parent_id as parentId,icon";

    //获取用户的权限
    @Override
    public List<Map<String, Object>> listPrivilegesByUserId(Integer userId) {
        Map<String,Object> map=new HashMap<String,Object>();
        map.put(COLUMNS_KEY1, COLUMNS3);
        map.put(COLUMNS_KEY2, COLUMNS1);
        map.put("userId", userId);
        return privilegeMapper.listSelective(map);
    }
    
    //获取用户的权限-菜单
    @Override
    public List<Map<String, Object>> listMenusByUserId(Integer userId) {
        Map<String,Object> map=new HashMap<String,Object>();
        map.put(COLUMNS_KEY1, COLUMNS3);
        map.put(COLUMNS_KEY2, COLUMNS1);
        map.put("userId", userId);
        map.put("extraMenu", 1);
        return privilegeMapper.listSelective(map);
    }
    
    //获取角色的权限
    @Override
    public List<Map<String,Object>> listPrivilegesByRoldId(Integer roldId) {
        Map<String,Object> map=new HashMap<String,Object>();
        map.put(COLUMNS_KEY1, COLUMNS3);
        List<Map<String,Object>> listAll=privilegeMapper.listSelective(map);//获取所有
        map.put("roldId", roldId);
        List<Map<String,Object>> listSelected=privilegeMapper.listSelective(map);//获取选中的
        
        //将选中的标识到全部中
        for (int i = 0; i < listSelected.size(); i++) {
            Map<String,Object> checked=listSelected.get(i);
            for (int j = 0; j < listAll.size(); j++) {
                Map<String,Object> item=listAll.get(j);
                if((Integer)checked.get("id")==(Integer)item.get("id")){
                    item.put("checked", true);
                    listAll.set(j, item);
                    break;
                }
            }
        }
        
        return listAll;
    }

    @Override
    public List<Map<String,Object>> listForCRUD(Map<String,Object> map) {
        //获取选中的
        map.put(COLUMNS_KEY1, COLUMNS2);
        map.put("extraPName", 1);//需要parentName
        return privilegeMapper.listSelective(map);
    }

    @Override
    public Privilege selectByPrimaryKey(Integer id) {
        return privilegeMapper.selectByPrimaryKey(id);
    }

    @Transactional
    @Override
    public void deleteByPrimaryKeys(Integer... ids) {
        for (Integer id : ids) {
            privilegeMapper.deleteByPrimaryKey(id);
        }
    }

    @Override
    public int insertSelective(Privilege record) {
        return privilegeMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKeySelective(Privilege record) {
        return privilegeMapper.updateByPrimaryKeySelective(record);
    }

}
