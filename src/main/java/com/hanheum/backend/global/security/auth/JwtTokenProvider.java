package com.hanheum.backend.global.security.auth;

import com.hanheum.backend.domain.member.domain.Member;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider {

    private final Key secretKey;

    // 토큰 유효시간 30분
    private final long tokenValidTime;

    public JwtTokenProvider(@Value("${jwt.secret}")String secretKey, @Value("${jwt.expireTime}")long tokenValidTime) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
        this.tokenValidTime = tokenValidTime;
    }

    /**
     * 로그인에 성공한 user 엔티티 객체를 받아와 새로운 JWT를 발행합니다.
     * @param authentication
     * @return String JWT
     */
    public String createToken(Authentication authentication) {
        Date expTime = new Date();
        expTime.setTime(expTime.getTime() + tokenValidTime);
        Member principal = (Member)authentication.getPrincipal();
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));


        Claims claims = Jwts.claims()
                .setSubject(principal.getId().toString())
                .setExpiration(expTime)
                .setIssuedAt(new Date());

        return Jwts.builder()
                .setClaims(claims)
                .claim("role", authorities)
                .setExpiration(expTime)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    /**
     * 1. SecretKey로 서명된 JWT를 복호화합니다.<br>
     * 2. claims를 꺼내 sub, role을 이용해 Authentication객체를 생성하고 반환합니다.
     * @param jwtToken
     * @return UsernamePasswordAuthenticationToken
     */
    public Authentication getAuthentication(String jwtToken) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();

        Collection<? extends GrantedAuthority> role = Arrays.stream(claims.get("role").toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(claims.getSubject(), null, role);
    }

    /**
     * SecretKey를 이용해 JWT 토큰을 복호화하여 유효성을 검증합니다.
     * @param jwtToken
     * @return valid: true<br>
     * invalid: false
     */
    public boolean isTokenValid(String jwtToken) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(jwtToken);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        }
        return false;
    }

}
