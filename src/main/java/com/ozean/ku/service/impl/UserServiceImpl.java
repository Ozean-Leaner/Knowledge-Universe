package com.ozean.ku.service.impl;

import com.ozean.ku.VO.UserDetailVO;
import com.ozean.ku.VO.UserSimpleVO;
import com.ozean.ku.entity.User;
import com.ozean.ku.exception.UserException;
import com.ozean.ku.mapper.UserMapper;
import com.ozean.ku.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public User getUserAllInfoById(Long id) {
        User user = userMapper.getUserAllInfoById(id);
        if (user == null) {
            throw new UserException("User not found");
        }
        return user;
    }

    @Override
    public List<UserSimpleVO> searchUserSimplePage(String name) {
        List<UserSimpleVO> userSimpleVOs = new ArrayList<>();
        List<User> users = userMapper.searchUserSimplePage(name);
        for (User user : users) {
            UserSimpleVO userSimpleVO = new UserSimpleVO();
            BeanUtils.copyProperties(user, userSimpleVO);
            userSimpleVOs.add(userSimpleVO);
        }
        return userSimpleVOs;
    }

    @Override
    public UserDetailVO getUserDetailInfo(Long id) {
        UserDetailVO userDetailVO = new UserDetailVO();
        try {
            BeanUtils.copyProperties(userMapper.getUserDetailInfo(id), userDetailVO);
        } catch (BeansException e) {
            throw new UserException("UserDetailVO 转换失败：" + e.getMessage());
        }
        return userDetailVO;
    }

    @Override
    public UserSimpleVO getUserSimpleInfo(Long id) {
        UserSimpleVO userSimpleVO = new UserSimpleVO();
        try{
            BeanUtils.copyProperties(userMapper.getUserSimpleInfo(id), userSimpleVO);
        } catch(Exception e){
            throw new UserException("UserSimpleVO 转换失败：" + e.getMessage());
        }
        return userSimpleVO;
    }

    @Override
    public void updateUserById(Long id, String username, String email, Integer gender, String desc, String avatar) {
        userMapper.updateUserById(id, username, email, gender, desc, avatar);
    }

    @Override
    public void deleteUserById(Long id) {
        User user = userMapper.getUserSimpleInfo(id);
        if (user == null)
            throw new UserException("User not found");
        else userMapper.deleteUserById(id);
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
