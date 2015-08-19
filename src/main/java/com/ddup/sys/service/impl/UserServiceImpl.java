package com.ddup.sys.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

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
    
    @Override
    public User getByUserNamePassword(User record) {
        return userMapper.getByUserNamePassword(record);
    }

}
