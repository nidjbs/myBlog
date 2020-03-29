package com.hyl.blog.service;

import com.hyl.blog.pojo.User;

public interface UserService {
    User checkUserByUsernameAndPassword(String username, String password);
}
