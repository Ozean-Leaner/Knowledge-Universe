package com.ozean.ku.controller;

import com.ozean.ku.entity.Result;
import com.ozean.ku.entity.User;
import com.ozean.ku.exception.AuthorizeException;
import com.ozean.ku.service.AuthorizeService;
import com.ozean.ku.utills.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthorizeController {

    private final AuthorizeService authorizeService;
    private final JwtUtils jwtUtils;

    public AuthorizeController(AuthorizeService authorizeService, JwtUtils jwtUtils) {
        this.authorizeService = authorizeService;
        this.jwtUtils = jwtUtils;
    }

    @GetMapping("/sendVerifyCode")
    public Result<String> sendVerifyCode(HttpServletRequest request, String email) {
        MDC.put("IP", request.getRemoteAddr());
        authorizeService.sendVerifyCode(email);
        log.info("verification code sent to email: {}", email);
        return Result.success("验证码已发送!");
    }

    @PostMapping("/register")
    public Result<String> registerUser(HttpServletRequest request, String username, String password, String verificationCode, String email, Integer gender) {
        MDC.put("IP",request.getRemoteAddr());
        authorizeService.register(username, password, verificationCode, email, gender);
        log.info("user signed up");
        return Result.success("注册成功！");
    }


    @PostMapping("/login")
    public Result<String> login(HttpServletRequest request, String loginID, String password) {
        MDC.put("IP",request.getRemoteAddr());
        String token = authorizeService.login(loginID, password);
        log.info("user login");
        return Result.success(token);
    }

    @GetMapping("/refresh")
    public Result<String> refreshToken() {
        User user = (User) Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getPrincipal();
        if (user != null) {
            String jwt = jwtUtils.createJwt((UserDetails) user);
            return Result.success(jwt);
        }
        throw new AuthorizeException("未知错误");
    }
}
