package com.lswr.demo.model.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

import java.security.Key;

@Slf4j
@Service
public class TokenService {

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.validate-time}")
    private Long validateTime;

    private Key key;

    // secretKey 초기화
    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    // JWT 토큰 생성
    public String createToken(String userId) {
        Claims claims = Jwts.claims().setSubject(userId); // 토큰에 사용자 ID를 설정

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + validateTime))
                .signWith(SignatureAlgorithm.HS256, key) // 0.11.5 버전에서 사용하는 방식
                .compact();
    }

    // JWT 토큰 만료 체크
    public boolean isExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    // 토큰 유효성 검사
    public boolean isValidate(String token) {
    	try {
    		String id = getClaims(token).getSubject();
		} catch (Exception e) {
			log.error("TOKEN 파싱 실패");
			return false;
		}
    	return true;
    }
    
    // JWT 토큰에서 사용자 ID 추출
    public String getUserId(String token) {
        return getClaims(token).getSubject();
    }

    // JWT 토큰에서 Claims 추출
    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
    }
}
