package com.lswr.demo.interceptor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.lswr.demo.model.service.TokenService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtInterceptor implements HandlerInterceptor {

    private final TokenService tokenService;

    public JwtInterceptor(TokenService tokenService) {
        this.tokenService = tokenService;
    }

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
        String authorizationHeader = request.getHeader("Authorization");

        // Authorization 헤더가 없으면 인증 실패
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Authorization header is missing or invalid");
            return false;
        }
        
        String token = authorizationHeader.replace("Bearer ", "");

        // 토큰 유효성 검사
        if (tokenService.isExpired(token)) { // 만료된 경우에만 오류 반환
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Expired token");
            return false;
        }
        
        try {
            // 유효한 토큰에서 userId를 추출하여 request에 저장
            request.setAttribute("userId", tokenService.getUserId(token));
        } catch (Exception e) {
            log.error("토큰 파싱 실패: {}", e.getMessage());
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Invalid token");
            return false;
        }
        
        // 요청을 계속 처리할 수 있도록 허용
        return true;
	}
}
