package com.ozean.ku.mapper;

import com.ozean.ku.VO.UserDetailVO;
import com.ozean.ku.VO.UserSimpleVO;
import com.ozean.ku.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper {

    @Select("select * from user where username = #{username}")
    User getUserAllInfoByName(String username);

    @Select("select * from user where email = #{email}")
    User getUserAllInfoByEmail(String email);

    @Select("select * from user where user_id = #{id}")
    User getUserAllInfoById(Integer id);

    @Insert("insert into user (username, password, email, gender) values (#{username}, #{password}, #{email}, #{gender})")
    void addUser(String username, String password, String email, Integer gender);

    @Select("select user_id, username, gender, avatar, description from user where username like concat('%', #{username}, '%')")
    List<UserSimpleVO> searchUserSimplePage(String name);

    @Select("select * from user")
    List<User> listAllUsers();

    @Select("select user_id, username, description, gender, post_id, avatar, comment_id from user where user_id = #{id}")
    UserDetailVO getUserDetailInfo(String id);
}
