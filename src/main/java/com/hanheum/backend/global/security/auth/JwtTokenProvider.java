package com.hanheum.backend.global.security.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    private String secretKey = "test";

    // 토큰 유효시간 30분
    private long tokenValidTime = 30 * 60 * 1000L;

//    public String createAndGetToken(String id) {
//        Claims claims = Jwts.claims().setSubject(id);
//        Date expTime = new Date();
//        expTime.setTime(expTime.getTime() + tokenValidTime);
//
//        return Jwts.builder()
//                .setSubject(authentication.getName())
//                .claim("auth", authorities)
//                .setExpiration(accessTokenExpiresIn)
//                .signWith(key, SignatureAlgorithm.HS256)
//                .compact()
//    }
}
