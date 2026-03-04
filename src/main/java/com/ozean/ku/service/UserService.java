package com.ozean.ku.service;

import com.ozean.ku.VO.UserDetailVO;
import com.ozean.ku.VO.UserSimpleVO;
import com.ozean.ku.entity.User;

import java.util.List;

public interface UserService {

    User getUserAllInfoByName(String username);

    List<User> listAllUsers();

    User getUserAllInfoByEmail(String email);

    User getUserAllInfoById(Long id);

    List<UserSimpleVO> searchUserSimplePage(String name);

    UserDetailVO getUserDetailInfo(Long id);

    UserSimpleVO getUserSimpleInfo(Long id);

    void updateUserById(Long id, String username, String email, Integer gender, String desc, String avatar);

    void deleteUserById(Long id);
}
