package com.ddup.sys.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ddup.sys.dao.UserMapper;
import com.ddup.sys.model.Role;
import com.ddup.sys.model.User;
import com.ddup.sys.service.UserService;
import com.ddup.utils.ProcessUtil;

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
    //private static final String COLUMNS_KEY1="columns_select";
    
    /** value **/
    //private static final String COLUMNS1="id,name";
    
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
    
    /*********************工具方法******************************/
    /**
     * @Title: formatUserList2ArrayList 
     * @Description: 将list(Page<E>)转为他的父类ArrayList
     * @param list
     * @throws
     */
    public static List<Map<String, Object>> formatUserList2ArrayList(List<User> list) {
        //返回的结果
        List<Map<String,Object>> resultList=new ArrayList<Map<String,Object>>();
        DateFormat sd= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//格式化时间
        
        Map<String,Object> tempMap=null;
        for (int i = 0; i < list.size(); i++) {
            User item=list.get(i);//源Model
            tempMap=new HashMap<String,Object>(0);//目标Map
            ProcessUtil.beanToMap(item, tempMap);//转Map
            
            //对可能有的时间进行格式化
            if(tempMap.containsKey("createTime")){
                tempMap.put("createTime",sd.format(tempMap.get("createTime")));
            }
            if(tempMap.containsKey("updateTime")){
                tempMap.put("updateTime",sd.format(tempMap.get("updateTime")));
            }
            
            //对权限进行字符串化处理
            String rIds="";
            String rNames="";
            next:
            if(tempMap.containsKey("roleList")){
                @SuppressWarnings("unchecked")
                List<Role> pList=(List<Role>)tempMap.get("roleList");
                if(pList.size()<1){
                    tempMap.remove("roleList");
                    break next;
                }
                for (int j = 0; j < pList.size(); j++) {
                    rIds+=pList.get(j).getId()+",";
                    rNames+=pList.get(j).getName()+",";
                }
                rIds=rIds.substring(0, rIds.length()-1);
                rNames=rNames.substring(0, rNames.length()-1);
                
                //清除privilegeList列
                tempMap.remove("roleList");
            }
            //设置上面生成的字符串描述
            tempMap.put("rIds",rIds);
            tempMap.put("rNames",rNames);
            
            //返回
            resultList.add(tempMap);
        }
        return resultList;
    }
}