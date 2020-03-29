package com.hyl.blog.service;

import com.hyl.blog.dao.UserRepository;
import com.hyl.blog.pojo.User;
import com.hyl.blog.util.MD5Util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUserByUsernameAndPassword(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, MD5Util.getCode(password));
        return user;
    }
}
