package com.ozean.ku.controller;

import com.ozean.ku.entity.Result;
import com.ozean.ku.entity.User;
import com.ozean.ku.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Data
@Slf4j
@RestController()
public class UserController {

    private final UserService userservice;

    public UserController(UserService userservice) {
        this.userservice = userservice;
    }

    @GetMapping("/user/find")
    public Result<User> findUserByName(HttpServletRequest request,String name){
        MDC.put("IP",request.getRemoteAddr());
        User user = userservice.findUserByName(name);
        log.info("user found by username");
        return Result.success(user);
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('admin')")
    public Result<List<User>> findAllUsers(HttpServletRequest request){
        MDC.put("IP",request.getRemoteAddr());
        List<User> users = userservice.findAllUser();
        log.info("user find");
        return Result.success(users);
    }
}
