package org.youth.api.config.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.youth.api.code.ResponseCode;
import org.youth.api.dto.ResponseDTO;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint{
	
	@Override
	public void commence(HttpServletRequest request,
						 HttpServletResponse response,
						 AuthenticationException authException) throws IOException, ServletException {
		
		log.warn("jwt authentication fail: {}", authException.getMessage());
		String message = (String)request.getAttribute(JwtAuthenticationFilter.JWT_EXCEPTION);

		ResponseDTO responseDTO = ResponseDTO.builder()
										  .code(ResponseCode.FAIL)
										  .message(message)
										  .build();
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonResponse = mapper.writeValueAsString(responseDTO);
		
		response.setContentType("application/json;charset=UTF-8");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.getWriter().print(jsonResponse);
	}

}
