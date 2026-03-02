package com.ozean.ku.mapper;

import com.ozean.ku.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper {

    @Select("select * from user where username = #{username}")
    User findUserAllByName(String username);

    @Select("select * from user")
    List<User> findAllUser();

    @Select("select * from user where email = #{email}")
    User findUserAllByEmail(String email);

    @Insert("insert into user (username, password, email, gender) values (#{username}, #{password}, #{email}, #{gender})")
    void addUser(String username, String password, String email, Integer gender);
}
