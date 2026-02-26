package com.ozean.ku.service;

import com.ozean.ku.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    User findUserByName(String username);

    List<User> findAllUser();
}
