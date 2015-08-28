package com.ddup.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ddup.sys.dao.UserMapper;
import com.ddup.sys.model.User;
import com.ddup.sys.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    
    /**
     * UserMapper
     */
    @Resource
    private UserMapper userMapper;
    
    /*** 定义需要动态查询的列的key和value ****/
    /** key **/
    //列名原始：是放在第一个select部分的
    private static final String COLUMNS_KEY1="columns_select";
    
    /** value **/
    private static final String COLUMNS1="id,name";
    
    /*********************** PROCESS ***********************/
    
    @Override
    public User getByUserNamePassword(User record) {
        return userMapper.getByUserNamePassword(record);
    }
    
    /**
     * 增加
     */
    @Transactional
    @Override
    public int insert(User record, Integer... rIds) {
        int effectCount=userMapper.insertSelective(record);
        if (rIds.length>0) {
            Integer id=record.getId();
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("userId", id);
            map.put("rIds", rIds);
            userMapper.insertUserRole(map);
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
            userMapper.deleteByPrimaryKeys(map);
        }
    }

    /**
     * 修改
     */
    @Transactional
    @Override
    public int updateByPrimaryKeySelective(User record, Integer... rIds) {
        int effectCount=userMapper.updateByPrimaryKeySelective(record);
        if (rIds.length>0) {
            //先清空原记录
            userMapper.deleteUserRole(record.getId());
            //再插入现在关系
            Integer id=record.getId();
            
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("userId", id);
            map.put("rIds", rIds);
            userMapper.insertUserRole(map);
        }
        return effectCount;
    }

    /**
     * 单个查询
     */
    @Override
    public User selectByPrimaryKey(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    /**
     * 唯一性查询
     */
    @Override
    public boolean checkUnique(User record) {
        return userMapper.countSelectedProperty(record)<1;
    }
    
    /**************************各种列表********************/

    /**
     * 专为CRUD列表时服务
     */
    @Override
    public List<User> listForCRUD(Map<String, Object> map) {
        return userMapper.listModels(map);
    }
    /**
     * 计数
     */
    @Override
    public Integer listForCRUDCount(Map<String, Object> map) {
        return userMapper.listModelsCount(map);
    }

}
