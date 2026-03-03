package com.ozean.ku.service.impl;

import com.ozean.ku.entity.User;
import com.ozean.ku.exception.AuthorizeException;
import com.ozean.ku.mapper.UserMapper;
import com.ozean.ku.service.AuthorizeService;
import com.ozean.ku.service.RedisService;
import com.ozean.ku.utills.JwtUtils;
import jakarta.annotation.Resource;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

@Service
public class AuthorizeServiceImpl implements AuthorizeService {

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    @Resource
    private Environment environment;

    private final UserMapper userMapper;
    private final RedisService redisService;
    private final JavaMailSender javaMailSender;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationContext applicationContext;
    private final JwtUtils jwtUtils;

    public AuthorizeServiceImpl(UserMapper userMapper, RedisService redisService, JavaMailSender javaMailSender, PasswordEncoder passwordEncoder, ApplicationContext applicationContext, JwtUtils jwtUtils) {
        this.userMapper = userMapper;
        this.redisService = redisService;
        this.javaMailSender = javaMailSender;
        this.passwordEncoder = passwordEncoder;
        this.applicationContext = applicationContext;
        this.jwtUtils = jwtUtils;
    }

    private static final String VERIFY_CODE_KEY_PREFIX = "verify_code:email:";
    private static final long VERIFY_CODE_EXPIRE_MINUTES = 30;

    private boolean isEmail(String loginID){
        return EMAIL_PATTERN.matcher(loginID).matches();
    }

    @Override
    public UserDetails loadUserByUsername(String loginID) throws UsernameNotFoundException {
        User user = isEmail(loginID) ? userMapper.getUserAllInfoByEmail(loginID) : userMapper.getUserAllInfoByName(loginID);
        if (user == null)
            throw new UsernameNotFoundException("用户名或密码错误");

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(String.valueOf(user.getRole()))
                .build();
    }

    @Override
    public void sendVerifyCode(String email) {

        if (userMapper.getUserAllInfoByEmail(email) != null){
            throw new AuthorizeException("该邮箱已被注册！");
        }

        String code = String.format("%06d", new SecureRandom().nextInt(1000000));

        String key = VERIFY_CODE_KEY_PREFIX + email;
        redisService.set(key, code, VERIFY_CODE_EXPIRE_MINUTES, TimeUnit.MINUTES);

        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom("Knowledgeuniverse@163.com");
            simpleMailMessage.setTo(email);
            simpleMailMessage.setSubject("【KU】注册验证码");
            simpleMailMessage.setText("您的注册验证码是："+code+"，30分钟内有效。");
            javaMailSender.send(simpleMailMessage);
        } catch (Exception e) {
            throw new AuthorizeException("未知错误：" + e.getMessage());
        }
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

        String encryptedPwd = passwordEncoder.encode(password);

        if (userMapper.getUserAllInfoByName(username) != null)
            throw new AuthorizeException("用户名已存在！");
        else if (userMapper.getUserAllInfoByEmail(email) != null)
            throw new AuthorizeException("该邮箱已注册！");

        userMapper.addUser(username, encryptedPwd, email, gender);

        redisService.delete(key);
    }

    @Override
    public String login(String loginID, String password) {
        try {
            AuthenticationManager authenticationManager = applicationContext.getBean(AuthenticationManager.class);
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginID, password));
            UserDetails userDetails = Objects.requireNonNull((UserDetails) authentication.getPrincipal(), "请输入用户名或密码！");
            return jwtUtils.createJwt(userDetails);
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof AuthenticationException) {
                throw new AuthorizeException("用户名或密码错误！");
            } else if (e.getCause() instanceof TooManyResultsException) {
                throw new AuthorizeException("系统错误：存在重复的用户数据，请联系管理员！");
            } else
            throw new AuthorizeException("未知错误："+ e.getMessage());
        }
    }
}
