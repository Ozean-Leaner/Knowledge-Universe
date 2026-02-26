package com.ozean.ku.controller;

import com.ozean.ku.entity.Result;
import com.ozean.ku.entity.User;
import com.ozean.ku.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping("/register")
    public Result registerUser(HttpServletRequest request, @Valid @RequestBody User user) {
        MDC.put("IP",request.getRemoteAddr());
        log.info("user signed up");
        return new Result();
    }

    @PostMapping("/login")
    public Result login(HttpServletRequest request, @Valid @RequestBody User user) {
        MDC.put("IP",request.getRemoteAddr());
        log.info("user login");
        return new Result();
    }

    @GetMapping("/user/find")
    public Result findUserByName(HttpServletRequest request,String name){
        MDC.put("IP",request.getRemoteAddr());
        User user = userservice.findUserByName(name);
        log.info("user found by username");
        return new  Result();
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('admin')")
    public Result findAllUsers(HttpServletRequest request){
        MDC.put("IP",request.getRemoteAddr());
        List<User> user = userservice.findAllUser();
        log.info("user find");
        return new Result();
    }
}
