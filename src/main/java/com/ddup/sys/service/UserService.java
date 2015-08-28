package com.ddup.sys.service;

import java.util.List;
import java.util.Map;

import com.ddup.sys.model.User;

public interface UserService {
    
    User getByUserNamePassword(User record);
    
    int insert(User record,Integer... rIds);
    void deleteByPrimaryKeys(Integer... ids);
    int updateByPrimaryKeySelective(User record,Integer... rIds);
    User selectByPrimaryKey(Integer id);
    boolean checkUnique(User record);//唯一返回true
    
    List<User> listForCRUD(Map<String,Object> map);//专为CRUD列表时服务
    
    Integer listForCRUDCount(Map<String,Object> map);
}
