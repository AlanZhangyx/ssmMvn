package com.ddup.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ddup.sys.dao.RoleMapper;
import com.ddup.sys.model.Role;
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

    @Transactional
    @Override
    public int insert(Role record, Integer... pIds) {
        int effectCount=roleMapper.insertSelective(record);
        if (pIds.length>0) {
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("roleId", record.getId());
            map.put("pIds", pIds);
            roleMapper.insertRolePrivilege(map);
        }
        return effectCount;
    }

    @Override
    public void deleteByPrimaryKeys(Integer... ids) {
        if (ids.length>0) {
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("ids", ids);
            roleMapper.deleteByPrimaryKeys(map);
        }
    }

    @Override
    public int updateByPrimaryKeySelective(Role record, Integer... pIds) {
        int effectCount=roleMapper.updateByPrimaryKeySelective(record);
        if (pIds.length>0) {
            //先清空原记录
            roleMapper.deleteRolePrivilege(record.getId());
            //再插入现在关系
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("roleId", record.getId());
            map.put("pIds", pIds);
            roleMapper.insertRolePrivilege(map);
        }
        return effectCount;
    }

    @Override
    public Role selectByPrimaryKey(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean checkUnique(Role record) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public List<Map<String, Object>> listForCRUD(Map<String, Object> map) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Map<String, Object>> listForZtree() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Map<String, Object>> listRolesByUserId(Integer userId) {
        // TODO Auto-generated method stub
        return null;
    }

}
