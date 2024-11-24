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
	
	    long startTime = System.currentTimeMillis();
	    log.info("요청이 들어왔다. 시작 시간: " + startTime);
        // WebSocket 요청 통과
        if ("websocket".equalsIgnoreCase(request.getHeader("Upgrade"))) {
            log.info("웹소켓 요청 통과");
            return true;
        }

        // CORS 프리플라이트 요청 통과
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            log.info("CORS 프리플라이트 요청 통과");
            response.setStatus(HttpServletResponse.SC_OK);
            return false; // Interceptor에서 나머지 로직 실행하지 않음
        }

		String authorizationHeader = request.getHeader("Authorization");
		log.info(authorizationHeader);
        
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
        long endTime = System.currentTimeMillis();
        log.info("요청 처리 완료. 소요 시간: " + (endTime - startTime) + "ms");
        
        return true;
	}
}
