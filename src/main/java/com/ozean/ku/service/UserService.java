package com.ozean.ku.service;

import com.ozean.ku.VO.UserDetailVO;
import com.ozean.ku.VO.UserSimpleVO;
import com.ozean.ku.entity.User;

import java.util.List;

public interface UserService {

    User getUserAllInfoByName(String username);

    List<User> listAllUsers();

    User getUserAllInfoByEmail(String email);

    User getUserAllInfoById(Integer id);

    List<UserSimpleVO> searchUserSimplePage(String name);

    UserDetailVO getUserDetailInfo(String id);

    UserSimpleVO getUserSimpleInfo(String id);

    User updateUserById(String id, String username, String email, Integer gender, String desc, String avatar);

}
