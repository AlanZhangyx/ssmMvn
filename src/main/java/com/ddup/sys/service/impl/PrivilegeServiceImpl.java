package com.ddup.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ddup.sys.dao.PrivilegeMapper;
import com.ddup.sys.service.PrivilegeService;

@Service
public class PrivilegeServiceImpl implements PrivilegeService {
    
    /**
     * PrivilegeMapper
     */
    @Resource
    private PrivilegeMapper privilegeMapper;
    
    /**
     * 菜单json为目的的列名
     */
    private static final String MENU_JSON_COLUMNS="id,name,url,parent_id as parentId,icon";

    @Override
    public List<Map<String, Object>> listPrivilegesByUserId(Integer userId) {
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("columns", MENU_JSON_COLUMNS);
        map.put("userId", userId);
        return privilegeMapper.listSelectedColumns(map);
    }
    
    @Override
    public List<Map<String,Object>> listPrivilegesByRoldId(Integer roldId) {
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("columns", MENU_JSON_COLUMNS);
        List<Map<String,Object>> listAll=privilegeMapper.listSelectedColumns(map);//获取所有
        map.put("roldId", roldId);
        List<Map<String,Object>> listSelected=privilegeMapper.listSelectedColumns(map);//获取选中的
        
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

}
