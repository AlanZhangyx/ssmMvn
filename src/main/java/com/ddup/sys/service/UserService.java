package com.ddup.sys.service;

import com.ddup.sys.model.User;

public interface UserService {
    
    User getByUserNamePassword(User record);
}
