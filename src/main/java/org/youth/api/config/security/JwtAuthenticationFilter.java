package org.youth.api.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {
	
	private final JwtTokenProvider jwtProvider;
	
	public static final String JWT_EXCEPTION = "JWT-EXCEPTION";

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		String token = jwtProvider.resolveToken((HttpServletRequest)request);
		
		try {
			if(token != null && jwtProvider.validateToken(token)) {
				Authentication authentication = jwtProvider.getAuthentication(token);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}catch(ExpiredJwtException e) {
			request.setAttribute(JWT_EXCEPTION, "로그인 세션이 만료되었습니다. 새로운 로그인이 필요합니다.");
		}catch(UnsupportedJwtException e) { //예상하는 형식과 다른 jwt토큰
			request.setAttribute(JWT_EXCEPTION, "로그인 형식이 올바르지 않습니다.");
		}catch (MalformedJwtException e) { //jwt 구성이 올바르지 않음
			request.setAttribute(JWT_EXCEPTION, "로그인 구성이 올바르지 않습니다.");
		}catch (SignatureException e) { //기존 서명이 확인되지 않음
			request.setAttribute(JWT_EXCEPTION, "기존의 로그인 토큰이 확인되지 않습니다.");
		}catch(Exception e) {
			request.setAttribute(JWT_EXCEPTION, "로그인 오류");
		}
		
		chain.doFilter(request, response);
	}
	
	
}
