package com.ozean.ku.service.impl;

import com.ozean.ku.entity.User;
import com.ozean.ku.exception.AuthorizeException;
import com.ozean.ku.exception.UserException;
import com.ozean.ku.mapper.UserMapper;
import com.ozean.ku.service.AuthorizeService;
import com.ozean.ku.service.RedisService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.concurrent.TimeUnit;

public class AuthorizeServiceImpl implements AuthorizeService {
    private final UserMapper userMapper;
    private final RedisService redisService;
    private final JavaMailSender javaMailSender;

    public AuthorizeServiceImpl(UserMapper userMapper, RedisService redisService, JavaMailSender javaMailSender) {
        this.userMapper = userMapper;
        this.redisService = redisService;
        this.javaMailSender = javaMailSender;
    }

    private static final String VERIFY_CODE_KEY_PREFIX = "verify_code:email:";
    private static final long VERIFY_CODE_EXPIRE_MINUTES = 30;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.findUserByName(username);
        if (user == null)
            throw new UsernameNotFoundException("用户名或密码错误");
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles("USER")
                .build();
    }

    @Override
    public void sendVerifyCode(String email) {
        if (userMapper.findUserByEmail(email) != null){
            throw new AuthorizeException("该邮箱已被注册！");
        }

        String code = String.valueOf((int) (Math.random() * 9 + 1) * 100000);

        String key = VERIFY_CODE_KEY_PREFIX + email;
        redisService.set(key, code, VERIFY_CODE_EXPIRE_MINUTES, TimeUnit.MINUTES);

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("");
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("【KU】注册验证码");
        simpleMailMessage.setText("您的注册验证码是："+code+"，30分钟内有效。");
        javaMailSender.send(simpleMailMessage);
    }

    @Override
    public void register(String username, String password, String verificationCode, String email, Integer gender) {
        String key = VERIFY_CODE_KEY_PREFIX + email;
        String redisCode = (String) redisService.get(key);
        if (redisCode == null) {
            throw new AuthorizeException("验证码已过期！");
        }
        if (!redisCode.equals(verificationCode)) {
            throw new AuthorizeException("验证码错误！");
        }

        userMapper.addUser(username, password, email, gender);
    }

    @Override
    public void loginByUsername(String username, String password) {
        User user = userMapper.findUserByName(username);
        if (user == null) {
            throw new AuthorizeException("用户名或密码错误！");
        }
        if (!password.equals(user.getPassword())) {
            throw new AuthorizeException("用户名或密码错误！");
        }

    }

    @Override
    public void loginByEmail(String email, String password) {
        User user = userMapper.findUserByEmail(email);
        if (user == null) {
            throw new AuthorizeException("邮箱或密码错误！");
        }
        if (!password.equals(user.getPassword())) {
            throw new AuthorizeException("用户名或密码错误！");
        }
    }
}
