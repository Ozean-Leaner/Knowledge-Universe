package com.ozean.ku.service.impl;

import com.ozean.ku.entity.User;
import com.ozean.ku.exception.UserException;
import com.ozean.ku.mapper.UserMapper;
import com.ozean.ku.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User findUserAllByName(String username) {
        User user = userMapper.findUserAllByName(username);
        if (user == null) {
            throw new UserException("User not found");
        }
        return user;
    }

    @Override
    public List<User> findAllUser() {
        List<User> users = userMapper.findAllUser();
        if (users == null) {
            throw new UserException("用户列表获取失败！");
        }
        return users;
    }
}
