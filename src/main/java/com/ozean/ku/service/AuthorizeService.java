package com.ozean.ku.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthorizeService extends UserDetailsService {

    UserDetails loadUserByUsername(String username);

    void sendVerifyCode(String email);

    void register(String username, String password, String verificationCode, String email, Integer gender);

    String login(String loginID, String password);

    void updateUserPwdById(String id, String password, String email, String verificationCode);

}
