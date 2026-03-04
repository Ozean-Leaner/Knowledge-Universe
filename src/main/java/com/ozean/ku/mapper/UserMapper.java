package com.ozean.ku.mapper;

import com.ozean.ku.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserMapper {

    @Select("select * from user where username = #{username}")
    User getUserAllInfoByName(String username);

    @Select("select * from user where email = #{email}")
    User getUserAllInfoByEmail(String email);

    @Select("select * from user where user_id = #{id}")
    User getUserAllInfoById(Long id);

    @Insert("insert into user (username, password, email, gender) values (#{username}, #{password}, #{email}, #{gender})")
    void addUser(String username, String password, String email, Integer gender);

    @Select("select user_id, username, gender, avatar, description from user where username like concat('%', #{username}, '%')")
    List<User> searchUserSimplePage(String name);

    @Select("select * from user")
    List<User> listAllUsers();

    @Select("select user_id, username, description, gender, post_id, avatar, comment_id from user where user_id = #{id}")
    User getUserDetailInfo(Long id);

    @Select("select user_id, username, gender, avatar, description from user where user_id = #{id}")
    User getUserSimpleInfo(Long id);

    @Update("update user set last_login_time = NOW() where user_id = #{id} ")
    void updateUserLoginTime(Long id);

    @Update("update user set username = #{username}, email = #{email}, gender = #{gender}, description = #{desc}, avatar = #{avatar} where user_id = #{id}")
    void updateUserById(Long id, String username, String email, Integer gender, String desc, String avatar);

    @Update("update user set password = #{password} where user_id = #{id}")
    void updateUserPwdById(Long id, String password);

    @Delete("delete from user where user_id = #{id}")
    void deleteUserById(Long id);
}
