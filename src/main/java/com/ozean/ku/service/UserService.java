package com.ozean.ku.service;

import com.ozean.ku.entity.User;

import java.util.List;

public interface UserService {

    User findUserAllByName(String username);

    List<User> findAllUser();
}
