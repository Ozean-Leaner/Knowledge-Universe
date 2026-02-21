package com.ozean.ku.controller;

import com.ozean.ku.entity.Result;
import com.ozean.ku.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController("/user")
public class UserController {

    @GetMapping("/")
    public Result getCategories(HttpServletRequest request) {
        MDC.put("IP",request.getRemoteAddr());
        log.info("categories got");
        return new Result();
    }

    @PostMapping("/register")
    public Result registerUser(HttpServletRequest request, @Valid User user) {
        MDC.put("IP",request.getRemoteAddr());
        log.info("user signed up");
        return new Result();
    }

}
