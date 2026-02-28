package com.ozean.ku.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthorizeService extends UserDetailsService {

    UserDetails loadUserByUsername(String username);

    void sendVerifyCode(String email);

    void register(String username, String password, String verificationCode, String email, Integer gender);

    void loginByUsername(String username, String password);

    void loginByEmail(String email, String password);

}
