package com.ozean.ku.service.impl;

import com.ozean.ku.VO.UserDetailVO;
import com.ozean.ku.VO.UserSimpleVO;
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
    public User getUserAllInfoByName(String username) {
        User user = userMapper.getUserAllInfoByName(username);
        if (user == null) {
            throw new UserException("User not found");
        }
        return user;
    }

    @Override
    public User getUserAllInfoByEmail(String email) {
        User user = userMapper.getUserAllInfoByEmail(email);
        if (user == null) {
            throw new UserException("User not found");
        }
        return user;
    }

    @Override
    public User getUserAllInfoById(Integer id) {
        User user = userMapper.getUserAllInfoById(id);
        if (user == null) {
            throw new UserException("User not found");
        }
        return user;
    }

    @Override
    public List<UserSimpleVO> searchUserSimplePage(String name) {
        List<UserSimpleVO> userSimpleVOs = userMapper.searchUserSimplePage(name);
        if  (userSimpleVOs == null)
            throw new UserException("User not found");
        return userSimpleVOs;
    }

    @Override
    public UserDetailVO getUserDetailInfo(String id) {
        UserDetailVO userDetailVO = userMapper.getUserDetailInfo(id);
        if (userDetailVO == null)
            throw new UserException("User not found");
        return userDetailVO;
    }

    @Override
    public List<User> listAllUsers() {
        List<User> users = userMapper.listAllUsers();
        if (users == null) {
            throw new UserException("用户列表获取失败！");
        }
        return users;
    }

}
