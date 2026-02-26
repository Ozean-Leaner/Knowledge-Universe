package com.ozean.ku.controller;

import com.ozean.ku.entity.Result;
import com.ozean.ku.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class AuthorizeController {

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
}
