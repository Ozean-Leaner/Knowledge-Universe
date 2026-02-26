package com.ozean.ku.mapper;

import com.ozean.ku.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("select (user_id, username, email, description, gender, post_id, comment_id, avatar) from user where username = #{username}")
    User findUserByName(String username);

    @Select("select * from user")
    List<User> findAllUser();

    @Select("select user_id from user where email = #{email}")
    User findUserByEmail(String email);

    @Insert("insert into user (username, email, password, gender) values (#{username}, #{email}, #{password}, #{gender})")
    void addUser(String username, String email, String password, Integer gender);
}
