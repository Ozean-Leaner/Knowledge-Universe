package com.ozean.ku.controller;

import com.ozean.ku.VO.UserDetailVO;
import com.ozean.ku.VO.UserSimpleVO;
import com.ozean.ku.entity.Result;
import com.ozean.ku.entity.User;
import com.ozean.ku.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Data
@Slf4j
@RestController()
@RequestMapping("/user")
public class UserController {

    private final UserService userservice;

    public UserController(UserService userservice) {
        this.userservice = userservice;
    }

    @GetMapping("/info/all/{name}")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public Result<User> getUserAllInfoByName(HttpServletRequest request, @PathVariable String name) {
        MDC.put("IP",request.getRemoteAddr());
        User user = userservice.getUserAllInfoByName(name);
        log.info("user all info found by name");
        return Result.success(user);
    }

    @GetMapping("/info/all/{email}")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public Result<User> getUserAllInfoByEmail(HttpServletRequest request, @PathVariable String email) {
        MDC.put("IP",request.getRemoteAddr());
        User user = userservice.getUserAllInfoByEmail(email);
        log.info("user all info found by email");
        return Result.success(user);
    }

    @GetMapping("/info/all/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public Result<User> getUserAllInfoById(HttpServletRequest request, @PathVariable Integer id) {
        MDC.put("IP",request.getRemoteAddr());
        User user = userservice.getUserAllInfoById(id);
        log.info("user all info found by id");
        return Result.success(user);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public Result<List<User>> listAllUsers(HttpServletRequest request){
        MDC.put("IP",request.getRemoteAddr());
        List<User> users = userservice.listAllUsers();
        log.info("all users found");
        return Result.success(users);
    }

    @GetMapping("/info/simple/{name}")
    @PreAuthorize("hasAnyRole('USER','ADMIN','SUPER_ADMIN')")
    public Result<List<UserSimpleVO>> searchUserSimplePage(HttpServletRequest request, @PathVariable String name){
        MDC.put("IP",request.getRemoteAddr());
        List<UserSimpleVO> userSimpleVOs = userservice.searchUserSimplePage(name);
        log.info("user simple info found");
        return Result.success(userSimpleVOs);
    }

    @GetMapping("/info/detail/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN','SUPER_ADMIN')")
    public Result<UserDetailVO> getUserDetailInfo(HttpServletRequest request, @PathVariable String id){
        MDC.put("IP",request.getRemoteAddr());
        UserDetailVO userDetailVO = userservice.getUserDetailInfo(id);
        log.info("user detailed info found");
        return Result.success(userDetailVO);
    }

    @GetMapping("/info/simple/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN','SUPER_ADMIN')")
    public Result<UserSimpleVO> getUserSimpleInfo(HttpServletRequest request, @PathVariable String id){
        MDC.put("IP",request.getRemoteAddr());
        UserSimpleVO userSimpleVO = userservice.getUserSimpleInfo(id);
        log.info("user simple info found by id");
        return Result.success(userSimpleVO);
    }

    @PutMapping("/info/update/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN','SUPER_ADMIN')")
    public Result<User> updateUserById(HttpServletRequest request, @PathVariable String id,
                                       String username,
                                       String email,
                                       Integer gender,
                                       String desc,
                                       String avatar){
        MDC.put("IP",request.getRemoteAddr());
        User user = userservice.updateUserById(id, username, email, gender, desc, avatar);
        log.info("user update info by id");
        return Result.success(user);
    }
}
