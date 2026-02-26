package com.ozean.ku.mapper;

import com.ozean.ku.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("select (user_id, username, emai, description, gender, post_id, comment_id, avatar) from user where username = #{username}")
    User findUserByName(String username);

    @Select("select * from user")
    List<User> findAllUser();
}
