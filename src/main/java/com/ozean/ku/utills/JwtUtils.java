package com.ozean.ku.utills;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class JwtUtils {

    @Value("${jwt.secret-key}")
    private String key;

    @Value("${jwt.expire-seconds}")
    private int expireSeconds;

    public String createJwt(UserDetails user) {
        Algorithm algorithm = Algorithm.HMAC256(key);
        Calendar calendar = Calendar.getInstance();
        Date now  = calendar.getTime();
        calendar.add(Calendar.SECOND, expireSeconds);
        return JWT.create()
                .withJWTId(UUID.randomUUID().toString())
                .withClaim("name", user.getUsername())
                .withClaim("authorities", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .withExpiresAt(calendar.getTime())
                .withIssuedAt(now)
                .sign(algorithm);
    }

    public UserDetails resolveJwt(String token) {
        Algorithm algorithm = Algorithm.HMAC256(key);
        JWTVerifier verifier = JWT.require(algorithm).build();
        try {
            DecodedJWT jwt = verifier.verify(token);

            if (blackList.contains(jwt.getId()))
                return null;

            Map<String, Claim> claims = jwt.getClaims();

            if(new Date().after(claims.get("exp").asDate()))
                return null;
            else
                return User
                        .withUsername(claims.get("name").asString())
                        .password("")
                        .authorities(claims.get("authorities").asArray(String.class))
                        .build();
        } catch (JWTVerificationException e) {
            return null;
        }
    }

    private static final HashSet<String> blackList = new HashSet<>();

    public boolean invalidate(String token) {
        Algorithm algorithm = Algorithm.HMAC256(key);
        JWTVerifier verifier = JWT.require(algorithm).build();
        try {
            DecodedJWT jwt = verifier.verify(token);
            Map<String, Claim> claims = jwt.getClaims();
            return blackList.add(jwt.getId());
        } catch (JWTVerificationException e) {
            return false;
        }
    }
}
